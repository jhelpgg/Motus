package fr.jhelp.viewmodel.dummy

import fr.jhelp.viewmodel.shared.WordPropositionModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WordPropositionDummy(override val active: StateFlow<Boolean>,
                           private val proposeWordAction: (String) -> Unit = {}) : WordPropositionModel
{
    constructor(active: Boolean) : this(MutableStateFlow(active))

    override fun proposeWord(word: String)
    {
        this.proposeWordAction(word)
    }
}