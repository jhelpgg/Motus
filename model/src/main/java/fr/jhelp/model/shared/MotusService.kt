package fr.jhelp.model.shared

import fr.jhelp.common.GameStatus
import fr.jhelp.common.GridInformation
import fr.jhelp.common.NumberLetters
import kotlinx.coroutines.flow.StateFlow

interface MotusService
{
    val numberLetters: StateFlow<NumberLetters>
    val gridInformation: StateFlow<GridInformation>
    val gameStatus: StateFlow<GameStatus>
    fun newGame()
    fun changeNumberLetters(numberLetters: NumberLetters)
    fun proposeWord(word: String)
}
