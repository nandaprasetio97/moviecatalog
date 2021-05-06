package com.nandaprasetio.moviecatalog.presentation.fragment.recyclerviewfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nandaprasetio.core.presentation.LoadingStateChangable
import com.nandaprasetio.moviecatalog.R
import com.nandaprasetio.core.presentation.RefreshWhileResuming
import com.nandaprasetio.moviecatalog.presentation.activity.BaseActivity
import com.nandaprasetio.core.presentation.adapter.listandloadstateadapter.listadapter.baselistadapter.BaseListViewHolder
import com.nandaprasetio.core.presentation.adapter.listandloadstateadapter.listadapter.baselistadapter.BasePagingDataAdapter
import com.nandaprasetio.moviecatalog.databinding.FragmentRecyclerViewBinding
import com.nandaprasetio.core.presentation.errorprovider.BaseErrorProvider
import com.nandaprasetio.core.presentation.errorprovider.ErrorProvider
import com.nandaprasetio.core.presentation.errorprovider.ErrorProviderResult
import com.nandaprasetio.moviecatalog.presentation.fragment.BaseFragment
import com.nandaprasetio.moviecatalog.ListLoaderMediator
import com.nandaprasetio.core.domain.usecase.listloader.ListLoaderUseCase
import com.nandaprasetio.core.domain.entity.adapterdata.BaseAdapterData

abstract class BaseRecyclerViewFragment<Value: BaseAdapterData, T: ListLoaderUseCase<Int, Value>>(
    private val key: String? = null
): BaseFragment<FragmentRecyclerViewBinding>(), LoadingStateChangable {
    private lateinit var contentRecyclerView: RecyclerView
    protected lateinit var listLoaderMediator: ListLoaderMediator<Int, Value>
    private lateinit var layoutViewLoading: View
    private lateinit var layoutViewNotification: View
    private lateinit var nameNotificationTextView: TextView
    private lateinit var warningNotificationImageView: ImageView

    override fun onInitializeViewBinding(): FragmentRecyclerViewBinding {
        return FragmentRecyclerViewBinding.inflate(this.layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewResult: View? = super.onCreateView(inflater, container, savedInstanceState)
        contentRecyclerView = viewResult!!.findViewById(R.id.recycler_view_content)
        layoutViewLoading = viewResult.findViewById(R.id.layout_view_loading)
        layoutViewNotification = viewResult.findViewById(R.id.layout_view_notification)
        nameNotificationTextView = layoutViewNotification.findViewById(R.id.text_view_name)
        warningNotificationImageView = layoutViewNotification.findViewById(R.id.image_view_warning)

        listLoaderMediator = ListLoaderMediator(
            baseActivity = this.requireActivity() as BaseActivity<*>,
            recyclerView = contentRecyclerView,
            onGetListLoaderUseCase = { getListLoaderUseCase() },
            onGetErrorProvider = { getErrorProvider() },
            pagingListAdapter = getPagingListAdapter(),
            key = key,
            loadingStateChangable = this
        )
        listLoaderMediator.attach()

        return viewResult
    }

    override fun onLoadingStateChange(state: Int) {
        contentRecyclerView.visibility = if (state == 0) View.VISIBLE else View.INVISIBLE
        layoutViewLoading.visibility = if (state == -1) View.VISIBLE else View.INVISIBLE
        layoutViewNotification.visibility = if (state == -2) View.VISIBLE else View.INVISIBLE
    }

    override fun onLoadingFailed(errorProviderResult: ErrorProviderResult?) {
        nameNotificationTextView.text = errorProviderResult?.message
    }

    override fun onResume() {
        super.onResume()
        if (this.requireActivity() is RefreshWhileResuming) {
            listLoaderMediator.refresh()
        }
    }

    abstract fun getListLoaderUseCase(): ListLoaderUseCase<Int, Value>

    abstract fun getPagingListAdapter(): BasePagingDataAdapter<Value, BaseListViewHolder>

    open fun getErrorProvider(): ErrorProvider {
        return BaseErrorProvider()
    }
}