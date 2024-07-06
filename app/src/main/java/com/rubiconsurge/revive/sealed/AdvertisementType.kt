package com.rubiconsurge.revive.sealed

sealed class AdvertisementType{
    object Store: AdvertisementType()
    object Product: AdvertisementType()
}