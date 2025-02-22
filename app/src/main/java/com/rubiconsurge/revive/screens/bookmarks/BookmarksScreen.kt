package com.mustfaibra.revive.screens.bookmarks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rubiconsurge.revive.R
import com.rubiconsurge.revive.components.ProductItemLayout
import com.rubiconsurge.revive.sealed.UiState
import com.rubiconsurge.revive.theme.Dimension

@Composable
fun BookmarksScreen(
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
    cartOffset: IntOffset,
    cartProductsIds: List<Int>,
    onProductClicked: (productId: Int) -> Unit,
    onCartStateChanged: (productId: Int) -> Unit,
    onBookmarkStateChanged: (productId: Int) -> Unit,
) {
    val bookmarksUiState by remember { bookmarkViewModel.bookmarkState }
    val bookmarkProducts = bookmarkViewModel.bookmarkItems

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
        verticalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
        contentPadding = PaddingValues(horizontal = Dimension.pagePadding),
    ) {
        /** Handling what to show depending on bookmark ui state */
        when (bookmarksUiState) {
            is UiState.Idle -> {
                /** it's idle, not sure */

            }
            is UiState.Loading -> {
                /** Still loading */

            }
            is UiState.Success -> {
                /** Loading finished successfully, Shoes header first! */
                item(
                    span = {
                        GridItemSpan(2)
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.bookmarks),
                        fontSize = 32.sp,
                        color = colorResource(id = R.color.theme_color)
                    )
                }
                /** Show bookmarked products */
                items(bookmarkProducts) { product ->
                    ProductItemLayout(
                        modifier = Modifier.fillMaxWidth(),
                        cartOffset = cartOffset,
                        price = product.price,
                        title = product.name,
                        discount = product.discount,
                        onCart = product.id in cartProductsIds,
                        onBookmark = product in bookmarkProducts,
                        onProductClicked = {
                            onProductClicked(product.id)
                        },
                        onChangeCartState = {
                            onCartStateChanged(product.id)
                        },
                        onChangeBookmarkState = { onBookmarkStateChanged(product.id) },
                        image = product.image,
                    )
                }
            }
            is UiState.Error -> {
                /** An error occur */
            }
        }
    }
}

