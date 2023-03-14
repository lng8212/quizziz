package com.longkd.quizizz.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.longkd.quizizz.domain.settings.GetThemeUseCase
import com.longkd.quizizz.domain.settings.ObserveThemeModeUseCase
import com.longkd.quizizz.model.Theme
import com.longkd.quizizz.result.Result
import com.longkd.quizizz.result.successOr
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


/**
 * Interface to implement activity theming via a ViewModel.
 *
 * You can inject a implementation of this via Dagger2, then use the implementation as an interface
 * delegate to add the functionality without writing any code
 *
 * Example usage:
 * ```
 * class MyViewModel @Inject constructor(
 *     themedActivityDelegate: ThemedActivityDelegate
 * ) : ViewModel(), ThemedActivityDelegate by themedActivityDelegate {
 * ```
 */

interface ThemedActivityDelegate {
    /**
     * Allows observing of the current theme
     */

    val theme: LiveData<Theme>

    /**
     * Allows querying of the current theme synchronously
     */
    val currentTheme: Theme
}

class ThemedActivityDelegateImpl @Inject constructor(
    private val observeThemeUseCase: ObserveThemeModeUseCase,
    private val getThemeUseCase: GetThemeUseCase
) : ThemedActivityDelegate {

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override val theme: LiveData<Theme> = liveData {
        observeThemeUseCase(Unit).collect {
            emit(it.successOr(Theme.SYSTEM))
        }
    }

    override val currentTheme: Theme
        get() = runBlocking { // Using runBlocking to execute this coroutine synchronously
            getThemeUseCase(Unit).let {
                if (it is Result.Success) it.data else Theme.SYSTEM
            }
        }
}
