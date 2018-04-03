package com.gc.magnetloading

import android.util.TypedValue
import android.view.View
import android.view.ViewParent
import android.content.res.TypedArray




/**
 * Created by navas on 30/3/18.
 */
fun View.dpToPx(dp : Float) : Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()

fun ViewParent.toView() : View = this as View

fun View.getRelativeTop() : Int {
    return if (id == android.R.id.content)
        top
    else
        top + parent.toView().getRelativeTop()
}

fun View.getRelativeLeft() : Int {
    return if (id == android.R.id.content)
        left
    else
        left + parent.toView().getRelativeLeft()
}

fun View.getCenterX() : Int = (x + this.width / 2).toInt()

fun View.getCenterY() : Int = (y + this.height / 2).toInt()

fun View.getRelativeCenterX() : Int = getRelativeLeft() + this.width / 2

fun View.getRelativeCenterY() : Int = getRelativeTop() + this.height / 2

fun View.getPrimaryColor() : Int = getThemeColor(R.attr.colorPrimary)

fun View.getPrimaryDarkColor() : Int = getThemeColor(R.attr.colorPrimaryDark)

fun View.getAccentColor() : Int = getThemeColor(R.attr.colorAccent)

private fun View.getThemeColor(themeColorId : Int) : Int {
    val typedValue = TypedValue()
    val a = context.obtainStyledAttributes(typedValue.data, intArrayOf(themeColorId))
    val color = a.getColor(0, 0)
    a.recycle()
    return color
}

fun View.getTimeAnimShort() : Long = getIntegerResource(android.R.integer.config_shortAnimTime).toLong()

fun View.getTimeAnimMedium() : Long = getIntegerResource(android.R.integer.config_mediumAnimTime).toLong()

fun View.getTimeAnimLong() : Long = getIntegerResource(android.R.integer.config_longAnimTime).toLong()

fun View.getIntegerResource(idResource : Int) : Int = resources.getInteger(idResource)

