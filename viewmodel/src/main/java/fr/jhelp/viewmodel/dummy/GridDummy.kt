package fr.jhelp.viewmodel.dummy

import fr.jhelp.common.GridInformation
import fr.jhelp.viewmodel.shared.GridModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GridDummy(override val gridInformation: StateFlow<GridInformation>) : GridModel
{
    constructor(gridInformation: GridInformation) : this(MutableStateFlow(gridInformation))
}