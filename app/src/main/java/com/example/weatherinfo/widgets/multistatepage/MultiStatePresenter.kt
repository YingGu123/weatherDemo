package com.example.weatherinfo.widgets.multistatepage

import androidx.annotation.LayoutRes

interface MultiStatePresenter {
    fun showSuccess(@LayoutRes layoutId: Int? = null, onComplete: ((MultiState) -> Unit)? = null) {
        show(MultiState.Success::class.java, layoutId, onComplete = onComplete)
    }

    fun showEmpty(@LayoutRes layoutId: Int? = null, onComplete: ((MultiState) -> Unit)? = null) {
        show(MultiState.Empty::class.java, layoutId, onComplete = onComplete)
    }

    fun showSkeleton(@LayoutRes layoutId: Int, itemSize: Int = 1) {
        show(MultiState.Skeleton::class.java, layoutId, itemSize)
    }

    fun showError(@LayoutRes layoutId: Int? = null, onComplete: ((MultiState) -> Unit)? = null, retry: () -> Unit) {
        show(MultiState.Error::class.java, layoutId, retry = retry, onComplete = onComplete)
    }

    fun getShowStatePage(): Class<MultiState>?

    fun <T : MultiState> show(
        clazz: Class<T>,
        @LayoutRes layoutId: Int? = null,
        itemSize: Int? = null,
        retry: (() -> Unit)? = null,
        onComplete: ((MultiState) -> Unit)? = null
    )
}