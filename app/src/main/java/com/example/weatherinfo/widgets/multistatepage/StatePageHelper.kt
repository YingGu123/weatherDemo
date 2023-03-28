package com.example.weatherinfo.widgets.multistatepage

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.collection.ArrayMap
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams

class StatePageHelper {
    private val statePool: ArrayMap<Class<out MultiState>, MultiState> = ArrayMap()

    fun <T : MultiState> getOrCratedState(
        @LayoutRes layoutId: Int? = null, clazz: Class<T>
    ): MultiState? {
        return if (statePool.containsKey(clazz)) {
            statePool[clazz]
        } else {
            if (layoutId != null && clazz != MultiState.Success::class.java) {
                val constructor = clazz.getConstructor(Int::class.java)
                statePool[clazz] = constructor.newInstance(layoutId)
            } else {
                statePool[clazz] = clazz.newInstance()
            }
            statePool[clazz]
        }
    }

    fun addStateView(
        multiState: MultiState,
        container: ViewGroup,
        itemSize: Int?,
        marginTop: Int?,
        @IdRes topViewId: Int?,
        retry: (() -> Unit)?,
        onComplete: ((MultiState) -> Unit)?
    ) {
        if (multiState is MultiState.Skeleton && itemSize != null) {
            multiState.itemSize = itemSize
        }
        val currentStateView =
            multiState.getOrCreateMultiStateView(container.context, container)
        multiState.onMultiStateViewCreated(currentStateView)
        if (container is ConstraintLayout) {
            currentStateView.id = View.generateViewId()
            updateConstraint(container, currentStateView, topViewId)
        } else if (container is FrameLayout) {
            container.addView(currentStateView, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT))
            marginTop?.let { updateMarginTop(it, currentStateView) }
        }
        if (multiState is MultiState.Error && retry != null) {
            multiState.setRetry(retry)
        }
        onComplete?.invoke(multiState)
    }

    private fun updateConstraint(
        container: ConstraintLayout,
        currentStateView: View,
        @IdRes topViewId: Int?
    ) {
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
        )
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        if (topViewId == null) {
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        } else {
            layoutParams.topToBottom = topViewId
        }
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        currentStateView.layoutParams = layoutParams
        container.addView(currentStateView)
    }

    private fun updateMarginTop(marginTop: Int, currentStateView: View) {
        if (marginTop != 0) {
            currentStateView.updateLayoutParams<FrameLayout.LayoutParams> {
                topMargin = marginTop
            }
        }
    }
}