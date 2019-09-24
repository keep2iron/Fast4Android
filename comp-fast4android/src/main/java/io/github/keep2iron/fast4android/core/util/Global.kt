package io.github.keep2iron.fast4android.core.util

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.collection.ArrayMap
import io.github.keep2iron.fast4android.core.ComponentProvider
import io.github.keep2iron.fast4android.core.Fast4Android
import java.io.Serializable

/**
 * 用于扩展 Application 的扩展方法类
 */
val COMPONENT_SERVICE = ArrayMap<Class<*>, Any>()

/**
 * 注册组件服务
 */
inline fun <reified T> registerComponent(componentProvider: ComponentProvider<T>) {
  COMPONENT_SERVICE[T::class.java] =
    componentProvider.onCreateComponent(Fast4Android.CONTEXT.applicationContext as Application)
}

/**
 * 获取组件
 */
inline fun <reified T> getComponentService(): T {
  return COMPONENT_SERVICE[T::class.java] as T
}

fun dp2px(dp: Int): Int {
  val density = Fast4Android.CONTEXT.resources.displayMetrics.density
  return (dp * density).toInt()
}

fun px2dp(px: Int): Int {
  val density = Fast4Android.CONTEXT.resources.displayMetrics.density
  return (px / density).toInt()
}

fun sp(sp: Int): Float {
  val scaledDensity = Fast4Android.CONTEXT.resources.displayMetrics.scaledDensity
  return sp * scaledDensity
}

fun startActivity(clazz: Class<out Activity>, vararg args: Pair<String, Any>) {
  val intent = Intent(Fast4Android.CONTEXT, clazz)
  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
  for (arg in args) {
    val value = arg.second
    when (value) {
      is String -> {
        intent.putExtra(arg.first, value)
      }
      is Int -> {
        intent.putExtra(arg.first, value)
      }
      is Double -> {
        intent.putExtra(arg.first, value)
      }
      is Float -> {
        intent.putExtra(arg.first, value)
      }
      is Byte -> {
        intent.putExtra(arg.first, value)
      }
      is Boolean -> {
        intent.putExtra(arg.first, value)
      }
      is Bundle -> {
        intent.putExtra(arg.first, value)
      }
      is Long -> {
        intent.putExtra(arg.first, value)
      }
      is Char -> {
        intent.putExtra(arg.first, value)
      }
      is Short -> {
        intent.putExtra(arg.first, value)
      }
      is Parcelable -> {
        intent.putExtra(arg.first, value)
      }
      is IntArray -> {
        intent.putExtra(arg.first, value)
      }
      is ByteArray -> {
        intent.putExtra(arg.first, value)
      }
      is FloatArray -> {
        intent.putExtra(arg.first, value)
      }
      is DoubleArray -> {
        intent.putExtra(arg.first, value)
      }
      is BooleanArray -> {
        intent.putExtra(arg.first, value)
      }
      is Serializable -> {
        intent.putExtra(arg.first, value)
      }
      is LongArray -> {
        intent.putExtra(arg.first, value)
      }
      is CharSequence -> {
        intent.putExtra(arg.first, value)
      }
    }
  }
  Fast4Android.CONTEXT.startActivity(intent)
}