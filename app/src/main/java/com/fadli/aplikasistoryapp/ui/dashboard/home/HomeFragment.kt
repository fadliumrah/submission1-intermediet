package com.fadli.aplikasistoryapp.ui.dashboard.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fadli.aplikasistoryapp.utils.settingPreferences.SettingViewModel
import com.fadli.aplikasistoryapp.ui.dashboard.story.StoryViewModel
import com.fadli.aplikasistoryapp.utils.models.ViewModelGeneralFactory
import com.fadli.aplikasistoryapp.utils.models.ViewModelSettingFactory
import com.fadli.aplikasistoryapp.databinding.FragmentHomeBinding
import com.fadli.aplikasistoryapp.ui.dashboard.MainActivity
import com.fadli.aplikasistoryapp.utils.Constanta
import com.fadli.aplikasistoryapp.utils.Helper
import com.fadli.aplikasistoryapp.utils.settingPreferences.SettingPreferences
import com.fadli.aplikasistoryapp.utils.settingPreferences.dataStore
import java.util.*
import kotlin.concurrent.schedule

class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var viewModel: StoryViewModel? = null
    private lateinit var binding: FragmentHomeBinding
    private val rvAdapter = HomeAdapter()
    private var tempToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this,
            ViewModelGeneralFactory((activity as MainActivity))
        )[StoryViewModel::class.java]
        val pref = SettingPreferences.getInstance((activity as MainActivity).dataStore)
        val settingViewModel =
            ViewModelProvider(this, ViewModelSettingFactory(pref))[SettingViewModel::class.java]
        settingViewModel.getUserPreferences(Constanta.UserPreferences.UserToken.name)
            .observe(viewLifecycleOwner) { token ->
                tempToken = StringBuilder("Bearer ").append(token).toString()
                viewModel?.loadStoryData(tempToken)
            }


        binding.btnJumpUp.visibility = View.GONE
        binding.swipeRefresh.setOnRefreshListener {
            onRefresh()
        }
        binding.rvStory.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
            adapter = rvAdapter
        }
        viewModel?.apply {
            loading.observe(viewLifecycleOwner) { binding.loading.root.visibility = it }
            error.observe(
                viewLifecycleOwner
            ) { if (it.isNotEmpty()) Helper.showDialogInfo(requireContext(), it) }
            storyList.observe(viewLifecycleOwner) {
                rvAdapter.apply {
                    initData(it)
                    notifyDataSetChanged()
                }
                binding.btnJumpUp.visibility = View.VISIBLE
            }
        }
        binding.btnJumpUp.setOnClickListener {
            binding.nestedScrollView.smoothScrollTo(0, 0)
        }
        return binding.root
    }

    override fun onRefresh() {
        binding.swipeRefresh.isRefreshing = true
        viewModel?.loadStoryData(tempToken)
        Timer().schedule(2000) {
            binding.swipeRefresh.isRefreshing = false
        }
        binding.nestedScrollView.smoothScrollTo(0, 0)
    }

}