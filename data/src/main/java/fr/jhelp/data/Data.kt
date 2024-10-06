package fr.jhelp.data

import fr.jhelp.data.implementation.WordListImplementation
import fr.jhelp.data.shared.WordListModel
import fr.jhelp.injector.inject

/**
 * Retrieve the injections for data
 */
fun data()
{
    // Inject the data access
    inject<WordListModel>(WordListImplementation())
}