package com.nandaprasetio.moviecatalog.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T: ViewBinding>: Fragment() {
    private var baseFragmentViewBinding: T? = null

    abstract fun onInitializeViewBinding(): T

    protected fun getViewBinding(): T? {
        return baseFragmentViewBinding
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        baseFragmentViewBinding = onInitializeViewBinding()
        return baseFragmentViewBinding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        baseFragmentViewBinding = null
    }
}