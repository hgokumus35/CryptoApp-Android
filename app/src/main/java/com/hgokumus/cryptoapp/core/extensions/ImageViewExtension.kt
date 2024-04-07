package com.hgokumus.cryptoapp.core.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hgokumus.cryptoapp.R

fun ImageView.loadUrl(url: String?) {
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .error(R.drawable.ic_error)
        .into(this)
}