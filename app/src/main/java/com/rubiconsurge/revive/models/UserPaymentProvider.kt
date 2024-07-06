package com.rubiconsurge.revive.models

import androidx.room.Entity

@Entity(tableName = "userPaymentProviders", primaryKeys = ["providerId", "cardNumber"])
data class UserPaymentProvider(
    val providerId: String,
    val cardNumber: String,
)
