package com.nandaprasetio.core.presentation.adapter.listandloadstateadapter.listadapter.baselistadapter

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.nandaprasetio.core.domain.entity.adapterdata.BaseAdapterData

abstract class BasePagingDataAdapter<T: BaseAdapterData, VH: BaseListViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>
): PagingDataAdapter<T, VH>(diffCallback)