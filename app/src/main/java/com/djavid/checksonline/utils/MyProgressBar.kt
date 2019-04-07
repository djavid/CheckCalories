package com.djavid.checksonline.utils

import android.content.Context
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.support.graphics.drawable.Animatable2Compat
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import com.djavid.checksonline.R
import javax.inject.Inject

class MyProgressBar @Inject constructor(
        context: Context,
        attrs: AttributeSet?
) : FrameLayout(context, attrs) {

    private var imageView: ImageView? = null
    private var inProgress: Boolean = false

    private val callback = object : Animatable2Compat.AnimationCallback() {
        override fun onAnimationEnd(drawable: Drawable?) {
            if (inProgress) (drawable as Animatable).start()
        }
    }

    init {
        imageView = ImageView(context, attrs).apply {
            setImageResource(R.drawable.progress_animation)
            addView(this)
            AnimatedVectorDrawableCompat.registerAnimationCallback(drawable, callback)
        }
    }

    fun play() {
        inProgress = true
        (imageView?.drawable as Animatable).start()
        (context as AppCompatActivity).runOnUiThread {
            imageView?.show(true)
        }
    }

    fun pause() {
        inProgress = false
        (imageView?.drawable as Animatable).stop()
        (context as AppCompatActivity).runOnUiThread {
            imageView?.show(false)
        }
        AnimatedVectorDrawableCompat.unregisterAnimationCallback(imageView?.drawable, callback)
    }
}