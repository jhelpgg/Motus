package fr.jhelp.viewmodel.shared

import kotlinx.coroutines.flow.StateFlow

interface WordPropositionModel
{
    val active: StateFlow<Boolean>

    fun proposeWord(word: String)
}