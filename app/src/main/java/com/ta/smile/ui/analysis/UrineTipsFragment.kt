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

class UrineTipsFragment: Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private var _binding: FragmentUrineTipsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_urine_tips, container, false)
        _binding = FragmentUrineTipsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = obtainMainViewModel(requireActivity() as AppCompatActivity)
        mainViewModel.getLatestUrineColor().observe(viewLifecycleOwner){
            if (it.createdAt == "-"){
                binding.tvNoData.visibility = View.VISIBLE
                binding.tvTipsUrine.visibility = View.GONE
            } else {
                binding.tvNoData.visibility = View.GONE
                binding.tvTipsUrine.visibility = View.VISIBLE
                binding.tvTipsUrine.text = when(it.type){
                    0-> resources.getString(R.string.tips_urine_color_bronwn)
                    1-> resources.getString(R.string.tips_urine_color_yellow)
                    2-> resources.getString(R.string.tips_urine_color_red)
                    else -> resources.getString(R.string.tips_urine_color_transparent)
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