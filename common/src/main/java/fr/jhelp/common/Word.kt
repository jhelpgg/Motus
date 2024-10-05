package fr.jhelp.common

/**
 * Represents a word
 */
class Word(val numberLetters: NumberLetters)
{
    private val letters = Array<Letter>(this.numberLetters.number) { Letter(' ', LetterStatus.EMPTY) }

    operator fun get(index: Int): Letter = this.letters[index]

    fun propose(index: Int, letter: Char)
    {
        if (letter in 'A'..'Z')
        {
            this.letters[index] = Letter(letter, LetterStatus.PROPOSED)
        }
    }

    fun status(index: Int, status: LetterStatus)
    {
        this.letters[index] = this.letters[index].copy(status = status)
    }
}