package com.anou.prototype.yoga.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Flowable

/*******************************************************************************
 * QuickSeries® Publishing inc.
 *
 *
 * Copyright (c) 1992-2017 QuickSeries® Publishing inc.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information of QuickSeries®
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with QuickSeries® and QuickSeries's Partners.
 *
 *
 * Created by Anou Chanthavong on 2018-01-29.
 */
@Dao
abstract class ModuleDao {
    @Query("SELECT * FROM Module ORDER BY position ASC")
    abstract    fun loadAllModules(): LiveData<List<ModuleEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertAll(modules: List<ModuleEntity>)

    @Query("SELECT COUNT(*) from Module")
    abstract fun count(): LiveData<Int>

    @Query("SELECT * FROM Module where eid LIKE  :moduleEid")
    abstract fun loadModuleByEid(moduleEid: String): LiveData<ModuleEntity>

}
