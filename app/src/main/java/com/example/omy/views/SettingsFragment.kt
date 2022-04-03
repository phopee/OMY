package com.example.omy.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.omy.R
import com.example.omy.databinding.SettingsFragmentBinding
import com.example.omy.models.SharedViewModel

private lateinit var binding: SettingsFragmentBinding
class SettingsFragment :Fragment() {
    private val viewModel :SharedViewModel by activityViewModels()
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
                    viewModel.REFRESH_RATE.value = 5000.0
                }
                R.id.btn_60 ->{
                    viewModel.REFRESH_RATE.value = 60000.0
                }
                R.id.btn_1h ->{
                    viewModel.REFRESH_RATE.value=  60000.0 * 60.0

                }

            }
            viewModel.changeRefreshRate()

            }
        binding.radioGroup.check(R.id.btn_1h)

    }

}