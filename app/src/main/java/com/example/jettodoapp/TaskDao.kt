package com.example.jettodoapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

/**
 * DB接続用のDaoインターフェース
 */
@Dao
interface TaskDao {

    /**
     * 非同期ワンショットクエリ
     */
    @Insert
    suspend fun insertTask(task: Task)

    /**
     * オブザーバブルクエリ
     */
    @Query("select * from Task")
    fun loadAllTasks(): Flow<List<Task>>

    /**
     * 非同期ワンショットクエリ
     */
    @Update
    suspend fun updateTask(task: Task)

    /**
     * 非同期ワンショットクエリ
     */
    @Delete
    suspend fun deleteTask(task: Task)
}