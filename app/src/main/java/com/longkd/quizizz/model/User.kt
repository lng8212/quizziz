package com.longkd.quizizz.model

import android.net.Uri

data class User(
    val userId: String = "",
    val name: String? = "",
    val imageUrl: Uri? = null,
    val email: String? = ""
)