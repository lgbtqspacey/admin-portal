package com.lgbtqspacey.king

class AndroidPlatform() : Platform() {
    override val name: String = "android"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun copyToClipboard(value: String) {
    // todo
}
