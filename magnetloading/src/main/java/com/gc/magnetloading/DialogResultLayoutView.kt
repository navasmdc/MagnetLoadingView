package com.gc.magnetloading

import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout

/**
 * Created by navas on 2/4/18.
 */
internal class DialogResultLayoutView(context: Context?) : RelativeLayout(context) {

    var animate = false

    var finalWidth : Int = 0
    var finalHeight : Int = 0

    var container = FrameLayout(context)

    init {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        container.setBackgroundResource(R.drawable.background_button)
        addView(container)
        container.layoutParams = LayoutParams(dpToPx(60f), dpToPx(60f))
        container.alpha = 0f
    }

    var contentView: View? = null
        set(value) {
            value?.alpha = 0f
            container.addView(value)
            value?.post {
                finalWidth = value.width
                finalHeight = value.height
                if(animate) startAnimation()
            }
        }

    fun configureView(x : Float, y : Float){
        container.x = x
        container.y = y
    }

    //reigon Animations
    fun startAnimation(){
        animate = true
        if(finalWidth != 0){
            container.animate().alpha(1f).setDuration(getTimeAnimShort())
                    .setListener(EndAnimationListener({
                        (container.layoutParams as LayoutParams).addRule(CENTER_IN_PARENT)
                        expandWithAnimation()
                    })).start()
        }
    }

    private fun expandWithAnimation(){
        val animation = ValueAnimator.ofInt(container.width, finalWidth)
        animation.duration = getTimeAnimShort()
        animation.addUpdateListener {
            container.layoutParams.height = animation.animatedValue as Int
            container.layoutParams = container.layoutParams
        }
        animation.addListener(EndAnimationListener({ expandHeightAnimation()}))
        animation.start()
    }

    private fun expandHeightAnimation(){
        val animation = ValueAnimator.ofInt(container.height, finalHeight)
        animation.duration = getTimeAnimShort()
        animation.addUpdateListener {
            container.layoutParams.height = animation.animatedValue as Int
            container.layoutParams = container.layoutParams
        }
        animation.addListener(EndAnimationListener({
            showContentViewAnimation()
        }))
        animation.start()
    }

    private fun showContentViewAnimation(){
        contentView?.animate()!!.alpha(1f).setDuration(getTimeAnimShort()).start()
    }
    //endregion




}