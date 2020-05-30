package test.taylor.com.taylorcode.retrofit.presenter

import android.content.Context
import test.taylor.com.taylorcode.retrofit.News

interface NewsView {
    fun showNews(news:List<News>?)
    abstract val newsContext:Context
}