package dk.skancode.barcodescannermodule

enum class ScannerConfigKey(val value: String) {
    AUTO_ENTER("EXTRA_SCAN_AUTOENT"),
    SCAN_POWER("EXTRA_SCAN_POWER"),
    SCAN_MODE("EXTRA_SCAN_MODE"),
    NOTIFICATION_SOUND("EXTRA_SCAN_NOTY_SND"),
    NOTIFICATION_VIBRATION("EXTRA_SCAN_NOTY_VIB"),
}