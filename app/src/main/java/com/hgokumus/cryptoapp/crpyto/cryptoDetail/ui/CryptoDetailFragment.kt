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
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.hgokumus.cryptoapp.R
import com.hgokumus.cryptoapp.core.extensions.*
import com.hgokumus.cryptoapp.core.extensions.Constants.CRYPTO_PRICE_FORMAT
import com.hgokumus.cryptoapp.core.extensions.Constants.EMPTY_STR
import com.hgokumus.cryptoapp.core.extensions.Constants.TWO_FLOAT
import com.hgokumus.cryptoapp.core.extensions.Constants.ZERO_DOUBLE
import com.hgokumus.cryptoapp.core.extensions.Constants.ZERO_INT
import com.hgokumus.cryptoapp.core.utils.Resource
import com.hgokumus.cryptoapp.core.utils.viewBinding
import com.hgokumus.cryptoapp.crpyto.cryptoDetail.viewmodel.CryptoDetailViewModel
import com.hgokumus.cryptoapp.databinding.FragmentCryptoDetailBinding
import com.hgokumus.cryptoapp.network.request.CryptoDetailRequest
import com.hgokumus.cryptoapp.network.request.PriceHistoryRequest
import com.hgokumus.cryptoapp.network.response.CryptoDetail
import com.hgokumus.cryptoapp.network.response.PriceHistory
import com.hgokumus.cryptoapp.room.entity.FavoriteCryptoEntity
import org.koin.android.ext.android.inject

class CryptoDetailFragment : Fragment() {

    companion object {
        fun newInstance(uuid: String, id: Long, isFavorite: Boolean) = CryptoDetailFragment().apply {
            this.uuid = uuid
            this.id = id
            this.isFavorite = isFavorite
        }
    }

