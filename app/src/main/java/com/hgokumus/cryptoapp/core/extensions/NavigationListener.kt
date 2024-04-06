package com.hgokumus.cryptoapp.core.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.hgokumus.cryptoapp.R

fun navigateToFragment(
    activity: FragmentActivity,
    fragment: Fragment,
    containerViewId: Int,
    tag: String
) {
    activity.supportFragmentManager.beginTransaction()
        .setReorderingAllowed(true)
        .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
        .replace(containerViewId, fragment)
        .addToBackStack(tag)
        .commit()
}