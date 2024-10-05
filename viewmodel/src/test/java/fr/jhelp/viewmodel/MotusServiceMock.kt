package fr.jhelp.viewmodel

import fr.jhelp.common.GameStatus
import fr.jhelp.common.GridInformation
import fr.jhelp.common.Letter
import fr.jhelp.common.LetterStatus
import fr.jhelp.common.NumberLetters
import fr.jhelp.common.WordInformation
import fr.jhelp.model.shared.MotusService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MotusServiceMock : MotusService
{
    val numberLettersMutable = MutableStateFlow(NumberLetters.SIX)
    override val numberLetters: StateFlow<NumberLetters> = this.numberLettersMutable
    val wordInformation = Array<WordInformation>(7) { WordInformation(Array<Letter>(6) { Letter(' ', LetterStatus.EMPTY) }) }
    val gridInformationMutable = MutableStateFlow(GridInformation(NumberLetters.SIX, this.wordInformation))
    override val gridInformation: StateFlow<GridInformation> = this.gridInformationMutable
    val gameStatusMutable = MutableStateFlow(GameStatus.PLAYING)
    override val gameStatus: StateFlow<GameStatus> = this.gameStatusMutable
    var nextWordInformation: WordInformation = WordInformation(Array<Letter>(this.numberLettersMutable.value.number) { Letter(' ', LetterStatus.INVALID) })
    var newGameAllowed: Boolean = true
    var nextWordExists: Boolean = true
    private var currentWordIndex = 0

    override fun newGame()
    {
        if (this.newGameAllowed)
        {
            this.initializeGrid()
        }
    }

    override fun changeNumberLetters(numberLetters: NumberLetters)
    {
        this.numberLettersMutable.value = numberLetters
        this.initializeGrid()
    }

    override fun proposeWord(word: String)
    {
        this.gameStatusMutable.value = GameStatus.WAITING

        if (this.currentWordIndex >= this.wordInformation.size)
        {
            this.gameStatusMutable.value = GameStatus.LOST
            return
        }

        val wordProposed =
            if (this.nextWordExists.not() || word.length != this.numberLettersMutable.value.number)
            {
                WordInformation(Array<Letter>(this.numberLettersMutable.value.number) { Letter(' ', LetterStatus.INVALID) })
            }
            else
            {
                this.nextWordInformation
            }

        this.wordInformation[this.currentWordIndex] = wordProposed
        this.currentWordIndex++
        var win = true

        for (index in 0 until this.numberLettersMutable.value.number)
        {
            if (wordProposed.letters[index].status != LetterStatus.WELL_PLACED)
            {
                win = false
                break
            }
        }

        if (win)
        {
            this.gameStatusMutable.value = GameStatus.WIN
            return
        }

        if (this.currentWordIndex >= this.wordInformation.size)
        {
            this.gameStatusMutable.value = GameStatus.LOST
            return
        }

        this.gameStatusMutable.value = GameStatus.PLAYING
    }

    private fun initializeGrid()
    {
        for (index in wordInformation.indices)
        {
            for (letterIndex in 0 until this.numberLettersMutable.value.number)
            {
                this.wordInformation[index] = WordInformation(Array<Letter>(this.numberLettersMutable.value.number) { Letter(' ', LetterStatus.EMPTY) })
            }
        }

        this.gameStatusMutable.value = GameStatus.PLAYING
        this.currentWordIndex = 0
    }
}