package fr.jhelp.model.dummy

import fr.jhelp.common.GameStatus
import fr.jhelp.common.GridInformation
import fr.jhelp.common.Letter
import fr.jhelp.common.LetterStatus
import fr.jhelp.common.NumberLetters
import fr.jhelp.common.WordInformation
import fr.jhelp.model.shared.MotusService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MotusServiceDummy(override val numberLetters: StateFlow<NumberLetters>,
                        override val gridInformation: StateFlow<GridInformation>,
                        override val gameStatus: StateFlow<GameStatus>,
                        val changeNumberLettersAction: (NumberLetters) -> Unit = {},
                        val proposeWordAction: (String) -> Unit = {},
                        val newGameAction: () -> Unit = {}) : MotusService
{
    constructor(numberLetters: NumberLetters = NumberLetters.SIX,
                grid: GridInformation = GridInformation(numberLetters, arrayOf(WordInformation(arrayOf(Letter('A', LetterStatus.PROPOSED))))),
                gameStatus: GameStatus) :
            this(MutableStateFlow(numberLetters), MutableStateFlow(grid), MutableStateFlow(gameStatus))

    override fun changeNumberLetters(numberLetters: NumberLetters)
    {
        this.changeNumberLettersAction(numberLetters)
    }

    override fun proposeWord(word: String)
    {
        this.proposeWordAction(word)
    }

    override fun newGame()
    {
        this.newGameAction()
    }
}