package com.rubiconsurge.revive.models

import androidx.room.Embedded
import androidx.room.Relation

data class BookmarkItemWithProduct(
    @Embedded
    val bookmark: BookmarkItem,
    @Relation(parentColumn = "productId", entityColumn = "id", entity = Product::class)
    val product: LocalProduct,
)
