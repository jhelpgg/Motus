package fr.jhelp.viewmodel

import fr.jhelp.injector.inject
import fr.jhelp.model.models
import fr.jhelp.viewmodel.implementation.GameSettingImplementation
import fr.jhelp.viewmodel.implementation.GridImplementation
import fr.jhelp.viewmodel.implementation.WordPropositionImplementation
import fr.jhelp.viewmodel.shared.GameSettingModel
import fr.jhelp.viewmodel.shared.GridModel
import fr.jhelp.viewmodel.shared.WordPropositionModel

fun viewModels()
{
    models()
    inject<GameSettingModel>(GameSettingImplementation())
    inject<GridModel>(GridImplementation())
    inject<WordPropositionModel>(WordPropositionImplementation())
}