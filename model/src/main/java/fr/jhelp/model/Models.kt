package fr.jhelp.model

import fr.jhelp.data.data
import fr.jhelp.injector.inject
import fr.jhelp.model.implementation.MotusServiceImplementation
import fr.jhelp.model.shared.MotusService

/**
 * Retrieve the injections for models
 */
fun models()
{
    // Retrieve the injections for data
    data()

    // Inject the models
    inject<MotusService>(MotusServiceImplementation())
}