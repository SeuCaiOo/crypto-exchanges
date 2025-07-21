package br.com.seucaio.cryptoexchanges

import android.app.Application
import android.util.Log
import br.com.seucaio.cryptoexchanges.di.appModules
import leakcanary.LeakCanary
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        configureKoin()

        configureTimber()

        configureLeakCanary()
    }

    private fun configureKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(appModules)
        }
    }

    private fun configureTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    private fun configureLeakCanary() {
        if (BuildConfig.DEBUG) {
            LeakCanary.config = LeakCanary.config.copy(
                retainedVisibleThreshold = 3,
                dumpHeap = true,
                requestWriteExternalStoragePermission = false,
                useExperimentalLeakFinders = true
            )
        }
    }
}

private class ReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) return
    }
}