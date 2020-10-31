package test.taylor.com.taylorcode.new_activity_result

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class NewActivityResultActivity:AppCompatActivity() {
    private val activityResultContract = ActivityResultContract()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerForActivityResult(activityResultContract){
            Log.v("ttaylor","tag=asdf, NewActivityResultActivity.onCreate()  on activity result=${it}")
        }.launch(22)
    }

    override fun onResume() {
        super.onResume()
        Log.v("ttaylor","tag=asdf, NewActivityResultActivity.onResume()  ")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.v("ttaylor","tag=, NewActivityResultActivity.onActivityResult()  ")
        super.onActivityResult(requestCode, resultCode, data)
    }
}