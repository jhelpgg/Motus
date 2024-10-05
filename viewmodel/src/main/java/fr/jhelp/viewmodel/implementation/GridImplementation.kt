package fr.jhelp.viewmodel.implementation

import fr.jhelp.common.GridInformation
import fr.jhelp.injector.injected
import fr.jhelp.model.shared.MotusService
import fr.jhelp.viewmodel.shared.GridModel
import kotlinx.coroutines.flow.StateFlow

internal class GridImplementation : GridModel
{
    private val motusService: MotusService by injected<MotusService>()

    override val gridInformation: StateFlow<GridInformation> = this.motusService.gridInformation
}