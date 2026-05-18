package org.kotools.types.qr.code

import qrcode.QRCode
import qrcode.QRCodeBuilder
import java.io.FileOutputStream

private const val URL: String = "https://github.com/kotools/types"
private const val BUILD_DIRECTORY: String = "subprojects/qr-code/build"

public fun main() {
    qrCodeOfCircles()
    qrCodeOfRoundedSquares()
    qrCodeOfSquares()
}

private fun qrCodeOfCircles(): Unit = QRCode.ofCircles()
    .buildAndWriteIn(file = "qrcode-circles.png")

private fun qrCodeOfRoundedSquares(): Unit = QRCode.ofRoundedSquares()
    .buildAndWriteIn(file = "qrcode-rounded-squares.png")

private fun qrCodeOfSquares(): Unit = QRCode.ofSquares()
    .buildAndWriteIn(file = "qrcode-squares.png")

private fun QRCodeBuilder.buildAndWriteIn(file: String) {
    val content: ByteArray = this.build(URL)
        .renderToBytes()
    FileOutputStream("$BUILD_DIRECTORY/$file").use {
        it.write(content)
    }
}
