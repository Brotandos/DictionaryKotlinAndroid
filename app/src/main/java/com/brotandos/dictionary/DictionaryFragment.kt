package com.brotandos.dictionary

import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.brotandos.koatlib.*
import com.brotandos.kuantumlib.ListKuantum
import com.brotandos.kuantumlib.TextKuantum
import com.brotandos.kuantumlib.of
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.scrollView

class DictionaryFragment: KoatlFragment(), View.OnClickListener {
    private val dictionary: Dictionary
    private val icCollapsed = R.drawable.ic_collapsed
    private val icExpanded = R.drawable.ic_expanded

    init {
        val list = mutableListOf<DictionaryItem>()
        for (i in 0 until 7) list += DictionaryItem(TextKuantum("key-$i"), TextKuantum("value-$i"))

        dictionary = Dictionary(TextKuantum("First dictionary"), ListKuantum(list))
    }

    override fun markup() = KUI {
        scrollView { vVertical {
            vFrame(bg(R.color.colorPrimary)) {
                // Слово "of" - infix функция.
                // Благодаря ней view-частица привязывается к тексту
                vLabel(10f.sp, text(Color.WHITE)).lp(submissive, g5) of dictionary.title
            }.lparams(matchParent, 50.dp)
            // Функция String.invoke в нижнем примере прописывает placeholder-текст для EditText
            // Здесь мы привязываем титул словаря. Попробуйте туда что-нибудь написать
            vText(line, "Set dictionary name"()).lp(row) of dictionary.title
            // Привязываем recyclerView к списку и описываем верстку для каждого элемента
            vList(linear).lp(row, m(5.dp)) of dictionary.items.vForEach { item, _ ->
                vVertical(bgLayerCard, mw) {
                    vLinear(content456) {
                        // Кнопка свернуть/развернуть
                        vImage(icCollapsed, tag(item), this@DictionaryFragment()).lp(row, 5f())
                        // Привязываем само слово
                        vText(line).lp(row, 1f()) of item.key
                        // Кнопка удаления слова
                        vImage(R.drawable.ic_remove, tag(item), this@DictionaryFragment()).lp(row, 5f())
                    }.lp(dominant, 1f())
                    // Привязываем значение слова
                    vText(text(G.Color.PRIMARY), hidden).lp(dominant, 1f(), m(2.dp)) of item.value
                }.llp(row, m(2.dp))
            }
            vBtn("Add item to dictionary", this@DictionaryFragment())
        }}
    }

    override fun onClick(v: View?) {
        // Нижняя функция самая сложная для понимания.
        // Мы внутри списка ищем подходящий элемент словаря...
        // ... и возвращаем view-частицу для описания слова
        // Оно нужно, чтобы развернуть элемент словаря...
        // ... и показать view, отвечающую за описание слова
        fun ListKuantum<DictionaryItem>.getItemValueView(itemAsViewTag: Any)
        = find { it == itemAsViewTag }!!.value.firstView

        if (v is Button) {
            // Добавляем в словарь новый элемент
            dictionary.items.add(DictionaryItem(TextKuantum(), TextKuantum()))
        }
        else when ((v as ImageView).resourceId) {
            // Разворачиваем слово
            icCollapsed -> {
                v.imageResource = icExpanded
                dictionary.items.getItemValueView(v.tag).visible()
            }
            // Сворачиваем слово
            icExpanded  -> {
                v.imageResource = icCollapsed
                dictionary.items.getItemValueView(v.tag).hidden()
            }
            // Удаляем элемент
            R.drawable.ic_remove -> dictionary.items.removeFirstWhere { it == v.tag }
        }
    }
}