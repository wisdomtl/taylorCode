package test.taylor.com.taylorcode.ui.recyclerview.anim2

import android.view.animation.LinearInterpolator
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import jp.wasabeef.recyclerview.animators.BaseItemAnimator

class PartyAvatarItemAnimator:BaseItemAnimator() {
    override fun animateAddImpl(holder: RecyclerView.ViewHolder) {
        ViewCompat.animate(holder.itemView)
            .alpha(1f)
            .setDuration(500)
            .setInterpolator(LinearInterpolator())
            .start()
    }

    override fun animateRemoveImpl(holder: RecyclerView.ViewHolder) {
        ViewCompat.animate(holder.itemView)
            .alpha(0f)
            .setDuration(1000)
            .setInterpolator(LinearInterpolator())
            .start()
    }
}