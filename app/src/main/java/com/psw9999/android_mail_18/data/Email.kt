package com.psw9999.android_mail_18.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Email(
    val type : String,
    val content : Content
) : Parcelable

@Parcelize
data class Content(
    val sender : String,
    val title : String,
    val content : String,
    val sendDate : String
) : Parcelable
