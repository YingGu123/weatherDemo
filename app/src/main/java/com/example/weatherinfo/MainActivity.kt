package com.example.weatherinfo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.weatherinfo.adapter.WeatherBaseInfoAdapter
import com.example.weatherinfo.base.BaseActivity
import com.example.weatherinfo.databinding.ActivityMainBinding
import com.example.weatherinfo.model.UiState
import com.example.weatherinfo.utils.SnackBarUtil
import com.example.weatherinfo.vm.MainViewModel

class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mAdapter: WeatherBaseInfoAdapter by lazy {
        WeatherBaseInfoAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initStatePageContainer(binding.root)

        binding.recyclerView.apply {
            setDividerItemDecoration()
            adapter = mAdapter
        }

        viewModel.getWeatherBaseInfo()

        lifecycleScope.launchWhenStarted {
            viewModel.uiStateFlow.collect {
                when (it) {
                    is UiState.Loading -> {
                        showSkeleton()
                    }
                    is UiState.Success -> {
                        showSuccess()
                    }
                    is UiState.ErrorToast -> {
                        SnackBarUtil.show(binding.root, it.message)
                    }
                    is UiState.ErrorPage -> {
                        showErrorPage()
                    }
                    else -> Unit
                }
            }
        }

        viewModel.weatherLiveList.observe(this) {
            mAdapter.submitList(it)
        }
    }

    override fun onRetry() {
        viewModel.getWeatherBaseInfo()
    }
}