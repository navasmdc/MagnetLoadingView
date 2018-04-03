package com.gc.magnetloading

import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout

/**
 * Created by navas on 31/3/18.
 */
internal class MagnetContainerView(context: Context, private val origin: MagnetLoadingButtonView) : Dialog(context, android.R.style.Theme_Translucent)
{
    private val container = FrameLayout(context)
    private val itemsContainer = MagnetItemsContainerView(context)
    private val buttonContainer = FrameLayout(context)
    private lateinit var button : MagnetLoadingButtonView
    private lateinit var pulseView : PulseView

    init {
        configureContainers(origin)
        configurePulse(origin)
        configureButton(origin)
    }

    //region Configure Views

    private fun configureContainers(origin : MagnetLoadingButtonView){

        container.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        itemsContainer.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        val color = origin.color
        itemsContainer.setBackgroundColor(
                Color.argb(200 , Color.red(color), Color.green(color), Color.blue(color))
        )
        itemsContainer.alpha = 0f
        buttonContainer.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        container.addView(itemsContainer)
        container.addView(buttonContainer)
    }

    private fun configurePulse(origin : View){
        pulseView = PulseView(context, origin)
        pulseView.layoutParams = ViewGroup.LayoutParams((origin.height * 2.5).toInt(), (origin.height * 2.5).toInt())
        pulseView.x = (origin.getRelativeCenterX() - (origin.height * 2.5) / 2).toFloat()
        pulseView.y = (origin.getRelativeCenterY() - (origin.height * 2.5)  / 2).toFloat()
    }

    private fun configureButton(origin : MagnetLoadingButtonView){
        button = MagnetLoadingButtonView(context, origin.attrs, origin)
        buttonContainer.addView(button)
        button.isClickable = false
        button.y = origin.getRelativeTop().toFloat()
        button.left = origin.getRelativeLeft()
    }

    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(container)
        startAnimations()
    }

    //region Animations

    private fun startAnimations(){
        itemsContainer.startAnimation(button, { pulseView.startAnimations() })
    }

    override fun dismiss() {
        super.dismiss()
        origin.visibility = View.VISIBLE
    }
    //endregion


}