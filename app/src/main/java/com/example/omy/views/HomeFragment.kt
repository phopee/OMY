package com.example.omy.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omy.R
import com.example.omy.adapters.CurrencyRatesAdapter
import com.example.omy.databinding.HomeFragmentBinding
import com.example.omy.models.HomeViewModel
import com.example.omy.models.SharedViewModel
import kotlinx.coroutines.launch
import java.lang.Long.parseLong
import java.text.SimpleDateFormat
import java.util.*

private lateinit var binding :HomeFragmentBinding

class HomeFragment : Fragment() {
    private val sharedviewModel: SharedViewModel by activityViewModels()
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        val root = binding.root
        binding.lifecycleOwner = this
        binding.view = this
        setupRecycler()
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.response.observe(viewLifecycleOwner, Observer { resp->
            if(resp.success) {
                viewModel.ratesList.value= viewModel.getListFromResponse(resp,sharedviewModel.BASE.value!!)
                viewModel.lastUpdate.value= resp.timestamp
                binding.lastUpdate.text = getDateTime(viewModel.lastUpdate.value!!)

            }
        })
        sharedviewModel.BASE.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()){
                viewModel.ratesList.value= viewModel.response.value?.let { resp ->
                    viewModel.getListFromResponse(
                        resp,sharedviewModel.BASE.value!!)
                }
                binding.currValue.text = it.toString()
            }
        })
        viewModel.ratesList.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()){
                binding.rvRates.adapter =
                    CurrencyRatesAdapter(viewModel.ratesList.value!!)
            }
        })
    }
    private fun getDateTime(time: Long): String? {
        try {
            val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)
            return simpleDateFormat.format(time * 1000L)
        } catch (e: Exception) {
            return e.toString()
        }
    }
    private fun setupRecycler() {

        val recycler = binding.rvRates
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.setHasFixedSize(true)
        val adapter = viewModel.ratesList.value?.let { CurrencyRatesAdapter(it) }
        recycler.adapter = adapter

    }
}