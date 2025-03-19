//package com.lgbtqspacey.king.helpers
//
//import androidx.compose.ui.graphics.ImageBitmap
//import androidx.compose.ui.graphics.asSkiaBitmap
//import androidx.compose.ui.graphics.toComposeImageBitmap
//import org.jetbrains.skia.EncodedImageFormat
//import org.jetbrains.skia.Image
//import kotlin.io.encoding.Base64
//import kotlin.io.encoding.ExperimentalEncodingApi
//
//class ImageParser {
//    fun base64ToBitmap(bitmapString: String): ImageBitmap {
//        val stringToByteArray = byteArrayOf(bitmapString.toByte())
//
//        return Image.makeFromEncoded(stringToByteArray).toComposeImageBitmap()
//    }
//
//    @OptIn(ExperimentalEncodingApi::class)
//    fun bitmapToBase64(image: ImageBitmap): String {
//        val encodedBytes = Image.makeFromBitmap(image.asSkiaBitmap())
//            .encodeToData(EncodedImageFormat.PNG, 100)?.bytes
//
//        return Base64.Default.encode(encodedBytes!!)
//    }
//}
