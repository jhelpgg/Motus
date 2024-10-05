package fr.jhelp.viewmodel.implementation

import fr.jhelp.common.GridInformation
import fr.jhelp.common.Letter
import fr.jhelp.common.LetterStatus
import fr.jhelp.common.NumberLetters
import fr.jhelp.common.WordInformation
import fr.jhelp.injector.inject
import fr.jhelp.model.shared.MotusService
import fr.jhelp.viewmodel.MotusServiceMock
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test

class GridImplementationTests
{
    companion object
    {
        val motusServiceMock = MotusServiceMock()

        @JvmStatic
        @BeforeClass
        fun initialize()
        {
            inject<MotusService>(motusServiceMock)
        }
    }

    @Test
    fun gridInformationTest()
    {
        val grid = GridImplementation()
        motusServiceMock.gridInformationMutable.value = GridInformation(NumberLetters.SIX,
                                                                        Array<WordInformation>(1) {
                                                                            WordInformation(Array<Letter>(1) {
                                                                                Letter('A', LetterStatus.PROPOSED)
                                                                            })
                                                                        })
        var gridInformation = grid.gridInformation.value
        Assert.assertEquals(NumberLetters.SIX, gridInformation.numberLetters)
        var words = gridInformation.words
        Assert.assertEquals(1, words.size)
        var letters = words[0].letters
        Assert.assertEquals(1, letters.size)
        var letter = letters[0]
        Assert.assertEquals('A', letter.letter)
        Assert.assertEquals(LetterStatus.PROPOSED, letter.status)

        motusServiceMock.gridInformationMutable.value = GridInformation(NumberLetters.SEVEN,
                                                                        Array<WordInformation>(1) {
                                                                            WordInformation(Array<Letter>(1) {
                                                                                Letter('B', LetterStatus.WELL_PLACED)
                                                                            })
                                                                        })
        gridInformation = grid.gridInformation.value
        Assert.assertEquals(NumberLetters.SEVEN, gridInformation.numberLetters)
        words = gridInformation.words
        Assert.assertEquals(1, words.size)
        letters = words[0].letters
        Assert.assertEquals(1, letters.size)
        letter = letters[0]
        Assert.assertEquals('B', letter.letter)
        Assert.assertEquals(LetterStatus.WELL_PLACED, letter.status)
    }
}