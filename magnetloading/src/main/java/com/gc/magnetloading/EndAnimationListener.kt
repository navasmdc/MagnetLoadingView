package com.gc.magnetloading

import android.animation.Animator
import android.view.animation.Animation

/**
 * Created by navas on 30/3/18.
 */
class EndAnimationListener(private val action : () -> Unit) : Animator.AnimatorListener {

    override fun onAnimationRepeat(p0: Animator?) {}

    override fun onAnimationEnd(p0: Animator?) { action()}

    override fun onAnimationCancel(p0: Animator?) {}

    override fun onAnimationStart(p0: Animator?) {}

}