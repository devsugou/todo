package com.example.jettodoapp

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * RoomのDataEntityクラス
 */
@Entity
data class Task(
    //id自動生成
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title: String,
    var description: String,
)
