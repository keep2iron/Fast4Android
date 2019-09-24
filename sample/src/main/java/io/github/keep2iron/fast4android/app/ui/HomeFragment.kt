package io.github.keep2iron.fast4android.app.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import io.github.keep2iron.base.util.layoutInflate
import io.github.keep2iron.fast4android.app.R
import io.github.keep2iron.fast4android.app.ui.dialog.DialogComponentsActivity
import io.github.keep2iron.fast4android.app.ui.grouplistview.GroupListViewActivity
import io.github.keep2iron.fast4android.app.ui.roundbutton.RoundComponentsActivity
import io.github.keep2iron.fast4android.app.ui.tabsegment.TabSegmentListActivity
import io.github.keep2iron.fast4android.arch.AbstractFragment
import io.github.keep2iron.fast4android.arch.FindViewById
import io.github.keep2iron.fast4android.core.util.startActivity

class HomeFragment : AbstractFragment<ViewDataBinding>() {
  private val recyclerView: RecyclerView by FindViewById(R.id.recyclerView)
//  private val titleBar: FastTopBarLayout by FindViewById(R.id.titleBar)
  override fun resId(): Int = R.layout.home_fragment
  private val items = listOf(
    Description("RoundComponents", R.mipmap.icon_grid_button, RoundComponentsActivity::class.java),
    Description("TabSegment", R.mipmap.icon_grid_tab_segment, TabSegmentListActivity::class.java),
    Description(
      "GroupListView",
      R.mipmap.icon_grid_group_list_view,
      GroupListViewActivity::class.java
    ),
    Description("Dialog", R.mipmap.icon_grid_dialog, DialogComponentsActivity::class.java)
  )

  override fun initVariables(savedInstanceState: Bundle?) {
    recyclerView.adapter = MainAdapter(items, requireFragmentManager())

    recyclerView.layoutManager = GridLayoutManager(requireContext().applicationContext, 3)
  }

  class MainAdapter(private val data: List<Description>, val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val context = parent.context
      return MyViewHolder(context.layoutInflate(R.layout.home_item_main, parent)).apply {
        val viewHolder = this
        itemView.setOnClickListener {
          startActivity(data[viewHolder.layoutPosition].clazz)
        }
      }
    }

    override fun getItemCount(): Int = data.size
    override
    fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val item = data[position]
      holder.itemView.findViewById<ImageView>(R.id.item_icon).setImageResource(item.icon)
      holder.itemView.findViewById<TextView>(R.id.item_name).text = item.title
    }
  }

  class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
  class Description(
    val title: String,
    val icon: Int,
    val clazz: Class<out Activity>
  )
}