package fr.jhelp.motus.ui.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.jhelp.common.Letter
import fr.jhelp.common.LetterStatus
import fr.jhelp.common.WordInformation
import fr.jhelp.motus.ui.theme.MotusTheme

@Composable
fun WordComposable(wordInformation: WordInformation, modifier: Modifier = Modifier)
{
    Row(modifier = modifier) {
        for (letter in wordInformation.letters)
        {
            LetterComposable(letter)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyWordComposable()
{
    MotusTheme {
        WordComposable(WordInformation(Array<Letter>(6) { Letter(' ', LetterStatus.EMPTY) }))
    }
}

@Preview(showBackground = true)
@Composable
fun SixLettersWordComposable()
{
    MotusTheme {
        WordComposable(WordInformation(Array<Letter>(6) { index -> Letter(('A'.code + index).toChar(), letterStatus(index)) }))
    }
}

@Preview(showBackground = true)
@Composable
fun SevenLettersWordComposable()
{
    MotusTheme {
        WordComposable(WordInformation(Array<Letter>(7) { index -> Letter(('A'.code + index).toChar(), letterStatus(index)) }))
    }
}

@Preview(showBackground = true)
@Composable
fun EightLettersWordComposable()
{
    MotusTheme {
        WordComposable(WordInformation(Array<Letter>(8) { index -> Letter(('A'.code + index).toChar(), letterStatus(index)) }))
    }
}

@Preview(showBackground = true)
@Composable
fun InvalidWordComposable()
{
    MotusTheme {
        WordComposable(WordInformation(Array<Letter>(6) { Letter(' ', LetterStatus.INVALID) }))
    }
}


private fun letterStatus(index: Int): LetterStatus
{
    return when (index % 3)
    {
        0    -> LetterStatus.NOT_PRESENT
        1    -> LetterStatus.WELL_PLACED
        else -> LetterStatus.MISPLACED
    }
}