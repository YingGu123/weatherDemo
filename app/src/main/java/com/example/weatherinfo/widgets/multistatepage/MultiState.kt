package com.example.weatherinfo.widgets.multistatepage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.weatherinfo.R
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout

sealed class MultiState {

    abstract fun getOrCreateMultiStateView(
        context: Context,
        container: ViewGroup
    ): View

    abstract fun onMultiStateViewCreated(view: View)

    abstract fun onFinished(view: View)

    protected var stateView: View? = null

    protected fun onCreateMultiStateView(
        context: Context,
        @LayoutRes layoutId: Int,
        container: ViewGroup
    ): View {
        val view = LayoutInflater.from(context).inflate(layoutId, container, false)
        stateView = view
        return view
    }

    fun getMultiStateView(): View? {
        return stateView
    }

    class Empty(@LayoutRes val layoutId: Int = R.layout.multi_state_empty) : MultiState() {
        override fun getOrCreateMultiStateView(
            context: Context,
            container: ViewGroup
        ): View {
            return onCreateMultiStateView(context, layoutId, container)
        }

        override fun onMultiStateViewCreated(view: View) {

        }

        override fun onFinished(view: View) {

        }

    }

    class Error(@LayoutRes val layoutId: Int = R.layout.multi_state_error) : MultiState() {
        private var retry: (() -> Unit)? = null

        override fun getOrCreateMultiStateView(
            context: Context,
            container: ViewGroup
        ): View {
            return onCreateMultiStateView(context, layoutId, container)
        }

        override fun onMultiStateViewCreated(view: View) {
            val context = view.context
            val title: String = context.getString(R.string.error_server_title)
            val msg: String = context.getString(R.string.error_server_msg)
            view.findViewById<AppCompatTextView>(R.id.tvPageError).text = title
            view.findViewById<AppCompatTextView>(R.id.tvPageErrorMsg).text = msg
            view.findViewById<AppCompatTextView>(R.id.tvPageErrorRetry).setOnClickListener {
                retry?.invoke()
            }
        }

        override fun onFinished(view: View) {

        }

        fun setRetry(retry: (() -> Unit)?) {
            this.retry = retry
        }

    }

    class Success : MultiState() {
        override fun getOrCreateMultiStateView(
            context: Context,
            container: ViewGroup
        ): View {
            return View(context)
        }

        override fun onMultiStateViewCreated(view: View) {

        }

        override fun onFinished(view: View) {

        }
    }

    class Skeleton(@LayoutRes private val layoutId: Int) : MultiState() {
        var itemSize: Int = 1

        override fun getOrCreateMultiStateView(
            context: Context,
            container: ViewGroup
        ): View {
            val rootLayout = FrameLayout(context)
            rootLayout.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
            val shimmerFrameLayout = ShimmerFrameLayout(context)
            val shimmer = Shimmer.AlphaHighlightBuilder()
                .setBaseAlpha(1f)
                .setHighlightAlpha(0f)
                .setWidthRatio(0.7f)
                .setDuration(1300)
                .build()
            shimmerFrameLayout.setShimmer(shimmer)
            if (itemSize == 1) {
                LayoutInflater.from(context).inflate(layoutId, shimmerFrameLayout)
            } else {
                val linearLayout = LinearLayout(context)
                linearLayout.orientation = LinearLayout.VERTICAL
                for (i in 0 until itemSize) {
                    LayoutInflater.from(context).inflate(layoutId, linearLayout)
                }
                shimmerFrameLayout.addView(linearLayout)
            }
            rootLayout.addView(shimmerFrameLayout)
            stateView = rootLayout
            return rootLayout
        }

        override fun onMultiStateViewCreated(view: View) {
            (view as? ShimmerFrameLayout)?.startShimmer()
        }

        override fun onFinished(view: View) {
            (view as? ShimmerFrameLayout)?.stopShimmer()
        }

    }
}