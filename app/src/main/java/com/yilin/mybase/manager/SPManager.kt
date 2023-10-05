package com.yilin.mybase.manager

import android.content.Context
import android.content.SharedPreferences
import com.yilin.mybase.MyApp

class SPManager private constructor() {

    companion object {
        val instance: SPManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { SPManager() }
    }

    private val sp: SharedPreferences

    fun putBoolean(key: String, isAccess: Boolean): SPManager {
        sp.edit().putBoolean(key, isAccess).apply()
        return this
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return sp.getBoolean(key, defValue)
    }


    init {
        val filename = "my_base_sp"
        sp = MyApp.instance.getSharedPreferences(filename, Context.MODE_PRIVATE)
    }
}