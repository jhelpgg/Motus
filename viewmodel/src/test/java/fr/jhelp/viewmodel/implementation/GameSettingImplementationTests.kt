package fr.jhelp.viewmodel.implementation

import fr.jhelp.common.GameStatus
import fr.jhelp.common.Letter
import fr.jhelp.common.LetterStatus
import fr.jhelp.common.NumberLetters
import fr.jhelp.common.WordInformation
import fr.jhelp.injector.inject
import fr.jhelp.model.shared.MotusService
import fr.jhelp.viewmodel.MotusServiceMock
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test

class GameSettingImplementationTests
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
    fun newGameTest()
    {
        motusServiceMock.changeNumberLetters(NumberLetters.SIX)
        val gameSettingImplementation = GameSettingImplementation()
        motusServiceMock.nextWordInformation = WordInformation(Array<Letter>(6) { Letter('A', LetterStatus.WELL_PLACED) })
        motusServiceMock.proposeWord("AAAAAA")
        this.waitGameStatus(gameSettingImplementation)
        Assert.assertEquals(GameStatus.WIN, gameSettingImplementation.gameSettingInformation.value.gameStatus)

        gameSettingImplementation.newGame()
        this.waitGameStatus(gameSettingImplementation)
        Assert.assertEquals(GameStatus.PLAYING, gameSettingImplementation.gameSettingInformation.value.gameStatus)
    }

    @Test
    fun numberLettersTest()
    {
        motusServiceMock.changeNumberLetters(NumberLetters.SIX)
        val gameSettingImplementation = GameSettingImplementation()
        Assert.assertEquals(NumberLetters.SIX, gameSettingImplementation.gameSettingInformation.value.numberLetters)
        gameSettingImplementation.numberLetters(NumberLetters.SEVEN)
        this.waitGameStatus(gameSettingImplementation)
        Assert.assertEquals(NumberLetters.SEVEN, gameSettingImplementation.gameSettingInformation.value.numberLetters)
        gameSettingImplementation.numberLetters(NumberLetters.EIGHT)
        this.waitGameStatus(gameSettingImplementation)
        Assert.assertEquals(NumberLetters.EIGHT, gameSettingImplementation.gameSettingInformation.value.numberLetters)
        gameSettingImplementation.numberLetters(NumberLetters.SIX)
        this.waitGameStatus(gameSettingImplementation)
        Assert.assertEquals(NumberLetters.SIX, gameSettingImplementation.gameSettingInformation.value.numberLetters)
    }

    private fun waitGameStatus(gameSettingImplementation: GameSettingImplementation) {
        runBlocking {
            do
            {
                delay(1024)
            }
            while (gameSettingImplementation.gameSettingInformation.value.gameStatus == GameStatus.WAITING)
        }
    }
}