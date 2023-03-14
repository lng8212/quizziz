package com.longkd.quizizz.domain.settings

import android.os.Build
import com.longkd.quizizz.data.prefs.PreferenceStorage
import com.longkd.quizizz.di.DefaultDispatcher
import com.longkd.quizizz.model.Theme
import com.longkd.quizizz.model.themeFromStorageKey
import com.longkd.quizizz.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


open class ObserveThemeModeUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage,
    @DefaultDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, Theme>(dispatcher) {
    override fun execute(parameters: Unit): Flow<Result<Theme>> {
        return preferenceStorage.observableSelectedTheme.map {
            val theme = themeFromStorageKey(it)
                ?: when {
                    Build.VERSION.SDK_INT >= 29 -> Theme.SYSTEM
                    else -> Theme.BATTERY_SAVER
                }
            Result.Success(theme)
        }
    }
}
