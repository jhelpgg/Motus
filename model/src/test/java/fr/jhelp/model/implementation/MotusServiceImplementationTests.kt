package fr.jhelp.model.implementation

import fr.jhelp.common.GameStatus
import fr.jhelp.common.LetterStatus
import fr.jhelp.common.NumberLetters
import fr.jhelp.data.dummy.WordListDummy
import fr.jhelp.data.shared.WordListModel
import fr.jhelp.injector.inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test

class MotusServiceImplementationTests
{
    companion object
    {
        private val possibleWords6 = listOf("PARTIE", "DEPUIS", "EQUIPE", "PATRIE")
        private val possibleWords7 = listOf("PREMIER", "COMMUNE", "FAMILLE", "PERIODE")
        private val possibleWords8 = listOf("PREMIERE", "FRANCAIS", "HISTOIRE", "PARCOURS")

        private val numberLettersMutable = MutableStateFlow(NumberLetters.SIX)
        var wordChoose: String = possibleWords6[0]
            private set
        var possibleWords = possibleWords6
            private set

        @JvmStatic
        @BeforeClass
        fun initialize()
        {
            inject<WordListModel>(mockWordListModel())
        }

        private fun mockWordListModel(): WordListModel
        {
            return WordListDummy(numberLettersMutable,
                                 setNumberLettersAction = Companion::setNumberLetters,
                                 oneWordAction = { wordChoose },
                                 wordExistsAction = { word -> possibleWords.contains(word) })
        }

        fun setNumberLetters(numberLetters: NumberLetters)
        {
            possibleWords = when (numberLetters)
            {
                NumberLetters.SIX -> possibleWords6
                NumberLetters.SEVEN -> possibleWords7
                NumberLetters.EIGHT -> possibleWords8
            }

            wordChoose = possibleWords[0]
            numberLettersMutable.value = numberLetters
        }
    }

    @Test
    fun proposeGoodWord()
    {
        MotusServiceImplementationTests.setNumberLetters(NumberLetters.SIX)
        val motusService = MotusServiceImplementation()
        this.proposeWord(MotusServiceImplementationTests.wordChoose, motusService)

        Assert.assertEquals(GameStatus.WIN, motusService.gameStatus.value)

        val gridInformation = motusService.gridInformation.value
        Assert.assertEquals(NumberLetters.SIX, gridInformation.numberLetters)
        val word = gridInformation.words[0]
        Assert.assertEquals(6, word.letters.size)

        for ((index, letter) in word.letters.withIndex())
        {
            Assert.assertEquals("Letter $letter an index $index should be well placed", LetterStatus.WELL_PLACED, letter.status)
        }
    }

    @Test
    fun proposeBadWord()
    {
        MotusServiceImplementationTests.setNumberLetters(NumberLetters.SIX)
        val motusService = MotusServiceImplementation()

        // for six letters good word is "PARTIE"
        // so here we should have
        // * P well placed
        // * A well placed
        // * T miss placed
        // * R miss placed
        // * I well placed
        // * E well placed
        this.proposeWord("PATRIE", motusService)

        Assert.assertEquals(GameStatus.PLAYING, motusService.gameStatus.value)

        val gridInformation = motusService.gridInformation.value
        Assert.assertEquals(NumberLetters.SIX, gridInformation.numberLetters)
        val word = gridInformation.words[0]
        Assert.assertEquals(6, word.letters.size)

        // P well placed
        Assert.assertEquals('P', word.letters[0].letter)
        Assert.assertEquals(LetterStatus.WELL_PLACED, word.letters[0].status)
        // A well placed
        Assert.assertEquals('A', word.letters[1].letter)
        Assert.assertEquals(LetterStatus.WELL_PLACED, word.letters[1].status)
        // T miss placed
        Assert.assertEquals('T', word.letters[2].letter)
        Assert.assertEquals(LetterStatus.MISPLACED, word.letters[2].status)
        // R miss placed
        Assert.assertEquals('R', word.letters[3].letter)
        Assert.assertEquals(LetterStatus.MISPLACED, word.letters[3].status)
        // I well placed
        Assert.assertEquals('I', word.letters[4].letter)
        Assert.assertEquals(LetterStatus.WELL_PLACED, word.letters[4].status)
        // E well placed
        Assert.assertEquals('E', word.letters[5].letter)
        Assert.assertEquals(LetterStatus.WELL_PLACED, word.letters[5].status)
    }

    @Test
    fun proposeInvalidWord()
    {
        MotusServiceImplementationTests.setNumberLetters(NumberLetters.SIX)
        val motusService = MotusServiceImplementation()
        this.proposeWord("INVALI", motusService)

        Assert.assertEquals(GameStatus.PLAYING, motusService.gameStatus.value)

        val gridInformation = motusService.gridInformation.value
        Assert.assertEquals(NumberLetters.SIX, gridInformation.numberLetters)
        val word = gridInformation.words[0]
        Assert.assertEquals(6, word.letters.size)

        for (letter in word.letters)
        {
            Assert.assertEquals(LetterStatus.INVALID, letter.status)
        }
    }

    @Test
    fun expireNumberTries()
    {
        MotusServiceImplementationTests.setNumberLetters(NumberLetters.SIX)
        val motusService = MotusServiceImplementation()
        val numberTries = motusService.gridInformation.value.words.size

        for(i in 0 until numberTries)
        {
            this.proposeWord(possibleWords6[1], motusService)
        }

        Assert.assertEquals(GameStatus.LOST, motusService.gameStatus.value)
    }

    private fun proposeWord(word:String, motusService: MotusServiceImplementation)
    {
        motusService.proposeWord(word)

        runBlocking {
            do
            {
                delay(1024)
            }
            while (motusService.gameStatus.value == GameStatus.WAITING)
        }
    }
}