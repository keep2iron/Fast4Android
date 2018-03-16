package io.github.keep2iron.android.util

import android.os.Looper

import io.reactivex.Observer
import io.reactivex.disposables.Disposables

class Preconditions private constructor() {

    init {
        throw AssertionError("No instances.")
    }

    companion object {
        fun <T> checkNotNull(value: T?, message: String): T {
            if (value == null) {
                throw NullPointerException(message)
            }
            return value
        }

        fun checkMainThread(observer: Observer<*>): Boolean {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                observer.onSubscribe(Disposables.empty())
                observer.onError(IllegalStateException(
                        "Expected to be called on the main thread but was " + Thread.currentThread().name))
                return false
            }
            return true
        }
    }
}