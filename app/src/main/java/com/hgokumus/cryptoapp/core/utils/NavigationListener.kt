package com.hgokumus.cryptoapp.core.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

interface NavigationListener {
    fun navigateToFragment(
        activity: FragmentActivity,
        fragment: Fragment,
        @IdRes containerViewId: Int,
        tag: String
    )
}