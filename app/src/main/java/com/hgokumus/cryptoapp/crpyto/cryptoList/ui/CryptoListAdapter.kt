package com.hgokumus.cryptoapp.crpyto.cryptoList.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hgokumus.cryptoapp.R
import com.hgokumus.cryptoapp.core.extensions.Constants.CRYPTO_PRICE_FORMAT
import com.hgokumus.cryptoapp.core.extensions.Constants.ZERO_DOUBLE
import com.hgokumus.cryptoapp.core.extensions.formatChangeAndPrice
import com.hgokumus.cryptoapp.core.extensions.formatNumber
import com.hgokumus.cryptoapp.core.extensions.loadUrl
import com.hgokumus.cryptoapp.core.extensions.orElse
import com.hgokumus.cryptoapp.databinding.CryptoListItemBinding
import com.hgokumus.cryptoapp.network.response.Crypto

class CryptoListAdapter(
    private val onRowClick: (uuid: String, id: Long, isFavorite: Boolean) -> Unit
) : ListAdapter<Crypto, CryptoListAdapter.CryptoListViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Crypto>() {
            override fun areItemsTheSame(oldItem: Crypto, newItem: Crypto) = oldItem.uuid == newItem.uuid
            override fun areContentsTheSame(oldItem: Crypto, newItem: Crypto) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoListViewHolder {
        val binding = CryptoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoListViewHolder, position: Int) = holder.bind(getItem(position))

    internal fun setItems(cryptoListResponse: List<Crypto>?) {
        submitList(cryptoListResponse?.toMutableList())
    }

    inner class CryptoListViewHolder(private val binding: CryptoListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cryptoListResponse: Crypto) = with(binding) {
            root.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    cryptoListResponse.uuid?.let { uuid -> onRowClick.invoke(uuid, cryptoListResponse.id, cryptoListResponse.isFavorite) }
                }
            }
            cryptoImage.loadUrl(cryptoListResponse.iconUrl)
            cryptoAbbreviation.text = cryptoListResponse.symbol
            cryptoName.text = cryptoListResponse.name
            cryptoPrice.text = String.format(
                itemView.context.getString(R.string.crypto_price),
                cryptoListResponse.price?.formatNumber(CRYPTO_PRICE_FORMAT)
            )
            cryptoChangeRate.text = String.format(
                itemView.context.getString(R.string.crypto_change_rate),
                formatChangeAndPrice(
                    cryptoListResponse.change?.toDouble().orElse { ZERO_DOUBLE },
                    cryptoListResponse.price?.toDouble().orElse { ZERO_DOUBLE }
                )
            )
            cryptoChangeRate.setTextColor(
                when {
                    cryptoListResponse.change?.toDouble().orElse { ZERO_DOUBLE } > ZERO_DOUBLE -> Color.GREEN
                    cryptoListResponse.change?.toDouble().orElse { ZERO_DOUBLE } < ZERO_DOUBLE -> Color.RED
                    else -> Color.BLACK
                }
            )
            favoriteStarLogo.isVisible = cryptoListResponse.isFavorite
        }
    }
}