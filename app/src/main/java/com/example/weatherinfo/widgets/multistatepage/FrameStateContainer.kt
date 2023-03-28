package com.example.weatherinfo.widgets.multistatepage

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes

class FrameStateContainer : FrameLayout, MultiStatePresenter {
    private var originTargetView: View? = null
    private var lastState: MultiState? = null
    private var marginTop: Int = 0
    private var helper = StatePageHelper()

    constructor(
        context: Context,
        originTargetView: View,
        marginTop: Int
    ) : this(context, null) {
        this.originTargetView = originTargetView
        this.marginTop = marginTop
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : this(context, attrs, 0)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (originTargetView == null && childCount == 1) {
            originTargetView = getChildAt(0)
        }
    }

    fun initialization() {
        addView(originTargetView, 0, generateDefaultLayoutParams())
    }

    override fun getShowStatePage(): Class<MultiState>? {
        return lastState?.javaClass
    }

    override fun <T : MultiState> show(
        clazz: Class<T>,
        @LayoutRes layoutId: Int?,
        itemSize: Int?,
        retry: (() -> Unit)?,
        onComplete: ((MultiState) -> Unit)?
    ) {
        if (lastState?.javaClass == clazz) return
        if (childCount == 0) {
            initialization()
        }
        helper.getOrCratedState(layoutId, clazz)?.let { multiState ->
            if (childCount > 1) {
                lastState?.getMultiStateView()?.let {
                    lastState?.onFinished(it)
                }
                removeViewAt(1)
            }
            if (multiState !is MultiState.Success) {
                helper.addStateView(multiState, this, itemSize, marginTop, null, retry, onComplete)
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