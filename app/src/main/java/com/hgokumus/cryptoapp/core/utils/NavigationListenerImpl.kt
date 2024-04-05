package com.hgokumus.cryptoapp.core.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class NavigationListenerImpl : NavigationListener {
    override fun navigateToFragment(
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
}