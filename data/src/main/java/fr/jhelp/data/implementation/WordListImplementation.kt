package fr.jhelp.data.implementation

import android.content.Context
import fr.jhelp.data.shared.NumberLetters
import fr.jhelp.data.shared.WordListModel
import fr.jhelp.injector.injected
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class WordListImplementation : WordListModel
{
    private val context: Context by injected<Context>()

    private val numberLettersMutable = MutableStateFlow(NumberLetters.SIX)
    private val mutex = Mutex()
    private val words = ArrayList<String>()
    private lateinit var deffered: Deferred<String>

    override val numberLetters: StateFlow<NumberLetters> = this.numberLettersMutable.asStateFlow()

    init
    {
        this.deffered = CoroutineScope(Dispatchers.IO).async {
            this@WordListImplementation.mutex.withLock { this@WordListImplementation.loadWords() }
            ""
        }
    }

    override fun setNumberLetters(numberLetters: NumberLetters)
    {
        if (numberLetters != this.numberLettersMutable.value)
        {
            val deferred = this.deffered
            this.deffered = CoroutineScope(Dispatchers.IO).async {
                deferred.await()
                this@WordListImplementation.mutex.withLock {
                    this@WordListImplementation.numberLettersMutable.value = numberLetters
                    this@WordListImplementation.loadWords()
                }
                ""
            }
        }
    }

    override fun oneWord(): Deferred<String>
    {
        val deferred = this.deffered
        this.deffered = CoroutineScope(Dispatchers.Default).async {
            deferred.await()
            val word = this@WordListImplementation.mutex.withLock {
                this@WordListImplementation.words.random()
            }
            word
        }
        return this.deffered
    }

    private fun loadWords()
    {
        this.words.clear()
        val assetPath = "words/words_${this.numberLettersMutable.value.number}_letters"
        this.context.assets.open(assetPath).bufferedReader().use { reader ->
            reader.readLines().forEach { line -> this.words.add(line) }
        }
    }
}