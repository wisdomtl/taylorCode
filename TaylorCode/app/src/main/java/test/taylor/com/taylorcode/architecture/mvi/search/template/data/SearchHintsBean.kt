package com.bilibili.studio.search.template.data

import androidx.annotation.Keep
import com.alibaba.fastjson.annotation.JSONField

@Keep
data class SearchHintsBean(
    @JSONField(name = "titles")
    val hints:List<String>
)