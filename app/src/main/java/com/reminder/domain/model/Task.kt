package com.reminder.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val id: Int,
    val name: String,
    val description: String?,
    val remindTime: String?
) : Parcelable