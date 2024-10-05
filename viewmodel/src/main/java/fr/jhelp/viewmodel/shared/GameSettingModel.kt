package fr.jhelp.viewmodel.shared

import fr.jhelp.common.NumberLetters
import kotlinx.coroutines.flow.StateFlow

interface GameSettingModel
{
    val gameSettingInformation : StateFlow<GameSettingInformation>

    fun newGame()

    fun numberLetters(numberLetters:NumberLetters)
}