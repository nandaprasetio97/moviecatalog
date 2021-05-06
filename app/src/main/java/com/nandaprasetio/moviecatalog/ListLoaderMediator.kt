package com.nandaprasetio.moviecatalog

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nandaprasetio.moviecatalog.presentation.activity.BaseActivity
import com.nandaprasetio.core.presentation.adapter.listandloadstateadapter.listadapter.baselistadapter.BaseListViewHolder
import com.nandaprasetio.core.presentation.adapter.listandloadstateadapter.listadapter.baselistadapter.BasePagingDataAdapter
import com.nandaprasetio.moviecatalog.presentation.footerloadstateadapter.FooterLoadStateAdapter
import com.nandaprasetio.core.presentation.errorprovider.ErrorProvider
import com.nandaprasetio.core.domain.usecase.listloader.ListLoaderUseCase
import com.nandaprasetio.core.domain.entity.adapterdata.BaseAdapterData
import com.nandaprasetio.core.domain.entity.result.FailedHttpResultThrowable
import com.nandaprasetio.core.presentation.LoadingStateChangable
import com.nandaprasetio.core.presentation.viewmodel.ListViewModel
import com.nandaprasetio.core.presentation.viewmodel.ListViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import java.lang.ref.WeakReference

@Suppress("UNCHECKED_CAST")
class ListLoaderMediator<Key: Any, Value: BaseAdapterData>(
    baseActivity: BaseActivity<*>,
    recyclerView: RecyclerView,
    private val onGetListLoaderUseCase: () -> ListLoaderUseCase<Key, Value>,
    private val onGetErrorProvider: () -> ErrorProvider,
    private val pagingListAdapter: BasePagingDataAdapter<Value, BaseListViewHolder>? = null,
    private val loadingStateChangable: LoadingStateChangable,
    private val key: String? = null
) {
    private val newKey: String = "androidx.lifecycle.ViewModelProvider.CustomKey"
    private val baseActivityWeakReference: WeakReference<BaseActivity<*>> = WeakReference(baseActivity)
    private val recyclerViewWeakReference: WeakReference<RecyclerView> = WeakReference(recyclerView)
    private lateinit var listViewModel: ListViewModel<Key, Value>

    fun attach(firstGetList: Boolean = false) {
        nullSafetyBaseActivityRecyclerView { baseActivity, recyclerView ->
            val viewModelProvider = ViewModelProvider(
                baseActivity, ListViewModelFactory(onGetListLoaderUseCase())
            )
            listViewModel = if (key != null) {
                viewModelProvider.get("${newKey}.{$key}", ListViewModel::class.java) as ListViewModel<Key, Value>
            } else {
                viewModelProvider.get(ListViewModel::class.java) as ListViewModel<Key, Value>
            }

            if (firstGetList) {
                setListFlowToRecyclerView()
            }

            listViewModel.apply {
                this.listLoadingStateLiveData.observe(baseActivity) { it2 ->
                    loadingStateChangable.onLoadingStateChange(it2)
                }
                this.listErrorLoadingStateLiveData.observe(baseActivity) { it2 ->
                    loadingStateChangable.onLoadingFailed(onGetErrorProvider().getMessage(baseActivity, it2))
                }
            }

            recyclerView.apply {
                this.layoutManager = LinearLayoutManager(baseActivity, RecyclerView.VERTICAL, false)
                this.adapter = pagingListAdapter?.withLoadStateFooter(
                    FooterLoadStateAdapter(onGetErrorProvider()) {
                        pagingListAdapter.retry()
                    }
                )
            }

            pagingListAdapter?.addLoadStateListener {
                when (val refreshState = it.source.refresh) {
                    is LoadState.Loading -> listViewModel.setLoadingState(-1)
                    is LoadState.Error -> {
                        listViewModel.setLoadingState(-2)
                        listViewModel.setErrorLoadingState(refreshState.error as FailedHttpResultThrowable)
                    }
                    else -> listViewModel.setLoadingState(0)
                }
            }
        }
    }

    fun getList() {
        if (this::listViewModel.isInitialized) {
            setListFlowToRecyclerView()
        }
    }

    fun refresh() {
        pagingListAdapter?.refresh()
    }

    private fun setListFlowToRecyclerView() {
        nullSafetyBaseActivityRecyclerView { it, _ ->
            it.lifecycleScope.launchWhenCreated {
                listViewModel.getList().collectLatest { it2 ->
                    pagingListAdapter?.submitData(it2)
                }
            }
        }
    }

    private fun nullSafetyBaseActivityRecyclerView(
        onNullSafety: (BaseActivity<*>, RecyclerView) -> Unit
    ) {
        baseActivityWeakReference.get()?.also { it ->
            recyclerViewWeakReference.get()?.also { it2 ->
                onNullSafety(it, it2)
            }
        }
    }
}