package com.brotandos.dictionary

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.brotandos.koatlib.*
import com.brotandos.kuantumlib.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.scrollView

class DictionaryFragment: KoatlFragment(), View.OnClickListener {
    private val icCollapsed = R.drawable.ic_collapsed
    private val icExpanded = R.drawable.ic_expanded
    private val qDictionary: Dictionary
    // Текст поиска
    private val qFilter = TextKuantum()
    // Флаг поиска по ключу
    private val qFilterByKey = BooleanKuantum(true)
    // Флаг поиска по значению
    private val qFilterByValue = BooleanKuantum(true)
    // Сохраним view-частицу, нам она пригодится
    private lateinit var vList: RecyclerView

    init {
        val list = mutableListOf<DictionaryItem>()
        for (i in 0 until 7) list += DictionaryItem(TextKuantum("key-$i"), TextKuantum("value-$i"))

        qDictionary = Dictionary(TextKuantum("First dictionary"), ListKuantum(list))
    }

    override fun markup() = KUI {
        scrollView { vVertical {
            vFrame(bg(R.color.colorPrimary)) {
                vLabel(10f.sp, text(Color.WHITE)).lp(submissive, g5) of qDictionary.qTitle
            }.lparams(matchParent, 50.dp)
            // Строка поиска
            vText(line, "Search"(), icLeft(R.drawable.ic_search), pIcon(3.dp)).lp(row) of qFilter {
                // Здесь прописывается реакция на изменение текста поиска
                if (it.isEmpty()) {
                    qDictionary.qItems.clearViewFilter()
                    vList.scrollToPosition(0)
                }
                else filterDictionary(it)
            }
            vLinear(p(2.dp)) {
                // Флаг поиска по ключу
                vCheck("by key").lp(row, 1f()) of qFilterByKey {
                    vList.requestLayout()
                    if (it.not() && qFilterByValue.value.not()) {
                        qFilterByKey becomes true
                        qFilterByValue becomes true
                    }
                    filterDictionary(qFilter.value)
                }
                // Флаг поиска по значению
                vCheck("by value").lp(row, 1f()) of qFilterByValue {
                    vList.requestLayout()
                    if (it.not() && qFilterByKey.value.not()) {
                        qFilterByKey becomes true
                        qFilterByValue becomes true
                    }
                    filterDictionary(qFilter.value)
                }
            }.lp(submissive, g258)
            vList = vList(linear).lp(row, m(5.dp)) of qDictionary.qItems.vForEach { item, _ ->
                vVertical(bgLayerCard, mw) {
                    vLinear(content456) {
                        vImage(icCollapsed, tag(item), this@DictionaryFragment()).lp(row, 5f())
                        vText(line).lp(row, 1f()) of item.qKey
                        vImage(R.drawable.ic_remove, tag(item), this@DictionaryFragment()).lp(row, 5f())
                    }.lp(dominant, 1f())
                    vText(text(G.Color.PRIMARY), hidden).lp(dominant, 1f(), m(2.dp)) of item.qValue
                }.llp(row, m(2.dp))
            }
            vBtn("Add item to dictionary", this@DictionaryFragment()).lp(row)
        }}
    }

    override fun onClick(v: View?) {
        fun ListKuantum<DictionaryItem>.getItemValueView(itemAsViewTag: Any)
        = find { it == itemAsViewTag }!!.qValue.firstView

        if (v is Button) {
            qDictionary.qItems.add(DictionaryItem(TextKuantum(), TextKuantum()))
            vList.scrollToPosition(qDictionary.qItems.lastIndex)
        }
        else when ((v as ImageView).resourceId) {
            icCollapsed -> {
                v.imageResource = icExpanded
                qDictionary.qItems.getItemValueView(v.tag).visible()
            }
            icExpanded  -> {
                v.imageResource = icCollapsed
                qDictionary.qItems.getItemValueView(v.tag).hidden()
            }
            R.drawable.ic_remove -> qDictionary.qItems.removeFirstWhere { it == v.tag }
        }
    }

    // Фильтруем словарь
    private fun filterDictionary(filterWord: String) {
        qDictionary.qItems.filterView {
            if (qFilterByKey.value && qFilterByValue.value.not()) it.qKey.value.contains(filterWord)
            else if (qFilterByValue.value && qFilterByKey.value.not()) it.qValue.value.contains(filterWord)
            else it.qKey.value.contains(filterWord) || it.qValue.value.contains(filterWord)
        }
    }
}