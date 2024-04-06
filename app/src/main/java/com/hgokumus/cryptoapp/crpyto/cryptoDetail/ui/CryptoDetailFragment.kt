package com.hgokumus.cryptoapp.crpyto.cryptoDetail.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.hgokumus.cryptoapp.R
import com.hgokumus.cryptoapp.core.extensions.Constants.CRYPTO_PRICE_FORMAT
import com.hgokumus.cryptoapp.core.extensions.formatChangeAndPrice
import com.hgokumus.cryptoapp.core.extensions.formatNumber
import com.hgokumus.cryptoapp.core.extensions.orElse
import com.hgokumus.cryptoapp.core.utils.Resource
import com.hgokumus.cryptoapp.core.utils.viewBinding
import com.hgokumus.cryptoapp.crpyto.cryptoDetail.viewmodel.CryptoDetailViewModel
import com.hgokumus.cryptoapp.databinding.FragmentCryptoDetailBinding
import com.hgokumus.cryptoapp.network.request.CryptoDetailRequest
import com.hgokumus.cryptoapp.network.request.PriceHistoryRequest
import com.hgokumus.cryptoapp.network.response.CryptoDetail
import com.hgokumus.cryptoapp.network.response.PriceHistory
import org.koin.android.ext.android.inject

class CryptoDetailFragment : Fragment() {

    companion object {
        fun newInstance(uuid: String) = CryptoDetailFragment().apply {
            this.uuid = uuid
        }
    }

    private var uuid: String = ""
    private val binding by viewBinding(FragmentCryptoDetailBinding::bind)
    private val cryptoDetailViewModel by inject<CryptoDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_crypto_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cryptoDetailViewModel.getCryptoDetail(CryptoDetailRequest(uuid))
        cryptoDetailViewModel.getPriceHistory(PriceHistoryRequest(uuid))
        setObservers()
        setListeners()
    }

    private fun setListeners() {
        binding.appbar.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setObservers() {
        cryptoDetailViewModel.cryptoDetailUIVisibility.observe(viewLifecycleOwner) {
            binding.cryptoDetailMainLayout.isVisible = it
        }
        cryptoDetailViewModel.getCryptoDetailEvent.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Error -> println("ERROR")
                is Resource.Success -> {
                    resource.data?.data?.coin?.let { initializeUI(it) }
                    cryptoDetailViewModel.setCryptoDetailUIVisibility(true)
                }
                else -> Unit
            }
        }
        cryptoDetailViewModel.getPriceHistoryEvent.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Error -> println("ERROR")
                is Resource.Success -> resource.data?.data?.let { initCryptoChart(it) }
                else -> Unit
            }
        }
    }

    private fun initializeUI(cryptoDetail: CryptoDetail) {
        with(binding) {
            appbar.cryptoAbbreviationDetail.text = cryptoDetail.symbol
            appbar.cryptoNameDetail.text = cryptoDetail.name
            priceLayout.cryptoRank.text = String.format(
                requireContext().getString(R.string.crypto_detail_rank),
                cryptoDetail.rank
            )
            priceLayout.cryptoPrice.text = String.format(
                requireContext().getString(R.string.crypto_price),
                cryptoDetail.price?.formatNumber(CRYPTO_PRICE_FORMAT)
            )
            priceLayout.changeRate.text = String.format(
                requireContext().getString(R.string.crypto_change_rate),
                formatChangeAndPrice(
                    cryptoDetail.change?.toDouble().orElse { 0.0 },
                    cryptoDetail.price?.toDouble().orElse { 0.0 }
                )
            )
            priceLayout.dailyHighPrice.text = String.format(
                requireContext().getString(R.string.crypto_detail_daily_high_price),
                cryptoDetail.sparkline?.maxOfOrNull { it.toDouble() }.toString().formatNumber(CRYPTO_PRICE_FORMAT)
            )
            priceLayout.dailyLowPrice.text = String.format(
                requireContext().getString(R.string.crypto_detail_daily_low_price),
                cryptoDetail.sparkline?.minOfOrNull { it.toDouble() }.toString().formatNumber(CRYPTO_PRICE_FORMAT)
            )
        }
    }

    private fun initCryptoChart(priceHistory: PriceHistory) {
        val chartDataSet = LineDataSet(cryptoDetailViewModel.cryptoChartUIModel(priceHistory.history), "")
        chartDataSet.color = Color.MAGENTA
        chartDataSet.lineWidth = 2f

        val lineData = LineData(chartDataSet)
        with(binding.cryptoChart) {
            data = lineData
            description.isEnabled = false
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            axisRight.isEnabled = false
            invalidate()
        }
    }
}