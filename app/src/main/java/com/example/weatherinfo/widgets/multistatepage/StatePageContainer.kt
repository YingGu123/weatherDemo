package com.example.weatherinfo.widgets.multistatepage

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout

class StatePageContainer(
    constraintContainer: ConstraintLayout? = null,
    @IdRes private val topViewId: Int? = null,
    frameContainer: FrameLayout? = null,
    private val marginTop: Int? = null
) : MultiStatePresenter {
    private var lastState: MultiState? = null
    private var helper = StatePageHelper()
    private lateinit var container: ViewGroup

    init {
        if (constraintContainer != null) {
            container = constraintContainer
        } else if (frameContainer != null) {
            container = frameContainer
        }
    }

    override fun getShowStatePage(): Class<MultiState>? {
        return lastState?.javaClass
    }

    override fun <T : MultiState> show(
        clazz: Class<T>,
        layoutId: Int?,
        itemSize: Int?,
        retry: (() -> Unit)?,
        onComplete: ((MultiState) -> Unit)?
    ) {
        if (lastState?.javaClass == clazz && clazz != MultiState.Error::class.java) return
        helper.getOrCratedState(layoutId, clazz)?.let { multiState ->
            if (lastState !is MultiState.Success) {
                lastState?.getMultiStateView()?.let {
                    lastState?.onFinished(it)
                    container.removeView(it)
                }
            }
            if (multiState !is MultiState.Success) {
                helper.addStateView(multiState, container, itemSize, marginTop, topViewId, retry, onComplete)
            }
            lastState = multiState
        }
    }

    inline fun <reified T : MultiState> show(
        layoutId: Int? = null,
        itemSize: Int? = null,
        noinline retry: (() -> Unit)? = null,
        noinline onComplete: ((MultiState) -> Unit)? = null
    ) {
        show(T::class.java, layoutId, itemSize, retry, onComplete)
    }
}