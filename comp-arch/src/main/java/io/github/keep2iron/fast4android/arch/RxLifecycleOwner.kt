package io.github.keep2iron.fast4android.arch

import io.github.keep2iron.fast4android.arch.rx.LifecycleEvent
import io.github.keep2iron.fast4android.arch.rx.RxLifecycle
import io.github.keep2iron.fast4android.arch.util.ioAsyncScheduler
import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.BehaviorSubject

interface RxLifecycleOwner {

  val publishSubject: BehaviorSubject<LifecycleEvent>

  fun <T> flowableIoAsyncAndBindLifecycle(): FlowableTransformer<T, T> {
    return FlowableTransformer { upstream ->
      upstream.ioAsyncScheduler()
        .compose(bindFlowableLifeCycle())
    }
  }

  fun <T> observableIoAsyncAndBindLifecycle(): ObservableTransformer<T, T> {
    return ObservableTransformer { upstream ->
      upstream.ioAsyncScheduler()
        .compose(bindObservableLifeCycle())
    }
  }

  fun <T> bindObservableLifeCycle(): ObservableTransformer<T, T> {
    return RxLifecycle.bindUntilEvent(publishSubject, LifecycleEvent.DESTROY)
  }

  fun <T> bindFlowableLifeCycle(): FlowableTransformer<T, T> {
    return RxLifecycle.bindUntilEvent(publishSubject, LifecycleEvent.DESTROY)
  }

}