    private var uuid: String = EMPTY_STR
    private var id: Long = ZERO_INT.toLong()
    private var isFavorite: Boolean = false
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
        binding.addToFavoritesButton.setOnClickListener {
            cryptoDetailViewModel.addToFavorites(
                FavoriteCryptoEntity(
                    name = cryptoDetailViewModel.cryptoDetail?.name,
                    iconUrl = cryptoDetailViewModel.cryptoDetail?.iconUrl,
                    rank = cryptoDetailViewModel.cryptoDetail?.rank
                )
            )
        }
        binding.removeFromFavoritesButton.setOnClickListener {
            cryptoDetailViewModel.removeFromFavorites(
                FavoriteCryptoEntity(
                    id = id,
                    name = cryptoDetailViewModel.cryptoDetail?.name,
                    iconUrl = cryptoDetailViewModel.cryptoDetail?.iconUrl,
                    rank = cryptoDetailViewModel.cryptoDetail?.rank
                )
            )
        }
    }

    private fun setObservers() {
        cryptoDetailViewModel.cryptoDetailUIVisibility.observe(viewLifecycleOwner) {
            binding.cryptoDetailMainLayout.isVisible = it
        }
        cryptoDetailViewModel.addRemoveButtonVisibility.observe(viewLifecycleOwner) {
            binding.addToFavoritesButton.isVisible = !it
            binding.removeFromFavoritesButton.isVisible = it
        }
        cryptoDetailViewModel.getCryptoDetailEvent.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Error -> context?.showErrorDialog(R.string.crypto_dialog_error_message) { activity?.onBackPressed() }
                is Resource.Success -> {
                    resource.data?.data?.coin?.let {
                        initializeUI(it)
                        cryptoDetailViewModel.cryptoDetail = it
                    }
                    cryptoDetailViewModel.setCryptoDetailUIVisibility(true)
                }
                else -> Unit
            }
        }
        cryptoDetailViewModel.getPriceHistoryEvent.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Error -> context?.showErrorDialog(R.string.crypto_dialog_error_message) { activity?.onBackPressed() }
                is Resource.Success -> resource.data?.data?.let { initCryptoChart(it) }
                else -> Unit
            }
        }
        cryptoDetailViewModel.addToFavoritesEvent.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                true -> context?.showSuccessDialog(R.string.crypto_add_to_favorites_success_dialog_message) {
                    cryptoDetailViewModel.setAddRemoveButtonVisibility(true)
                }
                false -> context?.showErrorDialog(R.string.crypto_add_to_favorites_error_dialog_message)
            }
        }
        cryptoDetailViewModel.removeFromFavoritesEvent.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                true -> context?.showSuccessDialog(R.string.crypto_remove_from_favorites_success_dialog_message) {
                    cryptoDetailViewModel.setAddRemoveButtonVisibility(false)
                }
                false -> context?.showErrorDialog(R.string.crypto_remove_from_favorites_error_dialog_message)
            }
        }
    }

    private fun initializeUI(cryptoDetail: CryptoDetail) = with(binding) {
        initAppBar(cryptoDetail)
        initPriceLayout(cryptoDetail)
        cryptoDetailViewModel.setAddRemoveButtonVisibility(isFavorite)
    }

    private fun initAppBar(cryptoDetail: CryptoDetail) = with(binding.appbar) {
        cryptoAbbreviationDetail.text = cryptoDetail.symbol
        cryptoNameDetail.text = cryptoDetail.name
        favoriteStarLogoDetail.visibility = when(isFavorite) {
            true -> View.VISIBLE
            false -> View.INVISIBLE
        }
    }

    private fun initCryptoChart(priceHistory: PriceHistory) {
        val chartDataSet = LineDataSet(cryptoDetailViewModel.cryptoChartUIModel(priceHistory.history), EMPTY_STR)
        chartDataSet.color = Color.MAGENTA
        chartDataSet.lineWidth = TWO_FLOAT

        val lineData = LineData(chartDataSet)
        with(binding.cryptoChart) {
            data = lineData
            animateX(1500)
            setTouchEnabled(true)
            setPinchZoom(true)
            description.isEnabled = false
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.valueFormatter = IndexAxisValueFormatter(timestampToDateString(cryptoDetailViewModel.timestamps))
            axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            axisRight.isEnabled = false
            invalidate()
        }
    }

    private fun initPriceLayout(cryptoDetail: CryptoDetail) = with(binding.priceLayout) {
        cryptoRank.text = String.format(requireContext().getString(R.string.crypto_detail_rank), cryptoDetail.rank)
        cryptoPrice.text = String.format(requireContext().getString(R.string.crypto_price), cryptoDetail.price?.formatNumber(CRYPTO_PRICE_FORMAT))
        changeRate.text = String.format(
            requireContext().getString(R.string.crypto_change_rate),
            formatChangeAndPrice(
                cryptoDetail.change?.toDouble().orElse { ZERO_DOUBLE },
                cryptoDetail.price?.toDouble().orElse { ZERO_DOUBLE }
            )
        )
        changeRate.setTextColor(
            when {
                cryptoDetail.change?.toDouble().orElse { ZERO_DOUBLE } > ZERO_DOUBLE -> Color.GREEN
                cryptoDetail.change?.toDouble().orElse { ZERO_DOUBLE } < ZERO_DOUBLE -> Color.RED
                else -> Color.BLACK
            }
        )
        dailyHighPrice.text = String.format(
            requireContext().getString(R.string.crypto_detail_daily_high_price),
            cryptoDetail.sparkline?.maxOfOrNull { it.toDouble().orElse { ZERO_DOUBLE } }.toString().formatNumber(CRYPTO_PRICE_FORMAT)
        )
        dailyLowPrice.text = String.format(
            requireContext().getString(R.string.crypto_detail_daily_low_price),
            cryptoDetail.sparkline?.minOfOrNull { it.toDouble().orElse { ZERO_DOUBLE } }.toString().formatNumber(CRYPTO_PRICE_FORMAT)
        )
    }
}