package com.brotandos.dictionary

data class Dictionary(var title: String, val items: MutableList<DictionaryItem>)
data class DictionaryItem(val key: String, val value: String)