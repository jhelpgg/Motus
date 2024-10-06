package fr.jhelp.tools

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlin.coroutines.CoroutineContext

/**
 * Dispatcher that do action immediately, useful for testing and dummy
 */
class ImmediateDispatcher : CoroutineDispatcher()
{
    override fun dispatch(context: CoroutineContext, block: Runnable)
    {
        block.run()
    }
}
