package com.brotandos.dictionary

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.View
import com.brotandos.koatlib.times
import org.jetbrains.anko.dip

fun Context.cardBg(color: Int, radius: Int = dip(2)) = GradientDrawable() * {
    shape = GradientDrawable.RECTANGLE
    cornerRadius = radius.toFloat()
    setColor(color)
}

fun View.layerCard(color: Int = G.Color.CARD) = LayerDrawable(arrayOf(
        context.cardBg(G.Color.CARD_SHADOW_1, dip(4)),
        context.cardBg(G.Color.CARD_SHADOW_2, dip(3)),
        context.cardBg(color)
)) * {
    setLayerInset(0, 0, 0, 0, 0)
    setLayerInset(1, 0, 0, 0, dip(1))
    setLayerInset(2, dip(1), dip(1), dip(1), dip(2))
}

val bgLayerCard: View.() -> Unit = {
    background = layerCard()
}