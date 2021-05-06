package com.nandaprasetio.moviecatalog.presentation.footerloadstateadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import com.nandaprasetio.core.presentation.adapter.listandloadstateadapter.loadstateadapter.baseloadstateadapter.BaseLoadStateAdapter
import com.nandaprasetio.moviecatalog.databinding.LayoutViewFooterListBinding
import com.nandaprasetio.core.presentation.errorprovider.ErrorProvider

class FooterLoadStateAdapter(private val errorProvider: ErrorProvider, private val onRetryButtonPressed: (() -> Unit)?): BaseLoadStateAdapter<FooterLoadStateViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterLoadStateViewHolder {
        return FooterLoadStateViewHolder(
            LayoutViewFooterListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onRetryButtonPressed
        )
    }

    override fun onBindViewHolder(holder: FooterLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, errorProvider)
    }
}