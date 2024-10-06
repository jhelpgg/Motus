package fr.jhelp.motus

import fr.jhelp.common.NumberLetters

val NumberLetters.haveNext: Boolean get() = this != NumberLetters.EIGHT

val NumberLetters.havePrevious: Boolean get() = this != NumberLetters.SIX

val NumberLetters.next: NumberLetters
    get() =
        when (this)
        {
            NumberLetters.SIX   -> NumberLetters.SEVEN
            NumberLetters.SEVEN -> NumberLetters.EIGHT
            NumberLetters.EIGHT -> NumberLetters.EIGHT
        }

val NumberLetters.previous: NumberLetters
    get() =
        when (this)
        {
            NumberLetters.SIX   -> NumberLetters.SIX
            NumberLetters.SEVEN -> NumberLetters.SIX
            NumberLetters.EIGHT -> NumberLetters.SEVEN
        }