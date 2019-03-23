package com.rlm.imeikotlin.repository.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = LoginEntity.TABLE_NAME_LOGIN)
data class LoginEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int) {

    companion object {
        const val TABLE_NAME_LOGIN = "login"
    }
}