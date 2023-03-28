package com.example.weatherinfo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.weatherinfo.adapter.WeatherCastInfoAdapter
import com.example.weatherinfo.base.BaseActivity
import com.example.weatherinfo.databinding.ActivityWeatherDetailBinding
import com.example.weatherinfo.model.UiState
import com.example.weatherinfo.utils.SnackBarUtil
import com.example.weatherinfo.vm.WeatherDetailViewModel

class WeatherDetailActivity : BaseActivity() {

    private val viewModel: WeatherDetailViewModel by viewModels()

    private val mAdapter: WeatherCastInfoAdapter by lazy {
        WeatherCastInfoAdapter()
    }

    private var adCode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityWeatherDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_weather_detail)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        initStatePageContainer(binding.rootLayout)

        binding.recyclerView.apply {
            setDividerItemDecoration()
            this.adapter = mAdapter
        }

        adCode = intent.getStringExtra(Const.BUNDLE_AD_CODE)

        viewModel.getWeatherAllInfo(adCode)

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

        viewModel.weatherForecastInfo.observe(this) {
            mAdapter.submitList(it.casts)
        }
    }

    override fun onRetry() {
        viewModel.getWeatherAllInfo(adCode)
    }
}