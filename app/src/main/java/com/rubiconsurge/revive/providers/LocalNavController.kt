package com.rubiconsurge.revive.providers

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalNavHost =
    compositionLocalOf<NavHostController>{ error("Nav host controller is not provided") }