package dk.skancode.barcodescannermodule.newlandimpl

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import dk.skancode.barcodescannermodule.Enabler
import dk.skancode.barcodescannermodule.IExpoModule
import dk.skancode.barcodescannermodule.IScannerModule
import dk.skancode.barcodescannermodule.ScanMode
import dk.skancode.barcodescannermodule.ScannerConfigKey

class NewlandScannerModule(private val context: Context, private val module: IExpoModule) : IScannerModule {
    private val dataReceiver = BarcodeDataReceiver(module)
    private fun getPreferences(): SharedPreferences {
        return context.getSharedPreferences(context.packageName + ".barcode", Context.MODE_PRIVATE)
    }

    override fun getScannerState(): String {
        return getPreferences().getString("scannerState", "off") ?: "off"
    }

    override fun setScannerState(enabler: Enabler) {
        getPreferences().edit().putString("scannerState", enabler.value).apply()
        configureScanner(ScannerConfigKey.SCAN_POWER, enabler.ordinal)
        module.sendEvent("onScannerStateChange", bundleOf(
            "state" to enabler.value,
        ))
    }

    override fun registerReceiver() {
        val filter = IntentFilter("nlscan.action.SCANNER_RESULT")
        val flag = ContextCompat.RECEIVER_EXPORTED

        ContextCompat.registerReceiver(context, dataReceiver, filter, flag)
    }

    override fun unregisterReceiver() {
        context.unregisterReceiver(dataReceiver)
    }

    override fun setAutoEnter(value: Enabler) {
        configureScanner(ScannerConfigKey.AUTO_ENTER, value.ordinal)
    }

    override fun setNotificationSound(value: Enabler) {
        configureScanner(ScannerConfigKey.NOTIFICATION_SOUND, value.ordinal)
    }

    override fun setNotificationVibration(value: Enabler) {
        configureScanner(ScannerConfigKey.NOTIFICATION_VIBRATION, value.ordinal)
    }

    override fun setScanMode(value: ScanMode) {
        configureScanner(ScannerConfigKey.SCAN_MODE, value.ordinal)
    }

    private fun configureScanner(key: ScannerConfigKey, value: Int) {
        val intent = Intent("ACTION_BAR_SCANCFG")

        intent.putExtra(key.value, value)
        context.sendBroadcast(intent)
    }
}