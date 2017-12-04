package com.quickseries.rca.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

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
 * Created by Anou Chanthavong on 2017-12-04.
 ******************************************************************************/
@Database(entities = {ModuleEntity.class}, version = 1)
public abstract class ApplicationDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "qs-rca-db";

    public abstract ModuleDao moduleDao();
}
