package io.github.keep2iron.android.core

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.keep2iron.android.rx.LifecycleEvent
import io.github.keep2iron.android.rx.RxLifecycle
import io.github.keep2iron.android.utilities.RxTransUtil
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.BehaviorSubject
import java.lang.IllegalArgumentException

/**
 *
 * @author keep2iron <a href="http://keep2iron.github.io">Contract me.</a>
 * @version 1.0
 * @since 2018/05/19 11:08
 */
abstract class AbstractFragment<DB : ViewDataBinding> : Fragment() {
    private var isInit: Boolean = false
    private var isOnAttach: Boolean = false

    private var baseActivity: AbstractActivity<*>? = null

    private var subject = BehaviorSubject.create<LifecycleEvent>()
    protected lateinit var dataBinding: DB
    private lateinit var contentView: View

    protected lateinit var applicationContext: Context

    var waitingForUser: Boolean = false

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        isOnAttach = true
        applicationContext = context!!.applicationContext
    }

    /**
     * 映射布局方法
     *
     * @return 被映射的布局id
     */
    @get:LayoutRes
    protected abstract val resId: Int

    /**
     * 初始化方法
     *
     * @param container 被映射的container对象
     */
    abstract fun initVariables(container: View, savedInstanceState: Bundle?)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(resId, container, false)
        try {
            val createBinding = DataBindingUtil.bind<DB>(view)
            if (createBinding != null) {
                dataBinding = createBinding
            }
        } catch (e: IllegalArgumentException) {
        }
        contentView = view

        contentView.isClickable = true
        contentView.setBackgroundColor(resources.getColor(android.R.color.white))

        if (context is AbstractActivity<*>) {
            baseActivity = context as AbstractActivity<*>?
            baseActivity?.setStatusColorFromAnnotation()
        }
        return contentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVariables(contentView, savedInstanceState)
        if (userVisibleHint && !isInit) {
            isInit = true
            lazyLoad(contentView)
        }
    }

    fun setStatusColor(@ColorRes color: Int) {
        baseActivity?.setStatusColor(color)
    }

    fun setStatusTextColor(isDark: Boolean) {
        baseActivity?.setStateTextColor(isDark)
    }

    /**
     * 绑定让订阅进行绑定生命周期
     */
    fun <T> bindObservableLifeCycle(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.compose(RxTransUtil.rxObservableScheduler())
                    .compose(RxLifecycle.bindUntilEvent(this.subject, LifecycleEvent.DESTROY))
        }
    }

    override fun onStart() {
        super.onStart()
        subject.onNext(LifecycleEvent.START)
    }

    override fun onResume() {
        super.onResume()
        subject.onNext(LifecycleEvent.RESUME)
    }

    override fun onPause() {
        super.onPause()
        subject.onNext(LifecycleEvent.PAUSE)
    }

    override fun onStop() {
        super.onStop()
        subject.onNext(LifecycleEvent.STOP)
    }

    override fun onDestroy() {
        subject.onNext(LifecycleEvent.DESTROY)
        super.onDestroy()
    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     *
     * @param container
     */
    open fun lazyLoad(container: View?) {
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (isVisibleToUser) {
            if (isOnAttach && !isInit) {
                lazyLoad(view)
                isInit = true
            }
        }

        if (isVisibleToUser) {
            if (parentFragment != null && !parentFragment!!.userVisibleHint) {
                waitingForUser = true
                return
            }
        }

        if (isOnAttach && host != null) {
            val fragments = childFragmentManager.fragments
            fragments?.forEach {
                if (it is AbstractFragment<*>) {
                    if (userVisibleHint) {
                        if (it.userVisibleHint || it.waitingForUser) {
                            it.userVisibleHint = true
                            it.waitingForUser = false
                        }
                    } else {
                        if (it.userVisibleHint) {
                            it.userVisibleHint = false
                            it.waitingForUser = true
                        }
                    }
                }
            }
        }
    }

    override fun onDetach() {
        isOnAttach = false
        super.onDetach()
    }

    fun <T : View> findViewById(viewId: Int): T {
        return contentView.findViewById(viewId) as T
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        if (isAdded) {
            val fragments = childFragmentManager.fragments
            fragments?.forEach {
                if (!it.isHidden && it.userVisibleHint) {
                    it.onHiddenChanged(hidden)
                }
            }
        }
    }
}