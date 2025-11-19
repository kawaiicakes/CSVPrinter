package io.github.kawaiicakes.csvprinter.forge;

import io.github.kawaiicakes.csvprinter.CSVPrinter;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(CSVPrinter.MOD_ID)
public final class CSVPrinterForge {
    public CSVPrinterForge() {
        // Run our common setup.
        CSVPrinter.init();
        MinecraftForge.EVENT_BUS.addListener(CSVPrinterForge::onDataLoad);
    }

    public static void onDataLoad(AddReloadListenerEvent event) {
        CSVPrinter.writeCSV();
    }
}
