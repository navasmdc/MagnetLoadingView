package com.gc.magnetloading

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView

/**
 * Created by navas on 2/4/18.
 */
internal class MagnetItemsContainerView(context: Context?) : FrameLayout(context) {
    
    lateinit var magnetView : View
    var stopAnimation = false

    fun stopAnimation() { stopAnimation = true }
    
    fun startAnimation(magnetView : View, endListener : (() -> Unit)?){
        animate()
                .alpha(1f)
                .setDuration(getTimeAnimLong())
                .setListener(EndAnimationListener({
                    endListener?.invoke()
                    itemsAnimation()
                }))
                .start()
    }

    private fun itemsAnimation(){
        Handler().postDelayed(
                {
                    if(!stopAnimation) {
                        generateMagnetItem().generateItemAnimation()
                        itemsAnimation()
                    }
                }
                ,300)
    }
    
    private fun generateMagnetItem() : ImageView {
        val item = ImageView(context)
        item.setImageResource(R.drawable.ic_data)
        item.layoutParams = ViewGroup.LayoutParams(dpToPx(40f),dpToPx(40f))
        item.setColorFilter(
                if(((Math.random() * 2).toInt()+1) == 2) Color.WHITE
                else getPrimaryDarkColor()
        )
        addView(item)
        item.alpha = 0.5f
        item.x = (Math.random() * width).toFloat()
        item.x =
                if(item.x > width / 2) (width + item.dpToPx(40f)).toFloat()
                else (- item.dpToPx(40f)).toFloat()
        item.y = (Math.random() * height).toFloat()
        item.y =
                if(item.y > height / 2) (height / 2).toFloat()
                else item.y
        return item
    }

    private fun ImageView.generateItemAnimation(){
        animate()
                .x((magnetView.getCenterX() - dpToPx(20f)).toFloat())
                .y((magnetView.getCenterY() - dpToPx(20f)).toFloat())
                .scaleX(0.5f)
                .scaleY(0.5f)
                .setDuration(
                        ((Math.hypot((magnetView.x -x).toDouble(), (magnetView.y - y).toDouble()) * 3000) / magnetView.y).toLong()
                )
                .setListener(EndAnimationListener({removeView(this)}))
                .start()
    }
    
}