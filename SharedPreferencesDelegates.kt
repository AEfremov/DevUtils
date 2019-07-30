package com.

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun SharedPreferences.delegateString(default: String = "", key: String? = null) =
        createDelegate(default, key, SharedPreferences::getString, SharedPreferences.Editor::putString)

fun SharedPreferences.delegateStringNullable(key: String? = null) =
        createDelegateNullable(key, SharedPreferences::getString, SharedPreferences.Editor::putString)

fun SharedPreferences.delegateInt(default: Int = 0, key: String? = null) =
        createDelegate(default, key, SharedPreferences::getInt, SharedPreferences.Editor::putInt)

fun SharedPreferences.delegateLong(default: Long = 0L, key: String? = null) =
        createDelegate(default, key, SharedPreferences::getLong, SharedPreferences.Editor::putLong)

fun SharedPreferences.delegateBoolean(default: Boolean = false, key: String? = null) =
        createDelegate(default, key, SharedPreferences::getBoolean, SharedPreferences.Editor::putBoolean)

private inline fun <T> SharedPreferences.createDelegate(
        defaultValue: T,
        key: String?,
        crossinline getter: SharedPreferences.(String, T) -> T,
        crossinline setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor
): ReadWriteProperty<Any, T> {
    return object : ReadWriteProperty<Any, T> {

        override fun getValue(thisRef: Any, property: KProperty<*>) =
                getter(key ?: property.name, defaultValue)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) =
                edit().setter(key ?: property.name, value).apply()
    }
}

private inline fun <T> SharedPreferences.createDelegateNullable(
        key: String?,
        crossinline getter: SharedPreferences.(String, T?) -> T?,
        crossinline setter: SharedPreferences.Editor.(String, T?) -> SharedPreferences.Editor
): ReadWriteProperty<Any, T?> {
    return object : ReadWriteProperty<Any, T?> {

        override fun getValue(thisRef: Any, property: KProperty<*>) =
                getter(key ?: property.name, null)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) =
                edit().setter(key ?: property.name, value).apply()
    }
}
