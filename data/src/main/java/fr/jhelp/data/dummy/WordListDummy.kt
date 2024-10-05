package fr.jhelp.data.dummy

import fr.jhelp.common.NumberLetters
import fr.jhelp.data.shared.WordListModel
import fr.jhelp.tools.Immediate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WordListDummy(override val numberLetters: StateFlow<NumberLetters>,
                    private val setNumberLettersAction: (NumberLetters) -> Unit = {},
                    private val oneWordAction: suspend () -> String = { "" },
                    private val wordExistsAction: suspend (String) -> Boolean = { true }) : WordListModel
{
    constructor(numberLetters: NumberLetters = NumberLetters.SIX) :
            this(MutableStateFlow(numberLetters),
                 {},
                 {
                     when (numberLetters)
                     {
                         NumberLetters.SIX -> "PARTIE"
                         NumberLetters.SEVEN -> "PREMIER"
                         NumberLetters.EIGHT -> "PREMIERE"
                     }
                 })

    override fun setNumberLetters(numberLetters: NumberLetters)
    {
        this.setNumberLettersAction(numberLetters)
    }

    override fun wordExists(word: String): Deferred<Boolean> =
        CoroutineScope(Dispatchers.Immediate).async { this@WordListDummy.wordExistsAction(word) }

    override fun oneWord(): Deferred<String> =
        CoroutineScope(Dispatchers.Immediate).async { this@WordListDummy.oneWordAction() }
}