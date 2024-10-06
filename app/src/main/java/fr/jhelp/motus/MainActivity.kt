package fr.jhelp.motus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.jhelp.motus.ui.composable.GameSettingComposable
import fr.jhelp.motus.ui.composable.GridComposable
import fr.jhelp.motus.ui.composable.WordPropositionComposable
import fr.jhelp.motus.ui.theme.MotusTheme

class MainActivity : ComponentActivity()
{
    private val gameSettingComposable: GameSettingComposable by lazy { GameSettingComposable() }
    private val gridComposable: GridComposable by lazy { GridComposable() }
    private val wordPropositionComposable: WordPropositionComposable by lazy { WordPropositionComposable() }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            MotusTheme {
                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    val (gameSetting, grid, wordProposition) = this.createRefs()

                    this@MainActivity.gameSettingComposable.Show(
                        modifier = Modifier.constrainAs(gameSetting) {
                            width = Dimension.fillToConstraints
                            height = Dimension.wrapContent

                            top.linkTo(parent.top, 8.dp)
                            start.linkTo(parent.start, 8.dp)
                            end.linkTo(parent.end, 8.dp)
                            // bottom free
                        })

                    this@MainActivity.gridComposable.Show(
                        modifier = Modifier.constrainAs(grid) {
                            width = Dimension.wrapContent
                            height = Dimension.wrapContent

                            // top free
                            start.linkTo(parent.start, 8.dp)
                            end.linkTo(parent.end, 8.dp)
                            bottom.linkTo(wordProposition.top, 8.dp)
                        }
                                                         )

                    this@MainActivity.wordPropositionComposable.Show(
                        modifier = Modifier.constrainAs(wordProposition) {
                            width = Dimension.fillToConstraints
                            height = Dimension.wrapContent

                            // top free
                            start.linkTo(parent.start, 8.dp)
                            end.linkTo(parent.end, 8.dp)
                            bottom.linkTo(parent.bottom, 8.dp)
                        }
                                                                    )
                }
            }
        }
    }
}
