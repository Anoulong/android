package com.quickseries.rca.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.quickseries.rca.manager.ExecutorManager;

import static com.quickseries.rca.repository.NetworkBoundResource.Status.ERROR;
import static com.quickseries.rca.repository.NetworkBoundResource.Status.LOADING;
import static com.quickseries.rca.repository.NetworkBoundResource.Status.SUCCESS;

/*******************************************************************************
 * QuickSeries® Publishing inc.
 * <p>
 * Copyright (c) 1992-2017 QuickSeries® Publishing inc.
 * All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of QuickSeries®
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with QuickSeries® and QuickSeries's Partners.
 * <p>
 * Created by Anou Chanthavong on 2017-12-05.
 ******************************************************************************/
public abstract class NetworkBoundResource <ResultType, RequestType> {
    public enum Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    private final ExecutorManager appExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        result.setValue(Resource.loading(null));
        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> setValue(Resource.success(newData)));
            }
        });
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource, newData -> setValue(Resource.loading(newData)));
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(dbSource);
            //noinspection ConstantConditions
            if (response.isSuccessful()) {
                appExecutors.diskIO().execute(() -> {
                    saveCallResult(processResponse(response));
                    appExecutors.mainThread().execute(() ->
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            result.addSource(loadFromDb(),
                                    newData -> setValue(Resource.success(newData)))
                    );
                });
            } else {
                onFetchFailed();
                result.addSource(dbSource,
                        newData -> setValue(Resource.error(response.errorMessage, newData)));
            }
        });
    }

    protected void onFetchFailed() {
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.body;
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();


    /**
     * A generic class that holds a value with its loading status.
     * @param <T>
     */
    public class Resource<T> {

        @NonNull
        public final Status status;

        @Nullable
        public final String message;

        @Nullable
        public final T data;

        public Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
            this.status = status;
            this.data = data;
            this.message = message;
        }

        public  <T> Resource<T> success(@Nullable T data) {
            return new Resource<>(SUCCESS, data, null);
        }

        public  <T> Resource<T> error(String msg, @Nullable T data) {
            return new Resource<>(ERROR, data, msg);
        }

        public  <T> Resource<T> loading(@Nullable T data) {
            return new Resource<>(LOADING, data, null);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Resource<?> resource = (Resource<?>) o;

            if (status != resource.status) {
                return false;
            }
            if (message != null ? !message.equals(resource.message) : resource.message != null) {
                return false;
            }
            return data != null ? data.equals(resource.data) : resource.data == null;
        }

        @Override
        public int hashCode() {
            int result = status.hashCode();
            result = 31 * result + (message != null ? message.hashCode() : 0);
            result = 31 * result + (data != null ? data.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Resource{" +
                    "status=" + status +
                    ", message='" + message + '\'' +
                    ", data=" + data +
                    '}';
        }
    }

}
