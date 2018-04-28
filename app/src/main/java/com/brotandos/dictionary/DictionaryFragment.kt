package com.brotandos.dictionary

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.brotandos.koatlib.*
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

    // Все веселье начинается здесь
    override fun markup() = KUI {
        // Многие view частицы начинаются с маркера 'v'. Ниже LinearLayout с вертикальной ориентацией
        vVertical {
            // FrameLayout, bg(colorRes: Int) - лямбда-функция, которая изменяет background частицы
            vFrame(bg(R.color.colorPrimary)) {
                // TextView, здесь функция-расширение Float.sp изменяет размер текста
                // функция lp - сокращенное от layoutParams
                // submissive означает width = wrapContent и height = wrapContent
                // g5 - gravity = Gravity.CENTER.
                // Аттрибут гравитации в библиотеке описан по принципу кнопок телефона
                // 1 - слева-наверху, 2 - центр-вверх, 5 - середина, 456 - середина вертикали и т.д.
                vLabel(dictionary.title, 10f.sp, text(Color.WHITE)).lp(submissive, g5)
            }.lparams(matchParent, 50.dp) // Надеюсь здесь Int.dp интуитивно понятно
            // Ниже верстается RecyclerView. Моя самая любимая часть библиотеки
            // Позволяет отказаться от создания адаптеров
            vList = vList(linear).forEachOf(dictionary.items) {
                item, i -> // item - текущий объект, i - позиция

                // bgLayerCard - описана внутри Styles.kt.
                // Это моя попытка внедрить CSS концепцию стилизации view-частиц
                // mw - сокращенное от width = matchParent и height = wrapContent
                vVertical(bgLayerCard, mw) {
                    lateinit var vValue: TextView

                    // content456 то же, что и gravity = Gravity.CENTER_VERTICAL
                    // Концепция телефонных кнопок удобна тем,
                    // что благодаря ей легче представлять расположение дочерних view-частиц
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
                        }.lp(row, 5f()) // row - то же, что и width = matchParent и height = wrapContent
                        // функция Float.invoke() у дочерних частиц LinearLayout'а означает weight
                        //
                        vLabel(item.key).lp(row, 1f())
                    }.lp(dominant) // dominant - width = matchParent, height = matchParent

                    // hidden - visibility = View.GONE
                    vValue = vLabel(item.value, text(G.Color.PRIMARY), hidden).lp(dominant, 1f(), m(2.dp))
                }.llp(row, m(2.dp)) // функция m(number: Int) то же, что и margin
            }.lp(row, m(5.dp))
        }
    }
}