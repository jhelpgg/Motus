package fr.jhelp.data.implementation

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import fr.jhelp.common.NumberLetters
import fr.jhelp.injector.inject
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WordListImplementationTests
{
    @Test
    fun getWords()
    {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        inject<Context>(appContext)
        val wordList = WordListImplementation()

        var word = runBlocking { wordList.oneWord().await() }
        Assert.assertEquals("Word '$word' should have 6 letters", 6, word.length)

        wordList.setNumberLetters(NumberLetters.SEVEN)
        word = runBlocking { wordList.oneWord().await() }
        Assert.assertEquals("Word '$word' should have 7 letters", 7, word.length)

        wordList.setNumberLetters(NumberLetters.EIGHT)
        word = runBlocking { wordList.oneWord().await() }
        Assert.assertEquals("Word '$word' should have 8 letters", 8, word.length)
    }
}