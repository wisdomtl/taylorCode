package test.taylor.com.taylorcode.no_field

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NoFieldViewModel:ViewModel() {

   val newsViewModel by lazy { MutableLiveData<NewsBean>() }

   fun fetchNews(){

   }
}