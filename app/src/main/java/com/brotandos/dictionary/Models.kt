package com.brotandos.dictionary

import com.brotandos.kuantumlib.ListKuantum
import com.brotandos.kuantumlib.TextKuantum

data class Dictionary(var title: TextKuantum, val items: ListKuantum<DictionaryItem>)
data class DictionaryItem(val key: TextKuantum, val value: TextKuantum)