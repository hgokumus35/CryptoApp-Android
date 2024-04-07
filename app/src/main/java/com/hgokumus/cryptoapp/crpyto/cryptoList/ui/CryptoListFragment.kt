package com.hgokumus.cryptoapp.crpyto.cryptoList.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hgokumus.cryptoapp.R
import com.hgokumus.cryptoapp.core.extensions.navigateToFragment
import com.hgokumus.cryptoapp.core.utils.Resource
import com.hgokumus.cryptoapp.core.utils.viewBinding
import com.hgokumus.cryptoapp.crpyto.cryptoDetail.ui.CryptoDetailFragment
import com.hgokumus.cryptoapp.crpyto.cryptoList.viewmodel.CryptoListViewModel
import com.hgokumus.cryptoapp.databinding.FragmentCryptoListBinding
import org.koin.android.ext.android.inject

class CryptoListFragment : Fragment() {

    companion object {
        const val CRYPTO_LIST_FRAGMENT_TAG = "CRYPTO_LIST_FRAGMENT"
        fun newInstance() = CryptoListFragment()
    }

    private val binding by viewBinding(FragmentCryptoListBinding::bind)
    private val cryptoListViewModel by inject<CryptoListViewModel>()
    private val cryptoListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CryptoListAdapter(
            onRowClick = { uuid ->
                navigateToFragment(
                    requireActivity(),
                    CryptoDetailFragment.newInstance(uuid),
                    R.id.homeContainer,
                    CRYPTO_LIST_FRAGMENT_TAG
                )
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_crypto_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cryptoListViewModel.getCryptoList()
        setAdapters()
        setObservers()
        setListeners()
    }

    private fun setAdapters() {
        with(binding.cryptoListRecyclerView) {
            adapter = cryptoListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            itemAnimator = null
        }
        setSpinnerAdapter()
    }

    private fun setListeners() {
        binding.filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                cryptoListViewModel.getCryptoList(cryptoListViewModel.filterTypes[position])
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setObservers() {
        cryptoListViewModel.getCryptoListEvent.observe(viewLifecycleOwner) { response ->
            if (response is Resource.Error) {
                println("ERROR")
            } else {
                response?.data?.data?.coins?.let { cryptoList ->
                    cryptoListViewModel.getAllCryptoWithFavorites(cryptoList)
                }
            }
        }
        cryptoListViewModel.getAllFavoritesEvent.observe(viewLifecycleOwner) {
            println("Data: $it")
            cryptoListAdapter.setItems(it)
            cryptoListAdapter.notifyDataSetChanged()
        }
    }

    private fun setSpinnerAdapter() {
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            R.layout.custom_spinner_layout,
            cryptoListViewModel.filterTypes
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.filterSpinner.adapter = spinnerAdapter
    }
}