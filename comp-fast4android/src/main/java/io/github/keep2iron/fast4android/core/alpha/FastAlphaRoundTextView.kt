package io.github.keep2iron.fast4android.core.alpha

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.StyleableRes
import androidx.appcompat.widget.AppCompatTextView
import io.github.keep2iron.fast4android.R

open class FastAlphaRoundTextView @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

  private val fastAlphaViewHelper = FastAlphaViewHelper(this)

  private val fastDrawableViewHelper =
    FastDrawableRoundViewHelper()

  init {
    val defValue = TypedValue.applyDimension(
      TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics
    ).toInt()

    background = fastDrawableViewHelper.resolveAttribute(context, attrs, defStyleAttr)?.build()

    val typedArray =
      context.obtainStyledAttributes(attrs, R.styleable.FastAlphaRoundTextView, defStyleAttr, 0)
    setCompoundTextView(
      compoundDrawables[0],
      typedArray,
      defValue,
      R.styleable.FastAlphaRoundTextView_fast_drawableLeftWidth,
      R.styleable.FastAlphaRoundTextView_fast_drawableLeftHeight
    )
    setCompoundTextView(
      compoundDrawables[1],
      typedArray,
      defValue,
      R.styleable.FastAlphaRoundTextView_fast_drawableTopWidth,
      R.styleable.FastAlphaRoundTextView_fast_drawableTopHeight
    )
    setCompoundTextView(
      compoundDrawables[2],
      typedArray,
      defValue,
      R.styleable.FastAlphaRoundTextView_fast_drawableRightWidth,
      R.styleable.FastAlphaRoundTextView_fast_drawableRightHeight
    )
    setCompoundTextView(
      compoundDrawables[3],
      typedArray,
      defValue,
      R.styleable.FastAlphaRoundTextView_fast_drawableBottomWidth,
      R.styleable.FastAlphaRoundTextView_fast_drawableBottomHeight
    )

    setCompoundTint(
      compoundDrawables[0],
      typedArray,
      R.styleable.FastAlphaRoundTextView_fast_drawableLeftTint
    )

    setCompoundTint(
      compoundDrawables[1],
      typedArray,
      R.styleable.FastAlphaRoundTextView_fast_drawableTopTint
    )

    setCompoundTint(
      compoundDrawables[2],
      typedArray,
      R.styleable.FastAlphaRoundTextView_fast_drawableRightTint
    )

    setCompoundTint(
      compoundDrawables[3],
      typedArray,
      R.styleable.FastAlphaRoundTextView_fast_drawableBottomTint
    )

    setCompoundDrawables(
      compoundDrawables[0],
      compoundDrawables[1],
      compoundDrawables[2],
      compoundDrawables[3]
    )
    typedArray.recycle()
  }

  private fun setCompoundTint(
    compoundDrawable: Drawable?,
    typedArray: TypedArray,
    @StyleableRes colorAttr: Int
  ) {
    if (compoundDrawable != null && typedArray.hasValue(colorAttr)) {
      compoundDrawable.setColorFilter(
        typedArray.getColor(colorAttr, Color.TRANSPARENT), PorterDuff.Mode.SRC_ATOP
      )
    }
  }

  private fun setCompoundTextView(
    compoundDrawable: Drawable?,
    typedArray: TypedArray,
    defValue: Int,
    @StyleableRes widthAttr: Int,
    @StyleableRes heightAttr: Int
  ) {
    compoundDrawable?.setBounds(
      0, 0, typedArray.getDimensionPixelSize(
      widthAttr,
      defValue
    )
      ,
      typedArray.getDimensionPixelSize(
        heightAttr,
        defValue
      )
    )
  }

  override fun setPressed(pressed: Boolean) {
    super.setPressed(pressed)
    fastAlphaViewHelper.onPressedChanged(this, pressed)
  }

  override fun setEnabled(enabled: Boolean) {
    super.setEnabled(enabled)
    fastAlphaViewHelper.onEnabledChanged(this, enabled)
  }

  /**
   * 设置是否要在 press 时改变透明度
   *
   * @param changeAlphaWhenPress 是否要在 press 时改变透明度
   */
  fun setChangeAlphaWhenPress(changeAlphaWhenPress: Boolean) {
    fastAlphaViewHelper.setChangeAlphaWhenPress(changeAlphaWhenPress)
  }

  /**
   * 设置是否要在 disabled 时改变透明度
   *
   * @param changeAlphaWhenDisable 是否要在 disabled 时改变透明度
   */
  fun setChangeAlphaWhenDisable(changeAlphaWhenDisable: Boolean) {
    fastAlphaViewHelper.setChangeAlphaWhenDisable(changeAlphaWhenDisable)
  }
}