package fr.jhelp.viewmodel.shared

import kotlinx.coroutines.flow.SharedFlow

interface WordPropositionModel
{
    val active : SharedFlow<Boolean>

    fun proposeWord(word: String)
}