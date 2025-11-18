package io.github.kawaiicakes.csvprinter.forge;

import io.github.kawaiicakes.csvprinter.CSVPrinter;
import net.minecraftforge.fml.common.Mod;

@Mod(CSVPrinter.MOD_ID)
public final class CSVPrinterForge {
    public CSVPrinterForge() {
        // Run our common setup.
        CSVPrinter.init();
    }
}
