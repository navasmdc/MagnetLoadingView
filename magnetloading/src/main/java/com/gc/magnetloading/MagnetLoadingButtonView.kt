package com.gc.magnetloading

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

/**
 * Created by navas on 30/3/18.
 */
class MagnetLoadingButtonView(context: Context?, var attrs: AttributeSet?, origin : MagnetLoadingButtonView?) : RelativeLayout(context, attrs), View.OnClickListener {


    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, null)

    private val layout = RelativeLayout(context)
    private val backgroundView = View(context)
    private val textView = TextView(context)
    private val iconView = ImageView(context)

    var color = getPrimaryColor()


    val ANIMATION_TIME : Long = getTimeAnimShort()
    val ANIMATION_SMALL_TIME : Long = getTimeAnimShort()

    private var clickListener : OnClickListener? = null

    init {
        configureBackgroundView()
        configureLayout()
        configureIcon(R.drawable.ic_download, getPrimaryColor())
        configureText("Demo text", Color.WHITE)
        super.setOnClickListener(this)
        post {
            val _width =
                    if(origin != null) origin.width - dpToPx(20f)
                    else width - dpToPx(20f)
            layoutParams.height = dpToPx(60f)
            layoutParams = layoutParams
            layout.layoutParams.height = dpToPx(60f)
            layout.layoutParams.width = _width
            layout.layoutParams = layout.layoutParams
            backgroundView.layoutParams.height = dpToPx(60f)
            backgroundView.layoutParams.width = _width
            backgroundView.layoutParams = backgroundView.layoutParams
            if(origin != null){
                postDelayed({origin.visibility = View.INVISIBLE},50)
                startAnimations()
            }
        }
    }

    //region Configure Views

    private fun configureBackgroundView(){
        backgroundView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)
        (backgroundView.layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.CENTER_IN_PARENT)
        backgroundView.setBackgroundResource(R.drawable.background_button)
        (backgroundView.background as GradientDrawable).setColor(Color.WHITE)
        addView(backgroundView)
        backgroundView.alpha = 0f
    }

    private fun configureLayout(){
        layout.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)
        (layout.layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.CENTER_IN_PARENT)
        layout.setBackgroundResource(R.drawable.background_button)
        (layout.background as GradientDrawable).setColor(color)
        addView(layout)
    }

    private fun configureIcon(drawableResource : Int, color :Int){
        iconView.layoutParams = LayoutParams(dpToPx(30f), dpToPx(30f))
        (iconView.layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.CENTER_IN_PARENT)
        iconView.alpha = 0f
        iconView.setImageResource(drawableResource)
        iconView.setColorFilter(color)
        layout.addView(iconView)
    }

    private fun configureText(text : String, color : Int){
        textView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)
        (textView.layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.CENTER_IN_PARENT)
        textView.text = text
        textView.setTextColor(color)
        layout.addView(textView)
    }

    //endregion

    override fun setOnClickListener(l: OnClickListener?) {
        clickListener = l
    }



    override fun onClick(v: View?) {
        clickListener?.onClick(v)
        showMagnetView()
//        startAnimations()
    }

    private fun showMagnetView(){
        MagnetContainerView(context, this).show()
    }

    //region Animations

    fun startAnimations(){
        backgroundView.alpha = 1f
        expandAnimation()
    }

    private fun expandAnimation() : ValueAnimator {
        val animation = ValueAnimator.ofInt(layout.layoutParams.width, layout.width + dpToPx(20f))
        animation.addUpdateListener {va ->
            layout.layoutParams.width = va.animatedValue as Int
            layout.layoutParams = layout.layoutParams
            backgroundView.layoutParams.width = va.animatedValue as Int
            backgroundView.layoutParams = layout.layoutParams
        }
        animation.addListener(EndAnimationListener({ collapseAnimation()}))
        animation.duration = ANIMATION_SMALL_TIME
        animation.start()
        return animation
    }

    private fun collapseAnimation()  {
        generateCollapseAnimation().start()
        generateColorAnimation().start()
        textView.animate().alpha(0f).setDuration(ANIMATION_TIME).setInterpolator(AccelerateInterpolator()).start()
        iconView.animate().alpha(1f).setDuration(ANIMATION_TIME).setInterpolator(AccelerateInterpolator()).start()
    }

    private fun generateCollapseAnimation() : ValueAnimator {
        val animation = ValueAnimator.ofInt(layout.layoutParams.width, dpToPx(60f))
        animation.addUpdateListener { va ->
            layout.layoutParams.width = va.animatedValue as Int
            layout.layoutParams = layout.layoutParams
            backgroundView.layoutParams.width = va.animatedValue as Int
            backgroundView.layoutParams = backgroundView.layoutParams
        }
        animation.duration = ANIMATION_TIME
        animation.interpolator = AccelerateInterpolator()
        return animation
    }

    private fun generateColorAnimation() : ValueAnimator {
        val animation = ValueAnimator()
        animation.setEvaluator(ArgbEvaluator())
        animation.setIntValues(255, 0)
        animation.addUpdateListener { va -> (
                layout.background as GradientDrawable).setColor(
                Color.argb(va.animatedValue as Int,Color.red(color), Color.green(color),Color.blue(color))) }
        animation.duration = ANIMATION_TIME
        return animation
    }

    //endregion

}