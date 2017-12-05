package com.quickseries.rca.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.quickseries.rca.viewmodel.ModuleListViewModel;
import com.quickseries.rca.viewmodel.RcaViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ModuleListViewModel.class)
    public abstract ViewModel bindModuleListViewModel(ModuleListViewModel moduleListViewModel);

//    @Binds
//    @IntoMap
//    @ViewModelKey(NewsViewModel.class)
//    abstract ViewModel bindNewsViewModel(NewsViewModel newsViewModel);


    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(RcaViewModelFactory factory);
}