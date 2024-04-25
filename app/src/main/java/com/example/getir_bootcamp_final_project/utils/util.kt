package com.example.getir_bootcamp_final_project.utils

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.PendingIntent.getActivity
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.example.getir_bootcamp_final_project.model.Product

const val DISCOUNT_VALUE = 15

fun applyDiscount(price: Double, discount: Int = DISCOUNT_VALUE): Double {
    return (price * (100 - discount)) / 100
}

fun Double.formatDouble(
    digitNum: Short = 0,
): String{
    return String.format("%.${digitNum}f", this).replace("-", "")
}

fun handleProductImageUrl(product: Product): String?{
    return when{
        !product.imageURL.isNullOrEmpty() -> product.imageURL
        !product.squareThumbnailURL.isNullOrEmpty() -> product.squareThumbnailURL
        else -> product.thumbnailURL
    }
}

fun expand(parent: View) {
    parent.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    val targetHeight = parent.measuredHeight

    // Older versions of android (pre API 21) cancel animations for views with a height of 0.
    parent.layoutParams.height = 1
    parent.visibility = View.VISIBLE
    val va = ValueAnimator.ofInt(1, targetHeight)
    va.addUpdateListener { animation ->
        parent.layoutParams.height = (animation.getAnimatedValue() as Int)
        parent.requestLayout()
    }
    va.addListener(object : Animator.AnimatorListener {

        override fun onAnimationStart(animation: Animator) {parent.visibility = View.VISIBLE}

        override fun onAnimationEnd(animation: Animator) { parent.layoutParams.height = LayoutParams.WRAP_CONTENT }

        override fun onAnimationCancel(animation: Animator) {}

        override fun onAnimationRepeat(animation: Animator) {}
    })
    va.setDuration(1000)
    va.interpolator = OvershootInterpolator()
    va.start()
}

fun collapse(v: View) {
    val initialHeight = v.measuredHeight
    val va = ValueAnimator.ofInt(initialHeight, 0)
    va.addUpdateListener { animation ->
        v.layoutParams.height = (animation.getAnimatedValue() as Int)
        v.requestLayout()
    }
    va.addListener(object : Animator.AnimatorListener {
        override fun onAnimationEnd(animation: Animator) {
            v.visibility = View.GONE
        }

        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
    })
    va.setDuration(1000)
    va.interpolator = DecelerateInterpolator()
    va.start()
}

fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
    println(currentDestination?.id)
}

fun clearBackStack(fm: FragmentManager){
    for (i in 0 until fm.backStackEntryCount) {
        fm.popBackStack()
    }
}