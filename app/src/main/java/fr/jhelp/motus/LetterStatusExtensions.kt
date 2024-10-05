package fr.jhelp.motus

import androidx.compose.ui.graphics.Color
import fr.jhelp.common.LetterStatus
import fr.jhelp.motus.ui.theme.Black
import fr.jhelp.motus.ui.theme.BlueDark
import fr.jhelp.motus.ui.theme.RedDark
import fr.jhelp.motus.ui.theme.YellowDark

val LetterStatus.color: Color
    get() = when (this)
    {
        LetterStatus.EMPTY       -> BlueDark
        LetterStatus.WELL_PLACED -> RedDark
        LetterStatus.PROPOSED    -> BlueDark
        LetterStatus.INVALID     -> Black
        LetterStatus.MISPLACED   -> YellowDark
        LetterStatus.NOT_PRESENT -> BlueDark
    }
