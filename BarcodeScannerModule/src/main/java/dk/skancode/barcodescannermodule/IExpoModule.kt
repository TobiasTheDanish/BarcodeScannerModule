package dk.skancode.barcodescannermodule

import android.os.Bundle


fun interface IExpoModule {
    fun sendEvent(eventName: String, payload: Bundle)
}