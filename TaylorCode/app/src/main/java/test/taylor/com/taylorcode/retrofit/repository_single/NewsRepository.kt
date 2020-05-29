package test.taylor.com.taylorcode.retrofit.repository_single

import androidx.lifecycle.LiveData
import io.reactivex.Single
import test.taylor.com.taylorcode.retrofit.News

interface NewsRepository {
    fun fetchNewsSingle(): Single<List<News>?>
}