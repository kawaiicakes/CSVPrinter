package io.github.kawaiicakes.csvprinter.fabric;

import io.github.kawaiicakes.csvprinter.CSVPrinter;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public final class CSVPrinterFabric implements ModInitializer, ClientModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        CSVPrinter.init();

        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(
                (ignored0, ignored1, ignored2) -> CSVPrinter.writeCSV()
        );
    }

    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    }
}
