package com.example.weatherinfo.widgets.multistatepage

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout

object MultiStatePage {

    fun bindMultiState(activity: Activity, marginTop: Int = 0): FrameStateContainer {
        val targetView = activity.findViewById<ViewGroup>(android.R.id.content)
        val targetViewIndex = 0
        val oldContent: View = targetView.getChildAt(targetViewIndex)
        targetView.removeView(oldContent)
        val oldLayoutParams = oldContent.layoutParams
        val multiStateContainer = FrameStateContainer(oldContent.context, oldContent, marginTop)
        targetView.addView(multiStateContainer, targetViewIndex, oldLayoutParams)
        multiStateContainer.initialization()
        return multiStateContainer
    }

    fun bindMultiState(targetView: View, marginTop: Int = 0): FrameStateContainer {
        val parent = targetView.parent as? ViewGroup
        var targetViewIndex = 0
        val multiStateContainer = FrameStateContainer(targetView.context, targetView, marginTop)
        parent?.let { targetViewParent ->
            for (i in 0 until targetViewParent.childCount) {
                if (targetViewParent.getChildAt(i) == targetView) {
                    targetViewIndex = i
                    break
                }
            }
            targetViewParent.removeView(targetView)
            targetViewParent.addView(multiStateContainer, targetViewIndex, targetView.layoutParams)
        }
        multiStateContainer.initialization()
        return multiStateContainer
    }

    fun bindFrameState(container: FrameLayout, @IdRes topViewId: Int? = null): StatePageContainer {
        return StatePageContainer(frameContainer = container, topViewId = topViewId)
    }

    fun bindConstrainState(container: ConstraintLayout, @IdRes topViewId: Int? = null): StatePageContainer {
        return StatePageContainer(constraintContainer = container, topViewId = topViewId)
    }
}