package com.nandaprasetio.moviecatalog.extension

import android.content.Context
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest

fun installModule(
    moduleName: String,
    context: Context,
    onIfModuleInstalled: () -> Unit,
    onSuccessInstallModule: (Int) -> Unit,
    onFailedInstallModule: (Exception) -> Unit
) {
    val splitInstallManager: SplitInstallManager = SplitInstallManagerFactory.create(context)
    if (splitInstallManager.installedModules.contains(moduleName)) {
        onIfModuleInstalled()
    } else {
        val request: SplitInstallRequest = SplitInstallRequest.newBuilder()
            .addModule(moduleName)
            .build()
        splitInstallManager.startInstall(request)
            .addOnSuccessListener {
                onSuccessInstallModule(it)
            }
            .addOnFailureListener {
                onFailedInstallModule(it)
            }
    }
}