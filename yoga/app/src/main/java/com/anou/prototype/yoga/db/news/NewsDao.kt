package com.anou.prototype.yoga.db.news

import androidx.room.*
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
 * Created by Anou Chanthavong on 2018-01-30.
 */

@Dao
abstract class NewsDao {

    @Query("SELECT * FROM News where customModuleEid LIKE :moduleEid AND status LIKE 'published' ORDER BY updatedAt DESC ")
    abstract fun loadNews(moduleEid: String): Flowable<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(news: List<NewsEntity>)

    @Query("DELETE FROM News WHERE customModuleEid = :moduleEid")
    abstract fun deleteByModuleEid(moduleEid: String)

    @Query("SELECT COUNT(*) from News where status LIKE 'published'")
    abstract fun count(): Flowable<Int>

}
