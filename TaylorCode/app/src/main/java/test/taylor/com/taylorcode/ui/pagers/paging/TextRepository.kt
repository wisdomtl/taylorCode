package test.taylor.com.taylorcode.ui.pagers.paging

import android.util.Log
import test.taylor.com.taylorcode.ui.recyclerview.variety.Text2

class TextRepository {

    private var itemNumber: Int = 1

    fun getText(count: Int): List<Text2> {
        Log.v("ttaylor", "TextRepository.getText(): ")
        return listOf(*(0..count).map { Text2(itemNumber,"item ${itemNumber++}") }.toTypedArray())
    }
}