package test.taylor.com.taylorcode.retrofit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import test.taylor.com.taylorcode.retrofit.repository.NewsRepository
import test.taylor.com.taylorcode.retrofit.repository.NewsRepositoryImpl

/**
 * level 2: business is in ViewModel
 */
class NewsViewModel2(var newsRepository: NewsRepository) : ViewModel() {

    var newsLiveData = MutableLiveData<List<News>>()

    fun fetchNews() {
        newsRepository.fetchNews().subscribe(
            {news ->
                newsLiveData.value = news
            },
            {error->
                newsLiveData.value = null
            }
        )
    }
}

class NewsFactory :ViewModelProvider.Factory{
    private val newsRepository= NewsRepositoryImpl()
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel2(newsRepository) as T
    }
}