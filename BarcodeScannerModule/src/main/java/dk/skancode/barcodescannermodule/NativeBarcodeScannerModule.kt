package dk.skancode.barcodescannermodule

import android.content.Context
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition

const val BARCODE_DATA_RECEIVED_EVENT_NAME = "onBarcodeDataReceived"
const val SCANNER_STATE_CHANGED = "onScannerStateChange"
class NativeBarcodeScannerModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("NativeBarcodeScanner")

//region lifecycle hooks
    OnCreate {
      scannerModule.setScannerState(Enabler.OFF)
    }

    OnDestroy {
      scannerModule.setScannerState(Enabler.ON)
    }

    OnStartObserving {
      scannerModule.registerReceiver()
    }

    OnStopObserving {
      scannerModule.unregisterReceiver()
    }

    OnActivityEntersBackground {
      scannerModule.setScannerState(Enabler.ON)
    }

    OnActivityEntersForeground {
      scannerModule.setScannerState(Enabler.OFF)
    }
    //endregion

    Property("autoEnter")
      .set<Enabler> { scannerModule.setAutoEnter(it) }

    Property("notificationSound")
      .set<Enabler> { scannerModule.setNotificationSound(it) }

    Property("notificationVibration")
      .set<Enabler> { scannerModule.setNotificationVibration(it) }

    Property("scanMode")
      .set<ScanMode> { scannerModule.setScanMode(it) }

    Events(BARCODE_DATA_RECEIVED_EVENT_NAME, SCANNER_STATE_CHANGED)

    Function("scannerAvailable") {
      scannerModule.isScannerAvailable()
    }

    Function("modelNumber") {
      android.os.Build.MODEL
    }

    Function("brand") {
      android.os.Build.BRAND
    }

    Function("getScanner") {
      scannerModule.getScannerState()
    }

    Function("setScanner") { value: Enabler ->
      scannerModule.setScannerState(value)
    }
  }
  private val context: Context
    get() = requireNotNull(appContext.reactContext)

  private val module = IExpoModule {eventName, payload -> }
  private val scannerModule: IScannerModule = ScannerModuleFactory.fromBrand(android.os.Build.BRAND, context, module)
}
