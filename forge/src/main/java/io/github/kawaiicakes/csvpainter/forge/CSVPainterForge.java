package io.github.kawaiicakes.csvpainter.forge;

import net.minecraftforge.fml.common.Mod;

import io.github.kawaiicakes.csvpainter.CSVPainter;

@Mod(CSVPainter.MOD_ID)
public final class CSVPainterForge {
    public CSVPainterForge() {
        // Run our common setup.
        CSVPainter.init();
    }
}
