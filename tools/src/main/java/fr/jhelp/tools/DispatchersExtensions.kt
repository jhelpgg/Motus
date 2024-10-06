package fr.jhelp.tools

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Dispatcher that do action immediately, useful for testing and dummy
 */
val Dispatchers.Immediate: CoroutineDispatcher get() = ImmediateDispatcher()
