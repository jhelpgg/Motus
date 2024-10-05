package fr.jhelp.common

/**
 * Grid of words to trace the tries
 */
class Grid(val numberLetters: NumberLetters, val numberTries: Int)
{
    private val words = Array<Word>(this.numberTries) { Word(this.numberLetters) }
    var currentWordIndex = 0
        private set

    operator fun get(index: Int): Word = this.words[index]

    fun setCurrent(word: Word)
    {
        if (this.currentWordIndex >= this.numberTries)
        {
            throw IllegalStateException("No more tries")
        }

        this.words[this.currentWordIndex] = word
    }

    fun nextWord(): Boolean
    {
        return if (this.currentWordIndex >= this.numberTries)
        {
            false
        }
        else
        {
            this.currentWordIndex++
            true
        }
    }
}