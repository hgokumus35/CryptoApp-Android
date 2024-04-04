package com.hgokumus.cryptoapp.cryptoList.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hgokumus.cryptoapp.R
import com.hgokumus.cryptoapp.core.utils.Resource
import com.hgokumus.cryptoapp.core.utils.viewBinding
import com.hgokumus.cryptoapp.cryptoList.viewmodel.CryptoListViewModel
import com.hgokumus.cryptoapp.databinding.FragmentCryptoListBinding
import org.koin.android.ext.android.inject

class CryptoListFragment : Fragment() {

    companion object {
        fun newInstance() = CryptoListFragment()
    }

    private val binding by viewBinding(FragmentCryptoListBinding::bind)
    private val cryptoListViewModel by inject<CryptoListViewModel>()
    private val cryptoListAdapter by lazy(LazyThreadSafetyMode.NONE) { CryptoListAdapter() }

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
        setObservers()
        setAdapters()
    }

    private fun setAdapters() {
        with(binding.cryptoListRecyclerView) {
            adapter = cryptoListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            itemAnimator = null
        }
    }

    private fun setObservers() {
        cryptoListViewModel.getCryptoListEvent.observe(viewLifecycleOwner) { response ->
            if (response is Resource.Error) {
                println("ERROR")
            } else {
                cryptoListAdapter.setItems(response?.data?.data?.coins)
            }
        }
    }
}