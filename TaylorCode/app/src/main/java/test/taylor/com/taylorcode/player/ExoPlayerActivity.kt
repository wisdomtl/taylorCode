package test.taylor.com.taylorcode.player

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import test.taylor.com.taylorcode.R

class ExoPlayerActivity : AppCompatActivity() {
    private lateinit var exoPlayerView: StyledPlayerView
    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exo_player_activity)
        exoPlayerView = findViewById(R.id.stylePlayerView)

    }

    private fun initPlayer() {
        if (player != null) return
        player = ExoPlayer.Builder(this@ExoPlayerActivity).build().also { player ->
            exoPlayerView.player = player
            player.setMediaItem(MediaItem.fromUri("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
            player.playWhenReady = true
            player.prepare()
        }
    }

    private fun releasePlayer(){
        player?.run {
            // TODO: playback info
//            playbackPosition = this.currentPosition
//            currentWindow = this.currentWindowIndex
            playWhenReady = this.playWhenReady
            release()
        }
        player = null
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            initPlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            initPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            releasePlayer()
        }
    }

    /**
     * Full screen experience
     */
    private fun hideSystemUi() {
        exoPlayerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }


}