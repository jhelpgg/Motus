package fr.jhelp.viewmodel.dummy

import fr.jhelp.viewmodel.shared.WordPropositionModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow

class WordPropositionDummy(override val active: SharedFlow<Boolean>,
                           private val proposeWordAction: (String) -> Unit = {}) : WordPropositionModel
{
    constructor(active: Boolean) : this(MutableStateFlow(active))

    override fun proposeWord(word: String)
    {
        this.proposeWordAction(word)
    }
}