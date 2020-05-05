package com.zakshaker.core

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

inline fun CoroutineScope.launchCatching(
    noinline block: suspend CoroutineScope.() -> Unit,
    crossinline exceptionHandler: (Throwable) -> Unit
): Job = launch(
    CoroutineExceptionHandler { _, throwable -> exceptionHandler(throwable) },
    block = block
)