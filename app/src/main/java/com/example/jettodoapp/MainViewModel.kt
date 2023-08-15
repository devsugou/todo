package com.example.jettodoapp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * DBから取得した情報を保持するModelクラス
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val taskDao: TaskDao): ViewModel() {
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var isShowDialog by mutableStateOf(false)

    /**
     * DBから最新のタスク情報を全て取得
     */
    val tasks = taskDao.loadAllTasks().distinctUntilChanged()

    /**
     * 編集用のTaskIDを保持
     */
    private var editingTask: Task? = null

    /**
     * 編集Mode=true/新規作成Mode=false
     */
    val isEditing: Boolean get() = editingTask != null

    /**
     * editingTaskのSetter
     */
    fun setEditingTask(task: Task){
        editingTask = task
        title = task.title
        description = task.description
    }

    /**
     * DBにタスク情報を登録
     */
    fun createTask() {
        viewModelScope.launch {
            val newTask = Task(title = title, description = description)
            taskDao.insertTask(newTask)
            Log.d(MainViewModel::class.simpleName, "success create task")
        }
    }

    /**
     * タスクを削除する
     */
    fun deleteTask(task: Task){
        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }

    /**
     * タスクを更新する
     */
    fun updateTask(){
        //nullチェック
        editingTask ?.let { task ->
            viewModelScope.launch {
                task.title = title
                task.description = description
                taskDao.updateTask(task)
            }
        }
    }

    /**
     * ダイヤログの情報をリセットする
     */
    fun resetProperties() {
        editingTask = null
        title = ""
        description = ""
    }
}