package com.android.whatstools

import androidx.room.PrimaryKey

data class Sample(
    val name: String,
    val message: String,
    val profile: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
)
