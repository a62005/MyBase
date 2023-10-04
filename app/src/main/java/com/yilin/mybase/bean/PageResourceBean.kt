package com.yilin.mybase.bean

import androidx.fragment.app.Fragment

data class PageResourceBean(
    val getFragment: (() -> Fragment)?,
    val titleRes: Int,
    val iconRes: Int,
    val disIconRes: Int
) {
    constructor(fragment: (() -> Fragment), titleRes: Int) : this(fragment, titleRes, 0, 0)
    constructor(fragment: (() -> Fragment)) : this(fragment, 0, 0, 0)
}