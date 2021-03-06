//package com.anou.prototype.yoga.manager
//
//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.LifecycleObserver
//import androidx.lifecycle.LifecycleOwner
//import androidx.lifecycle.OnLifecycleEvent
//import kotlinx.coroutines.CoroutineStart
//import kotlinx.coroutines.Deferred
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.newFixedThreadPoolContext
//
//
//internal class CoroutineLifecycleListener(private val deferred: Deferred<*>) : LifecycleObserver {
//  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
//  fun cancelCoroutine() {
//    if (!deferred.isCancelled) {
//      deferred.cancel()
//    }
//  }
//}
//
//// CoroutineContext running on background threads.
//internal val Background = newFixedThreadPoolContext(Runtime.getRuntime().availableProcessors() * 2, "Loader")
//
///**
// * Creates a lazily started coroutine that runs <code>loader()</code>.
// * The coroutine is automatically cancelled using the CoroutineLifecycleListener.
// */
//fun <T> LifecycleOwner.load(loader: suspend () -> T): Deferred<T> {
//  val deferred = async(context = Background, start = CoroutineStart.LAZY) {
//    loader()
//  }
//
//  lifecycle.addObserver(CoroutineLifecycleListener(deferred))
//  return deferred
//}
//
///**
// * Extension function on <code>Deferred<T><code> that creates a launches a coroutine which
// * will call <code>await()</code> and pass the returned value to <code>block()</code>.
// */
//infix fun <T> Deferred<T>.then(block: suspend (T) -> Unit): Job {
//  return launch(context = UI) {
//    try {
//      block(this@then.await())
//    } catch (e: Exception) {
//      // Just log the exception to confirm when we get cancelled (Expect JobCancellationException)
////      loge(e) { "Exception in then()!" }
//      throw e
//    }
//  }
//}
//
