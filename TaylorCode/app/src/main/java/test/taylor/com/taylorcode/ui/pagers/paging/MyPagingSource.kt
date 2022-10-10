package test.taylor.com.taylorcode.ui.pagers.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import test.taylor.com.taylorcode.ui.recyclerview.variety.Text2

class MyPagingSource(private val repo: TextRepository):PagingSource<Int, Text2>() {
    override fun getRefreshKey(state: PagingState<Int, Text2>): Int? {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Text2> {
        Log.v("ttaylor", "MyPagingSource.load(param.key=${params.key},loadSize=${params.loadSize}) ")
        return  try {
            val key = params.key ?: 0
            val loadSize = params.loadSize ?: 10
            delay(500)
            val data = repo.getText(loadSize)
            LoadResult.Page(
                data,
                if(key <=0) null else key-1,
                if(data.isNullOrEmpty()) null else key+1
            )
        } catch(exception:java.lang.Exception){
            LoadResult.Error(exception)
        }
    }
}