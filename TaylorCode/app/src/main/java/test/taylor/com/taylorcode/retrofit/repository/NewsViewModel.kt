package test.taylor.com.taylorcode.retrofit.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import test.taylor.com.taylorcode.retrofit.News

/**
 * level 2: business is in ViewModel
 */
class NewsViewModel(var newsRepository: NewsRepository) : ViewModel() {

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
        return NewsViewModel(newsRepository) as T
    }
}