package test.taylor.com.taylorcode.retrofit.repository_single

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import test.taylor.com.taylorcode.retrofit.News

/**
 * level 2: business is in ViewModel
 */
class NewsViewModel(var newsRepository: NewsRepository) : ViewModel() {

    var newsLiveData = MutableLiveData<List<News>>()

    fun fetchNewsSingle() {
        newsRepository.fetchNewsSingle().subscribe(
            {news ->
                newsLiveData.value = news
            },
            {error->
                newsLiveData.value = emptyList()
            }
        )
    }
}

class NewsFactory(context: Context) : ViewModelProvider.Factory {
    private val newsRepository = NewsRepositoryImpl(context)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}