package com.ta.smile.ui.analysis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ta.smile.R
import com.ta.smile.databinding.FragmentFecesTipsBinding
import com.ta.smile.databinding.FragmentUrineTipsBinding
import com.ta.smile.ui.MainViewModel
import com.ta.smile.ui.ViewModelFactory

class FecesTipsFragment: Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private var _binding: FragmentFecesTipsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFecesTipsBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = obtainMainViewModel(requireActivity() as AppCompatActivity)
        mainViewModel.getLatestFecesForm().observe(viewLifecycleOwner){
            if(it.createdAt == "-"){
                binding.tvNoData.visibility = View.VISIBLE
                binding.tvTipsFecesForm.visibility = View.GONE
            } else {
                binding.tvNoData.visibility = View.GONE
                binding.tvTipsFecesForm.visibility = View.VISIBLE
                binding.tvTipsFecesForm.text = when(it.type){
                    0 -> resources.getString(R.string.tips_feces_type_1)
                    1 -> resources.getString(R.string.tips_feces_type_2)
                    2 -> resources.getString(R.string.tips_feces_type_3)
                    3 -> resources.getString(R.string.tips_feces_type_4)
                    4 -> resources.getString(R.string.tips_feces_type_5)
                    5 -> resources.getString(R.string.tips_feces_type_6)
                    else -> resources.getString(R.string.tips_feces_type_7)
                }
            }
        }
        mainViewModel.getLatestFecesColor().observe(viewLifecycleOwner){
            if (it.createdAt == "-"){
                binding.tvNoData.visibility = View.VISIBLE
                binding.tvTipsFecesColor.visibility = View.GONE
            } else {
                binding.tvNoData.visibility = View.GONE
                binding.tvTipsFecesColor.visibility = View.VISIBLE
                binding.tvTipsFecesColor.text = when(it.type){
                    0 -> resources.getString(R.string.tips_feces_kehitaman)
                    1 -> resources.getString(R.string.tips_fees_kemerahan)
                    2 -> resources.getString(R.string.tips_feces_normal)
                    else -> resources.getString(R.string.tips_feces_pucat)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun obtainMainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }
}