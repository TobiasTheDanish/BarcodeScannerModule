package dk.skancode.barcodescannermodule.unitechimpl

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import dk.skancode.barcodescannermodule.IExpoModule

private const val DATA_INTENT = "unitech.scanservice.data"
private const val DATA_TYPE_INTENT = "unitech.scanservice.datatype"

class BarcodeDataReceiver(private val module: IExpoModule) : BroadcastReceiver() {
    private var data: String? = null
    private var dataType: Int? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == DATA_INTENT) {
            val bundle = intent.extras
            if (bundle != null) {
                data = bundle.getString("text")
                if (data == null) {
                    module.sendEvent("onBarcodeDataReceived", bundleOf(
                        "barcode1" to null,
                        "barcode2" to null,
                        "barcodeType" to null,
                        "ok" to false
                    ))
                } else if (dataType != null) {
                    module.sendEvent("onBarcodeDataReceived", bundleOf(
                        "barcode1" to data,
                        "barcode2" to null,
                        "barcodeType" to dataType,
                        "ok" to true
                    ))
                    data = null
                    dataType = null
                }
            } else {
                module.sendEvent("onBarcodeDataReceived", bundleOf(
                    "barcode1" to null,
                    "barcode2" to null,
                    "barcodeType" to null,
                    "ok" to false
                ))
                data = null
                dataType = null
            }
        }

        if (intent?.action == DATA_TYPE_INTENT) {
            val bundle = intent.extras
            if (bundle != null) {
                dataType = bundle.getInt("text", -1)
                if (dataType == -1) {
                    dataType = null
                    module.sendEvent("onBarcodeDataReceived", bundleOf(
                        "barcode1" to null,
                        "barcode2" to null,
                        "barcodeType" to null,
                        "ok" to false
                    ))
                } else if (data != null) {
                    module.sendEvent("onBarcodeDataReceived", bundleOf(
                        "barcode1" to data,
                        "barcode2" to null,
                        "barcodeType" to dataType,
                        "ok" to true
                    ))
                    data = null
                    dataType = null
                }
            } else {
                module.sendEvent("onBarcodeDataReceived", bundleOf(
                    "barcode1" to null,
                    "barcode2" to null,
                    "barcodeType" to null,
                    "ok" to false
                ))
                data = null
                dataType = null
            }
        }
    }
}