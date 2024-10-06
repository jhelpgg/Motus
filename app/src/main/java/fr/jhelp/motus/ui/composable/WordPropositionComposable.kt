package fr.jhelp.motus.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.jhelp.injector.inject
import fr.jhelp.injector.injected
import fr.jhelp.motus.R
import fr.jhelp.motus.ui.theme.MotusTheme
import fr.jhelp.viewmodel.dummy.WordPropositionDummy
import fr.jhelp.viewmodel.shared.WordPropositionModel

class WordPropositionComposable
{
    private val wordPropositionModel: WordPropositionModel by injected<WordPropositionModel>()

    @Composable
    fun Show(modifier: Modifier = Modifier)
    {
        val active: Boolean by this.wordPropositionModel.active.collectAsState()
        // Trick for cumulate word typed
        var word: String by remember { mutableStateOf("") }

        ConstraintLayout(modifier = modifier) {
            val (textFiled, button) = this.createRefs()
            TextField(enabled = active,
                      value = word,
                      keyboardActions = KeyboardActions { this.defaultKeyboardAction(ImeAction.None) },
                      keyboardOptions = KeyboardOptions(
                          capitalization = KeyboardCapitalization.Characters,
                          autoCorrect = false,
                          keyboardType = KeyboardType.Text,
                          imeAction = ImeAction.None
                                                       ),
                      onValueChange = { wordTyped -> word = wordTyped.trim().uppercase() },
                      modifier = Modifier.constrainAs(textFiled){
                          width = Dimension.fillToConstraints
                          height = Dimension.wrapContent

                          // top free
                          start.linkTo(parent.start)
                          end.linkTo(button.start)
                          bottom.linkTo(parent.bottom)
                      })

            Button(
                enabled = active,
                onClick = {
                    this@WordPropositionComposable.wordPropositionModel.proposeWord(word)
                    word = ""
                },
                modifier = Modifier.constrainAs(button){
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent

                    top.linkTo(parent.top)
                    // start free
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }) {
                Text(text = stringResource(id = R.string.submit))
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 400)
@Composable
fun WordPropositionComposablePreview()
{
    val wordPropositionModel = WordPropositionDummy(true)
    inject<WordPropositionModel>(wordPropositionModel)
    val wordPropositionComposable = WordPropositionComposable()

    MotusTheme {
        wordPropositionComposable.Show()
    }
}