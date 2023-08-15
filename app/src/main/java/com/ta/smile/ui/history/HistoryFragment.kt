package com.ta.smile.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.project.smarttoilet.adapter.FecesSectionPagerAdapter
import com.ta.smile.R
import com.ta.smile.databinding.FragmentHistoryBinding
import com.ta.smile.ui.analysis.AnalysisFragment

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(HistoryViewModel::class.java)

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView(){
        //TabLayout Mediator for feces
        val fecesSectionPagerAdapter = HistorySectionPagerAdapter(requireActivity() as AppCompatActivity)
        val fecesViewPager: ViewPager2? = view?.findViewById(R.id.view_pager_history)
        fecesViewPager?.adapter = fecesSectionPagerAdapter
        val tabsFeces: TabLayout? = view?.findViewById(R.id.tabl_history)
        if (tabsFeces != null) {
            if (fecesViewPager != null) {
                TabLayoutMediator(tabsFeces, fecesViewPager) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            }
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_feces_history,
            R.string.tab_urine_history
        )
    }
}