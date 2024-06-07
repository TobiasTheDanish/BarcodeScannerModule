package dk.skancode.barcodescannermodule

interface IScannerModule {
    fun isScannerAvailable(): Boolean {
        val brand = android.os.Build.BRAND.lowercase()
        return brand == "newland" || brand == "unitech"
    }

    fun getScannerState(): String
    fun setScannerState(enabler: Enabler)
    fun registerReceiver()
    fun unregisterReceiver()

    fun setAutoEnter(value: Enabler)
    fun setNotificationSound(value: Enabler)
    fun setNotificationVibration(value: Enabler)
    fun setScanMode(value: ScanMode)
}