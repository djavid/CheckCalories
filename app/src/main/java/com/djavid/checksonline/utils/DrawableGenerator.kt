package com.djavid.checksonline.utils

import android.graphics.drawable.Drawable
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator

class DrawableGenerator {
	
	fun getRoundDrawable(s: String): Drawable {
		val generator = ColorGenerator.MATERIAL
		val color = generator.getColor(s)
		
		return TextDrawable
				.builder()
				.beginConfig()
				.bold()
				.endConfig()
				.buildRound(s, color)
	}
	
}