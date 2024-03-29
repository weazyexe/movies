package exe.weazy.movies.di

import android.app.Application

class App : Application() {

    companion object {
        private lateinit var component : AppComponent
        fun getComponent() = component
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.create()
    }
}