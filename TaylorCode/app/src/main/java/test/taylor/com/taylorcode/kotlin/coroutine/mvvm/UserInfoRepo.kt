package test.taylor.com.taylorcode.kotlin.coroutine.mvvm

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class UserInfoRepo(context: Context) {
    private val dataStore = context.dataStore
    val remoteUserInfoFlow = flow {
        delay(2000)
        emit(UserInfo("remote name"))
    }
    val localUserInfoFlow =
        dataStore.data
            .map { UserInfo(it[stringPreferencesKey("name")] ?: "") }
            .onEach { delay(1000) }
}

