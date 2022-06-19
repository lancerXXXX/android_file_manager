package com.example.test1.utils

import com.example.test1.R

object ThemeUtil {
    // 1.icon 2.name 3.bg
    fun getFolderTheme(): NTuple3<Int, Int, Int> {
        return R.color.blue1 then R.color.blue1 then R.color.common_bg
    }
    // 1.icon 2.name 3.bg
    fun getFolderSelectedTheme(): NTuple3<Int, Int, Int> {
        return R.color.common_bg then R.color.common_bg then R.color.blue1
    }

    // 1.icon 2.name
    fun getFileTheme(): NTuple2<Int, Int> {
        return R.color.green1 then R.color.text_common
    }

}