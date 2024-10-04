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
        val applicationContext = this.applicationContext
        inject<Context>(applicationContext)
        viewModels()
    }
}