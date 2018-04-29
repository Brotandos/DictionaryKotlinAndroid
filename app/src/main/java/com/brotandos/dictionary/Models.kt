package com.brotandos.dictionary

import com.brotandos.kuantumlib.ListKuantum
import com.brotandos.kuantumlib.TextKuantum

data class Dictionary(var qTitle: TextKuantum, val qItems: ListKuantum<DictionaryItem>)
data class DictionaryItem(val qKey: TextKuantum, val qValue: TextKuantum)