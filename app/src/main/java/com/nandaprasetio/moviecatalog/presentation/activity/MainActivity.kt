package com.nandaprasetio.moviecatalog.presentation.activity

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.play.core.splitinstall.SplitInstallException
import com.nandaprasetio.moviecatalog.R
import com.nandaprasetio.moviecatalog.extension.installModule

class MainActivity: VideoListActivity(
    favourite = false,
    enableBack = false
) {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuInflater.inflate(R.menu.menu_main_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var selected = super.onOptionsItemSelected(item)
        if (!selected) {
            if (item.itemId == R.id.item_menu_favourite) {
                installModule(
                    moduleName = "favourite",
                    context = this,
                    onIfModuleInstalled = { startFavouriteActivity() },
                    onSuccessInstallModule = {
                        startFavouriteActivity()
                        Toast.makeText(this, this.getText(R.string.success_load_module), Toast.LENGTH_LONG).show()
                    },
                    onFailedInstallModule = { e ->
                        if (e is SplitInstallException) {
                            Toast.makeText(this, "${this.getText(R.string.module_not_available)} (${e.errorCode})", Toast.LENGTH_LONG).show()
                        }
                    }
                )
                selected = true
            }
        }
        return selected
    }

    private fun startFavouriteActivity() {
        this.startActivity(Intent(this, Class.forName("com.nandaprasetio.moviecatalog.favourite.presentation.VideoFavouriteActivity")))
    }
}