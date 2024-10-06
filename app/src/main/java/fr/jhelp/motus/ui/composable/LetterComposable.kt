package fr.jhelp.motus.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.jhelp.common.Letter
import fr.jhelp.common.LetterStatus
import fr.jhelp.motus.color
import fr.jhelp.motus.ui.theme.MotusTheme
import fr.jhelp.motus.ui.theme.Typography
import fr.jhelp.motus.ui.theme.White

/**
 * Composable for show a letter depends on its status
 */
@Composable
fun LetterComposable(letter: Letter, modifier: Modifier = Modifier)
{
    Text(
        text = letter.letter.toString(),
        color = White,
        fontStyle = Typography.bodyLarge.fontStyle,
        modifier = modifier
            .padding(1.dp)
            .background(letter.status.color)
        )
}

@Preview(showBackground = true)
@Composable
fun EmptyLetterComposable()
{
    MotusTheme {
        LetterComposable(Letter(' ', LetterStatus.EMPTY))
    }
}

@Preview(showBackground = true)
@Composable
fun InvalidLetterComposable()
{
    MotusTheme {
        LetterComposable(Letter(' ', LetterStatus.INVALID))
    }
}

@Preview(showBackground = true)
@Composable
fun ProposedLetterComposable()
{
    MotusTheme {
        LetterComposable(Letter('A', LetterStatus.PROPOSED))
    }
}

@Preview(showBackground = true)
@Composable
fun MissPlacedLetterComposable()
{
    MotusTheme {
        LetterComposable(Letter('A', LetterStatus.MISPLACED))
    }
}

@Preview(showBackground = true)
@Composable
fun WellPlacedLetterComposable()
{
    MotusTheme {
        LetterComposable(Letter('A', LetterStatus.WELL_PLACED))
    }
}

@Preview(showBackground = true)
@Composable
fun NotPresentLetterComposable()
{
    MotusTheme {
        LetterComposable(Letter('A', LetterStatus.NOT_PRESENT))
    }
}