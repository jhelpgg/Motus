package fr.jhelp.data.shared

import fr.jhelp.common.NumberLetters
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.StateFlow

interface WordListModel
{
    val numberLetters : StateFlow<NumberLetters>

    fun setNumberLetters(numberLetters: NumberLetters)

    fun oneWord() : Deferred<String>

    fun wordExists(word: String) : Deferred<Boolean>
}