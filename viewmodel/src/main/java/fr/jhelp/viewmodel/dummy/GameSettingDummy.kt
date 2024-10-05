package fr.jhelp.viewmodel.dummy

import fr.jhelp.common.NumberLetters
import fr.jhelp.viewmodel.shared.GameSettingInformation
import fr.jhelp.viewmodel.shared.GameSettingModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameSettingDummy(override val gameSettingInformation: StateFlow<GameSettingInformation>,
                       private val newGameAction: () -> Unit = {},
                       private val numberLettersAction: (NumberLetters) -> Unit = {}) : GameSettingModel
{
    constructor(gameSettingInformation: GameSettingInformation) : this(MutableStateFlow(gameSettingInformation))

    override fun newGame()
    {
        this.newGameAction()
    }

    override fun numberLetters(numberLetters: NumberLetters)
    {
        this.numberLettersAction(numberLetters)
    }
}