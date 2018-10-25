/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.anou.prototype.yoga.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.anou.prototype.yoga.db.ModuleDao
import com.anou.prototype.yoga.db.ModuleEntity

/**
 * Main database description.
 */
@Database(
    entities = [
        ModuleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {
    companion object {
        val DATABASE_NAME = "prototype-yoga.db"
    }

    abstract fun moduleDao(): ModuleDao

}
