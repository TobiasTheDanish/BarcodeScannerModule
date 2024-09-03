package dk.skancode.barcodescannermodule

import android.os.Bundle

fun interface IEventHandler {
    fun onDataReceived(eventName: String, payload: Bundle)
}