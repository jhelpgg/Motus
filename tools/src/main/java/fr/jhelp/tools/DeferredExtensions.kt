package fr.jhelp.tools

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Permits to do something after a deferred receive its result
 */
fun <T:Any> Deferred<T>.onResult(callback:(T)->Unit)
{
    this.invokeOnCompletion {
        if(this.isCompleted)
        {
            CoroutineScope(Dispatchers.Default).launch {
                callback(this@onResult.await())
            }
        }
    }
}