package io.github.keep2iron.fast4android.app.ui.grouplistview

import android.os.Bundle
import com.github.anzewei.parallaxbacklayout.ParallaxBack
import io.github.keep2iron.fast4android.app.R
import io.github.keep2iron.fast4android.app.databinding.GroupListViewActivityBinding
import io.github.keep2iron.fast4android.arch.AbstractActivity
import io.github.keep2iron.fast4android.base.util.FastDisplayHelper.dp2px
import io.github.keep2iron.fast4android.base.util.FastStatusBarHelper
import io.github.keep2iron.fast4android.core.widget.FastLoadingView

@ParallaxBack
class GroupListViewActivity : AbstractActivity<GroupListViewActivityBinding>() {

  override fun resId(): Int = R.layout.group_list_view_activity

  override fun initVariables(savedInstanceState: Bundle?) {
    FastStatusBarHelper.translucent(this)

    dataBinding.topBarLayout.setup {
      addLeftBackImageButton().setOnClickListener {
        finish()
      }
    }

    dataBinding.groupListView.apply {
      setInset(left = dp2px(context, 10), right = dp2px(context, 10))
      addChevronItem {
        title = "测试一个title1"
        setLeftGroupListImageResource(R.mipmap.example_image1)
        setOnClickListener {

        }
        setLeftImageSize(dp2px(context, 30), dp2px(context, 30))
      }
      addItem {
        title = "测试一个title2"
        setLeftImageSize(dp2px(context, 30), dp2px(context, 30))
        setLeftGroupListImageResource(R.mipmap.ic_launcher)
        setOnClickListener {

        }
      }
      addSwitchItem {
        title = "测试一个title3"
        setLeftImageSize(dp2px(context, 30), dp2px(context, 30))
        setLeftGroupListImageResource(R.mipmap.ic_launcher)
        setOnClickListener {

        }
      }
      addCustomItem {
        title = "测试一个title3"
        setLeftImageSize(dp2px(context, 30), dp2px(context, 30))
        setLeftGroupListImageResource(R.mipmap.ic_launcher)
        addAccessoryCustomView(FastLoadingView(context))
        setOnClickListener {

        }
      }
    }
  }

}