package fr.jhelp.data

import fr.jhelp.data.implementation.WordListImplementation
import fr.jhelp.data.shared.WordListModel
import fr.jhelp.injector.inject

fun data()
{
    inject<WordListModel>(WordListImplementation())
}