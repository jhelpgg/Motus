package fr.jhelp.model.implementation

import fr.jhelp.common.DEFAULT_NUMBER_TRIES
import fr.jhelp.common.GameStatus
import fr.jhelp.common.Grid
import fr.jhelp.common.GridInformation
import fr.jhelp.common.LetterStatus
import fr.jhelp.common.NumberLetters
import fr.jhelp.common.Word
import fr.jhelp.common.WordInformation
import fr.jhelp.data.shared.WordListModel
import fr.jhelp.injector.injected
import fr.jhelp.model.shared.MotusService
import fr.jhelp.tools.onResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class MotusServiceImplementation : MotusService
{
    private val wordListModel: WordListModel by injected<WordListModel>()
    private var currentWord = Word(NumberLetters.SIX)
    private var grid = Grid(NumberLetters.SIX, DEFAULT_NUMBER_TRIES)

    private val numberLettersMutable = MutableStateFlow(NumberLetters.SIX)
    override val numberLetters: StateFlow<NumberLetters> = this.numberLettersMutable.asStateFlow()
    private val gridInformationMutable = MutableStateFlow(this.gridInformation())
    override val gridInformation: StateFlow<GridInformation> = this.gridInformationMutable.asStateFlow()
    private val gameStatusMutable = MutableStateFlow(GameStatus.PLAYING)
    override val gameStatus: StateFlow<GameStatus> = this.gameStatusMutable.asStateFlow()

    init
    {
        CoroutineScope(Dispatchers.Default).launch {
            this@MotusServiceImplementation.wordListModel.numberLetters.collect { numberLetters ->
                this@MotusServiceImplementation.numberLetters(numberLetters)
            }
        }
    }

    override fun changeNumberLetters(numberLetters: NumberLetters)
    {
        this.wordListModel.setNumberLetters(numberLetters)
    }

    override fun proposeWord(word: String)
    {
        if (this.gameStatus.value != GameStatus.PLAYING)
        {
            return
        }

        this.gameStatusMutable.value = GameStatus.WAITING
        val numberLetters = this.numberLettersMutable.value
        val numberLettersValue = numberLetters.number

        if (word.length != numberLettersValue)
        {
            val wordInvalid = Word(numberLetters)

            for (index in 0 until numberLettersValue)
            {
                wordInvalid.status(index, LetterStatus.INVALID)
            }

            this.pushWord(wordInvalid)
            return
        }

        val wordUpperCase = word.uppercase()
        this.wordListModel.wordExists(wordUpperCase).onResult { exists ->
            val wordProposed = Word(numberLetters)

            if (exists)
            {
                for (index in 0 until numberLettersValue)
                {
                    wordProposed.propose(index, wordUpperCase[index])
                }

                this.compareAndUpdateWord(wordProposed)
            }
            else
            {
                for (index in 0 until numberLettersValue)
                {
                    wordProposed.status(index, LetterStatus.INVALID)
                }
            }

            this.pushWord(wordProposed)
        }
    }

    override fun newGame()
    {
        when (this.gameStatusMutable.value)
        {
            GameStatus.WIN, GameStatus.LOST ->
                CoroutineScope(Dispatchers.Default).launch {
                    this@MotusServiceImplementation.numberLetters(this@MotusServiceImplementation.numberLettersMutable.value)
                }

            else                            -> Unit
        }
    }

    private fun pushWord(word: Word)
    {
        this.grid.setCurrent(word)

        if (this.grid.nextWord())
        {
            if (this.gameStatusMutable.value == GameStatus.WAITING)
            {
                this.gameStatusMutable.value = GameStatus.PLAYING
            }
        }
        else
        {
            this.gameStatusMutable.value = GameStatus.LOST
        }

        this.gridInformationMutable.value = this.gridInformation()
    }

    private fun gridInformation(): GridInformation
    {
        val wordsInformation = Array(this.grid.numberTries) { indexWord ->
            val word = this.grid[indexWord]
            val letters = Array(word.numberLetters.number) { indexLetter -> word[indexLetter] }
            WordInformation(letters)
        }

        return GridInformation(this.grid.numberLetters, wordsInformation)
    }

    private suspend fun numberLetters(numberLetters: NumberLetters)
    {
        val oneWord = this.wordListModel.oneWord().await()
        this.currentWord = Word(numberLetters)

        for ((index, character) in oneWord.withIndex())
        {
            this.currentWord.propose(index, character)
        }

        this.grid = Grid(numberLetters, DEFAULT_NUMBER_TRIES)
        this.gridInformationMutable.value = this.gridInformation()
        this.gameStatusMutable.value = GameStatus.PLAYING
        this.numberLettersMutable.value = numberLetters
    }

    /**
     * Compare the word to current one and update its letters status
     */
    private fun compareAndUpdateWord(word: Word)
    {
        // Initialize
        for (index in 0 until this.currentWord.numberLetters.number)
        {
            this.currentWord.status(index, LetterStatus.PROPOSED)
        }

        // Compute well placed letters
        var numberWellPlaced = 0

        for (index in 0 until this.currentWord.numberLetters.number)
        {
            if (this.currentWord[index].letter == word[index].letter)
            {
                numberWellPlaced++
                word.status(index, LetterStatus.WELL_PLACED)
                this.currentWord.status(index, LetterStatus.WELL_PLACED)
            }
        }

        if (numberWellPlaced == this.currentWord.numberLetters.number)
        {
            this.gameStatusMutable.value = GameStatus.WIN
            return
        }

        // Compute miss placed letters
        for (index in 0 until this.currentWord.numberLetters.number)
        {
            if (word[index].status != LetterStatus.PROPOSED)
            {
                continue
            }

            for (indexCurrent in 0 until this.currentWord.numberLetters.number)
            {
                if (this.currentWord[indexCurrent].status != LetterStatus.PROPOSED)
                {
                    continue
                }

                if (this.currentWord[indexCurrent].letter == word[index].letter)
                {
                    word.status(index, LetterStatus.MISPLACED)
                    this.currentWord.status(indexCurrent, LetterStatus.MISPLACED)
                    break
                }
            }
        }
    }
}