package fr.jhelp.tools

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

val Dispatchers.Immediate: CoroutineDispatcher get() = ImmediateDispatcher()
