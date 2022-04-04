package com.example.omy.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.omy.R
import com.example.omy.databinding.SettingsFragmentBinding
import com.example.omy.models.SettingsViewModel
import com.example.omy.models.SharedViewModel

private lateinit var binding: SettingsFragmentBinding
class SettingsFragment :Fragment() {
    private val sharedViewModel :SharedViewModel by activityViewModels()
    private val viewModel :SettingsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false)
        val root = binding.root
        binding.lifecycleOwner = this
        binding.viewModel= viewModel
        binding.view = this
        setupRadioButtons()
        return root
    }
    fun setupRadioButtons(){
        binding.radioGroup.setOnCheckedChangeListener { group, _ ->
            val radio: RadioButton = group.findViewById(binding.radioGroup.checkedRadioButtonId)
            when (radio.id) {
                R.id.btn_5 ->{
                    sharedViewModel.REFRESH_RATE.value = 5000.0
                }
                R.id.btn_60 ->{
                    sharedViewModel.REFRESH_RATE.value = 60000.0
                }
                R.id.btn_1h ->{
                    sharedViewModel.REFRESH_RATE.value=  60000.0 * 60.0

                }

            }
            sharedViewModel.changeRefreshRate()

            }
        binding.radioGroup.check(R.id.btn_1h)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.symbols.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()){
                setupSpinner()
            }
        })
    }
    fun setupSpinner(){
        val adapter = ArrayAdapter<String>(requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,viewModel.symbols.value!!)
        binding.spCurr.adapter=adapter
        val default = adapter.getPosition(sharedViewModel.BASE.value)
        binding.spCurr.setSelection(default)
        binding.spCurr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(!sharedViewModel.BASE.value?.equals(adapter.getItem(position))!!){
                    sharedViewModel.BASE.value=adapter.getItem(position)
                }
            }

        }
    }

}