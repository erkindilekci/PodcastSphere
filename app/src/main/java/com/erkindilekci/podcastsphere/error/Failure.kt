package com.erkindilekci.podcastsphere.error

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.erkindilekci.podcastsphere.R

sealed class Failure(@StringRes val key: Int) {

    object UnexpectedFailure : Failure(R.string.unexpected_error)

    @Composable
    fun translate(): String {
        return stringResource(key)
    }
}
