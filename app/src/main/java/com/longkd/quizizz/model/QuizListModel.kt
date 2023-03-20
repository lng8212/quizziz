package com.longkd.quizizz.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuizListModel(
    val quiz_id: String,
    val name: String,
    val desc: String,
    val image: String,
    val level: String,
    val visibility: String,
    val category: String,
    val questions: Long
) : Parcelable {

    constructor() : this("", "", "", "", "", "", "", 0L)

}