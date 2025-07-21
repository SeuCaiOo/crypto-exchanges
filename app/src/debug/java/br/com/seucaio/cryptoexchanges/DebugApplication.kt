package br.com.seucaio.cryptoexchanges

import leakcanary.LeakCanary

class DebugApplication : MyApplication() {
    override fun onCreate() {
        super.onCreate()
        LeakCanary.config = LeakCanary.config.copy(
            retainedVisibleThreshold = 3,
            dumpHeap = true,
            requestWriteExternalStoragePermission = false,
            useExperimentalLeakFinders = true
        )
    }
}