package fr.jhelp.motus.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.jhelp.common.GameStatus
import fr.jhelp.common.NumberLetters
import fr.jhelp.injector.inject
import fr.jhelp.injector.injected
import fr.jhelp.motus.R
import fr.jhelp.motus.haveNext
import fr.jhelp.motus.havePrevious
import fr.jhelp.motus.next
import fr.jhelp.motus.previous
import fr.jhelp.motus.ui.theme.MotusTheme
import fr.jhelp.viewmodel.dummy.GameSettingDummy
import fr.jhelp.viewmodel.shared.GameSettingInformation
import fr.jhelp.viewmodel.shared.GameSettingModel

class GameSettingComposable
{
    private val gameSettingModel: GameSettingModel by injected<GameSettingModel>()

    @Composable
    fun Show(modifier: Modifier = Modifier)
    {
        val gameSettingInformation: GameSettingInformation by this.gameSettingModel.gameSettingInformation.collectAsState()

        Column(modifier) {
            Row {
                Button(enabled = gameSettingInformation.gameStatus == GameStatus.WIN || gameSettingInformation.gameStatus == GameStatus.LOST,
                       onClick = { this@GameSettingComposable.gameSettingModel.newGame() }) {
                    Image(
                        painter = painterResource(id = R.drawable.reuse),
                        contentDescription = stringResource(id = R.string.newGame),
                        modifier = Modifier.size(16.dp)
                         )
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                   ) {
                    Button(enabled = gameSettingInformation.numberLetters.havePrevious,
                           onClick = { this@GameSettingComposable.gameSettingModel.numberLetters(gameSettingInformation.numberLetters.previous) }) {
                        Image(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = stringResource(id = R.string.previousNumberLetters, gameSettingInformation.numberLetters.previous.number),
                            modifier = Modifier.size(16.dp)
                             )
                    }

                    Text(text = gameSettingInformation.numberLetters.number.toString())

                    Button(enabled = gameSettingInformation.numberLetters.haveNext,
                           onClick = { this@GameSettingComposable.gameSettingModel.numberLetters(gameSettingInformation.numberLetters.next) }) {
                        Image(
                            painter = painterResource(id = R.drawable.next),
                            contentDescription = stringResource(id = R.string.nextNumberLetters, gameSettingInformation.numberLetters.next.number),
                            modifier = Modifier.size(16.dp)
                             )
                    }
                }
            }

            Text(text = gameSettingInformation.gameStatus.name)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SixLettersGameSettingComposablePreview()
{
    val gameSettingInformation = GameSettingInformation(NumberLetters.SIX, GameStatus.PLAYING)
    val gameSettingModel = GameSettingDummy(gameSettingInformation)
    inject<GameSettingModel>(gameSettingModel)
    val gameSettingComposable = GameSettingComposable()

    MotusTheme {
        gameSettingComposable.Show()
    }
}

@Preview(showBackground = true)
@Composable
fun SevenLettersGameSettingComposablePreview()
{
    val gameSettingInformation = GameSettingInformation(NumberLetters.SEVEN, GameStatus.LOST)
    val gameSettingModel = GameSettingDummy(gameSettingInformation)
    inject<GameSettingModel>(gameSettingModel)
    val gameSettingComposable = GameSettingComposable()

    MotusTheme {
        gameSettingComposable.Show()
    }
}

@Preview(showBackground = true)
@Composable
fun EightLettersGameSettingComposablePreview()
{
    val gameSettingInformation = GameSettingInformation(NumberLetters.EIGHT, GameStatus.WIN)
    val gameSettingModel = GameSettingDummy(gameSettingInformation)
    inject<GameSettingModel>(gameSettingModel)
    val gameSettingComposable = GameSettingComposable()

    MotusTheme {
        gameSettingComposable.Show()
    }
}