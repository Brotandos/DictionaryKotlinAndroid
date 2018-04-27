package com.brotandos.dictionary

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.brotandos.koatlib.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.sdk25.coroutines.onClick

class DictionaryFragment: KoatlFragment() {
    private val dictionary: Dictionary
    private val icCollapsed = R.drawable.ic_collapsed
    private val icExpanded = R.drawable.ic_expanded
    private lateinit var vList: RecyclerView

    init {
        val list = mutableListOf<DictionaryItem>()
        for (i in 0 until 20) list += DictionaryItem("key-$i", "value-$i")

        dictionary = Dictionary("First dictionary", list)
    }

    override fun markup() = KUI {
        vVertical {
            vFrame(bg(R.color.colorPrimary)) {
                vLabel(dictionary.title, 10f.sp, text(Color.WHITE)).lp(submissive, g5)
            }.lparams(matchParent, 50.dp)
            vList = vList(linear).forEachOf(dictionary.items) { item, _ ->
                vVertical(bgLayerCard, mw) {
                    lateinit var vValue: TextView

                    vLinear(content456) {
                        vImage(icCollapsed) {
                            onClick {
                                if (this@vImage.resourceId == icCollapsed) {
                                    imageResource = icExpanded
                                    vValue.visible()
                                } else {
                                    imageResource = icCollapsed
                                    vValue.hidden()
                                }
                            }
                        }.lp(row, 5f())
                        vLabel(item.key, line).lp(row, 1f())
                    }.lp(dominant)
                    vValue = vLabel(item.value, text(G.Color.PRIMARY), hidden).lp(dominant, 1f(), m(2.dp))
                }.llp(row, m(2.dp))
            }.lp(row, m(5.dp))
        }
    }
}