package com.nandaprasetio.core.presentation.adapter.listandloadstateadapter.listadapter.baselistadapter

import androidx.recyclerview.widget.DiffUtil
import com.nandaprasetio.core.domain.entity.adapterdata.BaseAdapterData

abstract class BaseDiffUtilItemCallback<T: BaseAdapterData>: DiffUtil.ItemCallback<T>()