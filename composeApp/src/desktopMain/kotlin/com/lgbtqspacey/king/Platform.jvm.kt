package com.lgbtqspacey.king

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

class JVMPlatform : Platform() {
    override val name: String = "JVM"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun copyToClipboard(value: String) {
    Toolkit.getDefaultToolkit()
        .systemClipboard.setContents(
            StringSelection(value),
            null
        )
}
