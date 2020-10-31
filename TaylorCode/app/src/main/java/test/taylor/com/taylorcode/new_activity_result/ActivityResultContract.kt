package test.taylor.com.taylorcode.new_activity_result

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import test.taylor.com.taylorcode.kotlin.coroutine.CoroutineActivity

class ActivityResultContract : ActivityResultContract<Int, Int>() {
    override fun createIntent(context: Context, input: Int?): Intent {
        return Intent(context, CoroutineActivity::class.java).apply {
            putExtra("name", input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Int {
        return if (resultCode == Activity.RESULT_OK && intent != null) intent.getIntExtra("name", 0) else 0
    }
}