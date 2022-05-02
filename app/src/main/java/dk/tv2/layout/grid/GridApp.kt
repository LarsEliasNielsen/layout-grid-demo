package dk.tv2.layout.grid

import android.app.Application
import timber.log.Timber

class GridApp: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}
