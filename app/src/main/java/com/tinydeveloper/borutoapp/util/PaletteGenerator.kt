package com.tinydeveloper.borutoapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult

object PaletteGenerator {
    suspend fun convertImageUrlToBitmap(
        imageUrl: String,
        context: Context
    ): Bitmap?{
        val request = ImageRequest.Builder(context = context)
            .data(imageUrl)
            .allowHardware(false)
            .build()
        val imageResult = ImageLoader(context = context).execute(request = request)
        return if(imageResult is SuccessResult)
            (imageResult.drawable as BitmapDrawable).bitmap
        else
            null
    }

    fun extractColorFromBitmap(bitmap: Bitmap): Map<String, String> {
        return mapOf(
            VIBRANT_COLOR to parseColorSwatch(
                Palette.from(bitmap).generate().vibrantSwatch
            ),
            DARK_VIBRANT_COLOR to parseColorSwatch(
                Palette.from(bitmap).generate().darkVibrantSwatch
            ),
            ON_DARK_VIBRANT_COLOR to parseBodyColor(
                Palette.from(bitmap).generate().darkVibrantSwatch?.bodyTextColor
            )
        )
    }

    private fun parseColorSwatch(color: Palette.Swatch?): String{
        return if (color != null) "#${Integer.toHexString(color.rgb)}" else "#000000"
    }

    private fun parseBodyColor(color: Int?): String {
        return if (color != null) "#${Integer.toHexString(color)}" else "#FFFFFF"
    }
}