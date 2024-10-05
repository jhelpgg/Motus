package fr.jhelp.viewmodel.shared

import fr.jhelp.common.GridInformation
import kotlinx.coroutines.flow.StateFlow

interface GridModel
{
    val gridInformation : StateFlow<GridInformation>
}