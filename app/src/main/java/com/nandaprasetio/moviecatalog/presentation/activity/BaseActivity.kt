package com.nandaprasetio.moviecatalog.presentation.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.nandaprasetio.moviecatalog.R

abstract class BaseActivity<T: ViewBinding>(
    private val skipInitializeView: Boolean = false,
    private val enableBack: Boolean = true
): AppCompatActivity() {
    private var baseActivityViewBinding: T? = null

    abstract fun onInitializeViewBinding(): T

    protected fun getViewBinding(): T? {
        return baseActivityViewBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        this.setTheme(R.style.Theme_MovieCatalog)

        super.onCreate(savedInstanceState)
        if (skipInitializeView) {
            return
        }

        baseActivityViewBinding = onInitializeViewBinding()
        this.setContentView(baseActivityViewBinding?.root)

        this.supportActionBar?.apply {
            this.setDisplayShowHomeEnabled(enableBack)
            this.setDisplayHomeAsUpEnabled(enableBack)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.onBackPressed()
            return true
        }

        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        baseActivityViewBinding = null
    }
}