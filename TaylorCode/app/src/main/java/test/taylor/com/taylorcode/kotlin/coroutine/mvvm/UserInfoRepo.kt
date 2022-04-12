package test.taylor.com.taylorcode.kotlin.coroutine.mvvm

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import tech.thdev.network.flowcalladapterfactory.FlowCallAdapterFactory
import test.taylor.com.taylorcode.retrofit.News
import test.taylor.com.taylorcode.retrofit.repository_livedata.room.NewsDatabase

class UserInfoRepo(context: Context) {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.apiopen.top")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    private val newApi by lazy { retrofit.create(NewsApi::class.java) }
    private val newDatabase = NewsDatabase.getInstance(context)
    private val newsDao = newDatabase.newsDao()
    private val dataStore = context.dataStore

    val remoteUserInfoFlow = flow {
        delay(2000)
        emit(UserInfo("remote name"))
    }

    val localUserInfoFlow =
        dataStore.data
            .map { UserInfo(it[stringPreferencesKey("name")] ?: "") }
            .onEach { delay(1000) }

    val localNewsFlow = newsDao.queryNewsFlow().map { news ->
        news?.map { News(it.path, it.image, it.title, it.passtime) } ?: emptyList()
    }

    val remoteNewsFlow = newApi.fetchNews(
        mapOf("page" to "1", "count" to "8")
    ).map { newsBean ->
        if (newsBean.code == 200) {
            newsBean.result ?: emptyList()
        } else {
            throw Exception(newsBean.message)
        }
    }
}

