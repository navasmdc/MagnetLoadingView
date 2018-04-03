package com.gc.magnetloading

import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * Created by navas on 2/4/18.
 */
class PulseView(context: Context?, origin : View) : FrameLayout(context) {

    private val pulse1 = View(context)
    private val pulse2 = View(context)

    private val TIME_PULSE_ANIMATION : Long = 1500

    private var stopAnimations = false

    init {
        configurePulseView(origin, pulse1)
        configurePulseView(origin, pulse1)
    }


    private fun configurePulseView(origin : View, viewToConfigure : View){
        viewToConfigure.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,   ViewGroup.LayoutParams.MATCH_PARENT)
        viewToConfigure.setBackgroundResource(R.drawable.background_pulse)
        addView(viewToConfigure)
        viewToConfigure.alpha = 0f
    }

    fun stopAnimations() { stopAnimations = false }

    fun startAnimations() {
        generatePulseAnimation(pulse1).start()
    }

    private fun generatePulseAnimation(view : View) : ValueAnimator {
        val valueAnimator = ValueAnimator.ofFloat(1f, 0.4f)
        var launchedAnim = false
        valueAnimator.addUpdateListener {
            if(view.alpha == 0f) view.alpha = 1f
            view.scaleX = valueAnimator.animatedValue as Float
            view.scaleY = valueAnimator.animatedValue as Float
            if(valueAnimator.animatedValue as Float <= 0.5 && !launchedAnim) {
                launchedAnim = true
                if (view == pulse1) generatePulseAnimation(pulse2).start()
                else generatePulseAnimation(pulse1).start()
            }
        }
        valueAnimator.duration = TIME_PULSE_ANIMATION
        return valueAnimator
    }

}