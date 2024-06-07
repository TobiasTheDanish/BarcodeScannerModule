package dk.skancode.barcodescannermodule

enum class ScanMode(val value: String) {
    PADDING("0"),
    DIRECT("direct"),
    SIMULATE("simulate"),
    API("api")
}