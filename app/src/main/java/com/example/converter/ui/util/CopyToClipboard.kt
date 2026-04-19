package com.example.converter.ui.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context


fun copyToClipboard(
    context: Context,
    textToCopy: String
) {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    val clipData = ClipData.newPlainText("", textToCopy)
    clipboardManager.setPrimaryClip(clipData)
}