package fr.jhelp.model

import fr.jhelp.data.data
import fr.jhelp.injector.inject
import fr.jhelp.model.implementation.MotusServiceImplementation
import fr.jhelp.model.shared.MotusService

fun models()
{
    data()
    inject<MotusService>(MotusServiceImplementation())
}