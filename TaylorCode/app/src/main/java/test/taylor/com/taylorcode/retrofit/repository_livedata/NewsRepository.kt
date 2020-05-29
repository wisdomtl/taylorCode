package test.taylor.com.taylorcode.retrofit.repository_livedata

import androidx.lifecycle.LiveData
import io.reactivex.Single
import test.taylor.com.taylorcode.retrofit.News

interface NewsRepository {

    fun fetchNewsLiveData():LiveData<List<News>?>
}