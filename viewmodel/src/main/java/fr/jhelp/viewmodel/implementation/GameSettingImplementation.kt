package fr.jhelp.viewmodel.implementation

import fr.jhelp.common.GameStatus
import fr.jhelp.common.NumberLetters
import fr.jhelp.injector.injected
import fr.jhelp.model.shared.MotusService
import fr.jhelp.viewmodel.shared.GameSettingInformation
import fr.jhelp.viewmodel.shared.GameSettingModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class GameSettingImplementation : GameSettingModel
{
    private val mutex = Mutex()
    private val motusService: MotusService by injected<MotusService>()
    private val gameSettingInformationMutable = MutableStateFlow<GameSettingInformation>(GameSettingInformation(NumberLetters.SIX, GameStatus.PLAYING))
    override val gameSettingInformation: StateFlow<GameSettingInformation> = this.gameSettingInformationMutable.asStateFlow()

    init
    {
        CoroutineScope(Dispatchers.Default).launch {
            this@GameSettingImplementation.motusService.gameStatus.collect { gameStatus ->
                this@GameSettingImplementation.mutex.withLock {
                    this@GameSettingImplementation.gameSettingInformationMutable.value = this@GameSettingImplementation.gameSettingInformationMutable.value.copy(gameStatus = gameStatus)
                }
            }
        }

        CoroutineScope(Dispatchers.Default).launch {
            this@GameSettingImplementation.motusService.numberLetters.collect { numberLetters ->
                this@GameSettingImplementation.mutex.withLock {
                    this@GameSettingImplementation.gameSettingInformationMutable.value = this@GameSettingImplementation.gameSettingInformationMutable.value.copy(numberLetters = numberLetters)
                }
            }
        }
    }

    override fun newGame()
    {
        this.motusService.newGame()
    }

    override fun numberLetters(numberLetters: NumberLetters)
    {
        this.motusService.changeNumberLetters(numberLetters)
    }
}