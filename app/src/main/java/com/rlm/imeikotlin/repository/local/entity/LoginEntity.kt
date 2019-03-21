package com.rlm.imeikotlin.repository.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoginEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int)