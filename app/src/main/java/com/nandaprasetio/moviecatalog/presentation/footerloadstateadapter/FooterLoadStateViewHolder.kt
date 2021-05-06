package com.nandaprasetio.moviecatalog.presentation.footerloadstateadapter

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.paging.LoadState
import com.nandaprasetio.moviecatalog.R
import com.nandaprasetio.core.presentation.adapter.listandloadstateadapter.loadstateadapter.baseloadstateadapter.BaseLoadStateAdapterViewHolder
import com.nandaprasetio.moviecatalog.databinding.LayoutViewFooterListBinding
import com.nandaprasetio.core.presentation.errorprovider.ErrorProvider
import com.nandaprasetio.core.domain.entity.result.FailedHttpResultThrowable

class FooterLoadStateViewHolder(
    layoutViewFooterListBinding: LayoutViewFooterListBinding,
    private val onRetryButtonPressed: (() -> Unit)?
): BaseLoadStateAdapterViewHolder(layoutViewFooterListBinding.root) {
    private val linearLayoutError: LinearLayoutCompat = layoutViewFooterListBinding.linearLayoutError
    private val progressBarLoading: ProgressBar = layoutViewFooterListBinding.progressBarLoading
    private val textViewError: TextView = layoutViewFooterListBinding.textViewError
    private val buttonRetry: Button = layoutViewFooterListBinding.buttonRetry

    fun bind(loadState: LoadState, errorProvider: ErrorProvider) {
        progressBarLoading.visibility = if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
        linearLayoutError.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
        buttonRetry.setOnClickListener { onRetryButtonPressed?.invoke() }
        itemView.context.also {
            textViewError.text = if (loadState is LoadState.Error) {
                val error: Throwable = loadState.error
                if (error is FailedHttpResultThrowable) {
                    errorProvider.getMessage(it, error)?.message
                } else {
                    it.getString(R.string.unknown_error)
                }
            } else {
                null
            }
        }
    }
}