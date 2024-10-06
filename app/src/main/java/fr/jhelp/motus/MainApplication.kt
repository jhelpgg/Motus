package fr.jhelp.motus

import android.app.Application
import android.content.Context
import fr.jhelp.injector.inject
import fr.jhelp.viewmodel.viewModels

class MainApplication : Application()
{
    override fun onCreate()
    {
        super.onCreate()
        // Inject the application context to be use everywhere a context is required
        // It is safe to share tha application context since it is a real singleton for the all application
        val applicationContext = this.applicationContext
        inject<Context>(applicationContext)
        // Retrieve the injections for view models
        viewModels()
    }
}