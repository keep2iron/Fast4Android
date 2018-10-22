package io.github.keep2iron.android.comp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import io.github.keep2iron.android.R

/**
 * @author keep2iron [Contract me.](http://keep2iron.github.io)
 * @version 1.0
 * @since 2018/01/24 10:48
 *
 * 默认实现的LoadMoreAdapter
 */
class LoadMoreAdapter constructor(context: Context,
                                  recyclerView: RecyclerView,
                                  listener: (adapter: AbstractLoadMoreAdapter) -> Unit
) : AbstractLoadMoreAdapter(context.applicationContext, recyclerView, listener) {

    override fun getLayoutId(): Int {
        return R.layout.item_load_more
    }

}