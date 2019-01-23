package com.anou.prototype.core.db.module

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class ModuleDao {
//    @Query("SELECT * FROM Module ORDER BY position ASC")
//    abstract fun loadAllModules(): LiveData<List<ModuleEntity>>
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    abstract fun insertAll(modules: List<ModuleEntity>)
//
//    @Query("SELECT COUNT(*) from Module")
//    abstract fun count(): LiveData<Int>
//
//    @Query("SELECT * FROM Module where eid LIKE  :moduleEid")
//    abstract fun loadModuleByEid(moduleEid: String): LiveData<ModuleEntity>
//
//    @Query("SELECT * FROM Module")
//    abstract fun retrieveAll(): LiveData<List<ModuleEntity>>

}
