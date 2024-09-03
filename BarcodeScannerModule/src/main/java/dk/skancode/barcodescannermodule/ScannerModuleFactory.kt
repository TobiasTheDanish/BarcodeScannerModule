package dk.skancode.barcodescannermodule

import android.content.Context
import dk.skancode.barcodescannermodule.newlandimpl.NewlandScannerModule
import dk.skancode.barcodescannermodule.unitechimpl.UnitechScannerModule

class ScannerModuleFactory {
    companion object {
        fun fromBrand(brand: String, context: Context, module: IEventHandler): IScannerModule {
            return when (brand.lowercase()) {
                "newland" -> NewlandScannerModule(context, module)
                "unitech" -> UnitechScannerModule(context, module)
                else -> DummyScannerModule()
            }
        }
    }
}