package io.github.keep2iron.fast4android.base.widget

import android.graphics.Rect

interface IWindowInsetLayout {
  fun applySystemWindowInsets19(insets: Rect): Boolean

  fun applySystemWindowInsets21(insets: Any): Boolean
}