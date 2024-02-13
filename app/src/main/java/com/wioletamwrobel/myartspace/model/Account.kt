package com.wioletamwrobel.myartspace.model

import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.twotone.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Account (
    @ColumnInfo(name = "user_id")@PrimaryKey(autoGenerate = true) val userId: Long = 0,
    val mail: String = "",
    val password: String = ""
)

