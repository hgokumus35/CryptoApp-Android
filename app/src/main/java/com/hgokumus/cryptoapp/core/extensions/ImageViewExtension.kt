package com.hgokumus.cryptoapp.core.extensions

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.hgokumus.cryptoapp.R
import de.hdodenhof.circleimageview.CircleImageView

fun CircleImageView.loadUrl(url: String?) {
    val requestOptions = RequestOptions()
        .override(24, 24)
        .transform(CenterCrop())
    Glide.with(context)
        .load(url)
        .apply(requestOptions)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .error(R.drawable.ic_error)
        .into(this)
}