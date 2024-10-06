package fr.jhelp.viewmodel.implementation

import fr.jhelp.common.GameStatus
import fr.jhelp.injector.injected
import fr.jhelp.model.shared.MotusService
import fr.jhelp.viewmodel.shared.WordPropositionModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class WordPropositionImplementation : WordPropositionModel
{
    private val motusService: MotusService by injected<MotusService>()

    private val activeMutable = MutableStateFlow<Boolean>(true)
    override val active: StateFlow<Boolean> = this.activeMutable.asStateFlow()

    init
    {
        CoroutineScope(Dispatchers.Default).launch {
            this@WordPropositionImplementation.motusService.gameStatus.collect { gameStatus ->
                this@WordPropositionImplementation.activeMutable.value = gameStatus == GameStatus.PLAYING
            }
        }
    }

    override fun proposeWord(word: String)
    {
        this.motusService.proposeWord(word)
    }
}