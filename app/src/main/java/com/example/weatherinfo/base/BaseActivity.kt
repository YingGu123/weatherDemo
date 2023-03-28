package com.example.weatherinfo.base

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.weatherinfo.Const
import com.example.weatherinfo.R
import com.example.weatherinfo.widgets.multistatepage.MultiStatePage
import com.example.weatherinfo.widgets.multistatepage.StatePageContainer

open class BaseActivity : AppCompatActivity() {

    private var statePage: StatePageContainer? = null

    open fun initStatePageContainer(container: ConstraintLayout, @IdRes topViewId: Int? = null) {
        statePage = MultiStatePage.bindConstrainState(container, topViewId)
    }

    open fun showEmptyPage(content: String) {
        statePage?.run {
            this.showEmpty()
        }
    }

    open fun showSkeleton(
        layoutId: Int = R.layout.multi_state_skeleton,
        itemSize: Int = Const.SKELETON_DEFAULT_SIZE
    ) {
        statePage?.run {
            this.showSkeleton(layoutId, itemSize)
        }
    }

    open fun showErrorPage() {
        statePage?.run {
            this.showError { onRetry() }
        }
    }

    open fun showSuccess() {
        statePage?.run {
            this.showSuccess()
        }
    }

    open fun onRetry() {

    }
}