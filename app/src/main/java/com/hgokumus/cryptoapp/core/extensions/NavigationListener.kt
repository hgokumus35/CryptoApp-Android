package com.hgokumus.cryptoapp.core.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun navigateToFragment(
    activity: FragmentActivity,
    fragment: Fragment,
    containerViewId: Int,
    tag: String
) {
    activity.supportFragmentManager.beginTransaction()
        .setReorderingAllowed(true)
        .replace(containerViewId, fragment)
        .addToBackStack(tag)
        .commit()
}