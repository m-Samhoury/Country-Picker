package com.moustafa.countrypicker.repository.network

/**
 * @author moustafasamhoury
 * created on Thursday, 19 Sep, 2019
 */


sealed class StateMonitor<out T> {
    object Init : StateMonitor<Nothing>()
    object Loading : StateMonitor<Nothing>()
    data class Loaded<T>(val result: T) : StateMonitor<T>()
    data class Failed(val failed: Throwable, val action: (() -> Any)? = null) : StateMonitor<Nothing>()
}
