package fr.jhelp.motus.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.jhelp.common.GridInformation
import fr.jhelp.common.Letter
import fr.jhelp.common.LetterStatus
import fr.jhelp.common.NumberLetters
import fr.jhelp.common.WordInformation
import fr.jhelp.injector.inject
import fr.jhelp.injector.injected
import fr.jhelp.motus.ui.theme.MotusTheme
import fr.jhelp.viewmodel.dummy.GridDummy
import fr.jhelp.viewmodel.shared.GridModel

class GridComposable
{
    private val gridModel: GridModel by injected<GridModel>()

    @Composable
    fun Show(modifier: Modifier = Modifier)
    {
        val gridInformation: GridInformation by this.gridModel.gridInformation.collectAsState()

        Column(modifier = modifier) {
            for (wordInformation in gridInformation.words)
            {
                WordComposable(wordInformation = wordInformation)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GridComposablePreview()
{
    val gridInformation = GridInformation(
        NumberLetters.SIX,
        arrayOf(
            WordInformation(
                arrayOf(
                    Letter('A', LetterStatus.MISPLACED),
                    Letter('V', LetterStatus.NOT_PRESENT),
                    Letter('I', LetterStatus.MISPLACED),
                    Letter('O', LetterStatus.NOT_PRESENT),
                    Letter('N', LetterStatus.NOT_PRESENT),
                    Letter('S', LetterStatus.NOT_PRESENT)
                       )
                           ),
            WordInformation(
                arrayOf(
                    Letter('B', LetterStatus.NOT_PRESENT),
                    Letter('E', LetterStatus.MISPLACED),
                    Letter('L', LetterStatus.NOT_PRESENT),
                    Letter('L', LetterStatus.NOT_PRESENT),
                    Letter('E', LetterStatus.NOT_PRESENT),
                    Letter('S', LetterStatus.NOT_PRESENT)
                       )
                           ),
            WordInformation(Array<Letter>(6){Letter(' ', LetterStatus.INVALID)}),
            WordInformation(
                arrayOf(
                    Letter('P', LetterStatus.WELL_PLACED),
                    Letter('A', LetterStatus.WELL_PLACED),
                    Letter('T', LetterStatus.MISPLACED),
                    Letter('R', LetterStatus.MISPLACED),
                    Letter('I', LetterStatus.WELL_PLACED),
                    Letter('E', LetterStatus.WELL_PLACED)
                       )
                           ),
            WordInformation(
                arrayOf(
                    Letter('P', LetterStatus.WELL_PLACED),
                    Letter('A', LetterStatus.WELL_PLACED),
                    Letter('R', LetterStatus.WELL_PLACED),
                    Letter('T', LetterStatus.WELL_PLACED),
                    Letter('I', LetterStatus.WELL_PLACED),
                    Letter('E', LetterStatus.WELL_PLACED)
                       )
                           ),
            WordInformation(Array<Letter>(6){Letter(' ', LetterStatus.EMPTY)}),
            WordInformation(Array<Letter>(6){Letter(' ', LetterStatus.EMPTY)})
               )
                                         )
    val gridModel = GridDummy(gridInformation)
    inject<GridModel>(gridModel)
    val gridComposable = GridComposable()

    MotusTheme {
        gridComposable.Show()
    }
}
