package test.taylor.com.taylorcode.kotlin.coroutine.mvvm

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.extension.contentView
import test.taylor.com.taylorcode.kotlin.layout_width
import test.taylor.com.taylorcode.kotlin.match_parent

class StateFlowActivity : AppCompatActivity() {

    private val userInfoViewModel by lazy {
        ViewModelProvider(
            this,
            UserInfoViewModelFactory(UserInfoRepo(this))
        )[UserInfoViewModel::class.java]
    }

    private lateinit var tv: TextView

    private val contentView by lazy {
        ConstraintLayout {
            layout_id = "container"
            layout_width = match_parent
            layout_height = match_parent

            tv = TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 30f
                textColor = "#000000"
                center_horizontal = true
                center_vertical = true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            dataStore.edit { it[stringPreferencesKey("name")] = "local name" }
        }
        setContentView(contentView)

        /**
         * use StateFlow like LiveData
         */
//        userInfoViewModel.fetchUserInfo()
//        lifecycleScope.launch {
//            userInfoViewModel.userInfoStateFlow.collect {
//                if (it.loading) {
//                    showLoading()
//                } else {
//                    dismissLoading()
//                    tv.text = it.userName
//                }
//            }
//        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                userInfoViewModel.userInfoSharedFlow.collect {
                    showUserName(it) }
            }
        }
    }

    private fun showUserName(it: UserInfoModel) {
        if (it.loading) {
            showLoading()
        } else {
            dismissLoading()
            tv.text = it.userName
        }
    }
}

fun Activity.showLoading() {
    contentView()?.apply {
        ProgressBar2 {
            layout_id = "pb"
            layout_width = 50
            layout_height = 50
            layout_gravity2 = gravity_center
        }
    }
}

fun Activity.dismissLoading() {
    val pb = contentView()?.find<ProgressBar>("pb")
    pb?.let { contentView()?.removeView(it) }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user-info")