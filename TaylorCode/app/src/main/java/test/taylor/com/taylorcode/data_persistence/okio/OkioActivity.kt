package test.taylor.com.taylorcode.data_persistence.okio

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okio.buffer
import okio.sink
import java.io.File

class OkioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath
        Log.v("ttaylor", "onCreate() dir=$dir")
        dir?.let {
            MainScope().launch {
                repeat(10_000) {
                    writeText(File("$dir/taylor"))
                }
            }
        }
    }

    private fun writeText(file: File) {
        file.sink(true).buffer().use {
            it.writeUtf8("hello okio")
            it.writeUtf8("\n")
        }
    }
}