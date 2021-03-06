package io.github.keep2iron.fast4android.core.alpha

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable.RECTANGLE
import android.util.AttributeSet
import io.github.keep2iron.fast4android.R
import io.github.keep2iron.peach.DrawableCreator

class FastDrawableRoundViewHelper {

  fun resolveAttribute(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int
  ): DrawableCreator? {

    val typeValue =
      context.obtainStyledAttributes(
        attrs,
        R.styleable.FastDrawableRoundViewHelper,
        defStyleAttr,
        0
      )

    val haveRadius =
      typeValue.hasValue(R.styleable.FastDrawableRoundViewHelper_fast_drawable_radius) ||
        typeValue.hasValue(R.styleable.FastDrawableRoundViewHelper_fast_drawable_left_top_radius) ||
        typeValue.hasValue(R.styleable.FastDrawableRoundViewHelper_fast_drawable_left_bottom_radius) ||
        typeValue.hasValue(R.styleable.FastDrawableRoundViewHelper_fast_drawable_right_top_radius) ||
        typeValue.hasValue(R.styleable.FastDrawableRoundViewHelper_fast_drawable_right_bottom_radius)

    val radius = typeValue.getDimensionPixelSize(
      R.styleable.FastDrawableRoundViewHelper_fast_drawable_radius,
      0
    )

    val strokeWidth = typeValue.getDimensionPixelSize(
      R.styleable.FastDrawableRoundViewHelper_fast_drawable_stroke_width,
      0
    )

    val strokeColor = typeValue.getColor(
      R.styleable.FastDrawableRoundViewHelper_fast_drawable_stroke_color,
      Color.TRANSPARENT
    )

    var leftTopRadius = radius
    var leftBottomRadius = radius
    var rightTopRadius = radius
    var rightBottomRadius = radius

    leftTopRadius = typeValue.getDimensionPixelSize(
      R.styleable.FastDrawableRoundViewHelper_fast_drawable_left_top_radius,
      leftTopRadius
    )
    leftBottomRadius = typeValue.getDimensionPixelSize(
      R.styleable.FastDrawableRoundViewHelper_fast_drawable_left_bottom_radius,
      leftBottomRadius
    )
    rightTopRadius = typeValue.getDimensionPixelSize(
      R.styleable.FastDrawableRoundViewHelper_fast_drawable_right_top_radius,
      rightTopRadius
    )
    rightBottomRadius = typeValue.getDimensionPixelSize(
      R.styleable.FastDrawableRoundViewHelper_fast_drawable_right_bottom_radius,
      rightBottomRadius
    )

    val startColor = typeValue.getColor(
      R.styleable.FastDrawableRoundViewHelper_fast_drawable_color_start,
      Color.TRANSPARENT
    )
    val centerColor = typeValue.getColor(
      R.styleable.FastDrawableRoundViewHelper_fast_drawable_color_center,
      Color.TRANSPARENT
    )
    val endColor = typeValue.getColor(
      R.styleable.FastDrawableRoundViewHelper_fast_drawable_color_end,
      Color.TRANSPARENT
    )
    val colorAngle = typeValue.getInt(
      R.styleable.FastDrawableRoundViewHelper_fast_drawable_color_angle,
      -1
    )

    var radiusAdjust = typeValue.getBoolean(
      R.styleable.FastDrawableRoundViewHelper_fast_drawable_radius_adjust_bounds,
      false
    )
    if (haveRadius) {
      //如果有圆角则覆盖radiusAdjust属性
      radiusAdjust = false
    }


    val hasColorStart = typeValue.hasValue(R.styleable.FastDrawableRoundViewHelper_fast_drawable_color_start)
    val hasColorCenter = typeValue.hasValue(R.styleable.FastDrawableRoundViewHelper_fast_drawable_color_center)
    val hasColorEnd = typeValue.hasValue(R.styleable.FastDrawableRoundViewHelper_fast_drawable_color_end)


    if (strokeWidth == 0 && !hasColorStart && !hasColorCenter && !hasColorEnd) {
      //如果没有颜色 没有 border大小
      return null
    } else if (strokeColor == Color.TRANSPARENT && !hasColorStart && !hasColorCenter && !hasColorEnd) {
      //如果没有颜色 border颜色为透明
      return null
    }

    val drawableCreator = DrawableCreator().apply {
      gradient()
      linearGradient()

      if (hasColorStart) {
        startColor(startColor)
      }
      if (hasColorCenter) {
        centerColor(centerColor)
      }
      if (hasColorEnd) {
        endColor(endColor)
      }

      //如果没有设置颜色则为透明
      if (!hasColorStart && !hasColorCenter && !hasColorEnd) {
        startColor(Color.TRANSPARENT)
        endColor(Color.TRANSPARENT)
      }

      shape(RECTANGLE)

      if (radiusAdjust) {
        rounded()
      } else {
        cornerRadii(leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius)
      }

      if (strokeWidth > 0) {
        strokeWidth(strokeWidth)
        strokeColor(strokeColor)
      }

      if (colorAngle != -1) {
        angle(colorAngle)
      }
    }

    typeValue.recycle()
    return drawableCreator
  }

}