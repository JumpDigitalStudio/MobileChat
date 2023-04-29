package com.example.chatandroidapp.models

import java.text.SimpleDateFormat
import java.util.Date

data class Message(
    var date: String = "",
    var message: String = "",
    var sender: String = ""
)
{
    override fun toString(): String {
        val formattedDate = SimpleDateFormat("HH:mm").format(Date(date))
        return "$sender: $message ($formattedDate)"
    }
}
