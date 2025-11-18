package io.github.kawaiicakes.csvprinter;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import org.slf4j.Logger;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class CSVPrinter {
    public static Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "csvprinter";

    public static void init() {} // this is really only used to ensure this class is loaded at runtime

    /**
     * Call whenever server resources are finished reloading.
     */
    public static void writeCSV() {
        try {
            PrintWriter writer = new PrintWriter("modded.csv", StandardCharsets.UTF_8);

            writer.println(
                    "name,discriminator,properties,opacity,receivesLight,insubstantial,resource,tileEntity,tileEntityId,treeRelated,vegetation,blockLight,natural,watery,colour"
            );

            for (Block block : BuiltInRegistries.BLOCK) {
                final ResourceLocation name = BuiltInRegistries.BLOCK.getKey(block);

                if (name.getNamespace().equals("minecraft")) continue;

                String entry = name + "," +
                        "," + // discriminator
                        properties(block) + // properties
                        "," + // opacity
                        "," + // receivesLight
                        "," + // insubstantial
                        "," + // resource
                        (block instanceof BaseEntityBlock) + "," +
                        "," + // tileEntityId
                        "," + // treeRelated
                        "," + // vegetation
                        block.defaultBlockState().getLightEmission() + "," +
                        "," + // natural
                        "," + // watery
                        Integer.toHexString(block.defaultMapColor().calculateRGBColor(MapColor.Brightness.HIGH));

                writer.println(entry);
            }

            writer.close();
        } catch (Exception lol) {
            LOGGER.error("CSVPrinter was unable to run!", lol);
        }
    }

    private static String properties(Block block) {
        StringBuilder properties = new StringBuilder("\"");

        final List<Property<?>> ordered = new ArrayList<>(block.getStateDefinition().getProperties());

        for (Property<?> property : ordered) {
            StringBuilder propertyValues = new StringBuilder("\"");
            propertyValues.append(property.getName()).append(":");

            if (property instanceof EnumProperty<?> enumProperty) {
                Collection<?> possible = enumProperty.getPossibleValues();
                propertyValues = new StringBuilder("e[");

                for (Object object : possible) {
                    if (!(object instanceof StringRepresentable str)) continue;
                    propertyValues.append(str.getSerializedName()).append(";");
                }

                propertyValues.append("]");
            } else if (property instanceof BooleanProperty) {
                propertyValues = new StringBuilder("b");
            } else if (property instanceof IntegerProperty integerProperty) {
                Collection<Integer> possible = integerProperty.getPossibleValues();
                propertyValues = new StringBuilder("i[" + Collections.min(possible) + "-" + Collections.max(possible) + "]");
            }

            if (ordered.indexOf(property) != ordered.size() - 1) propertyValues.append(",");

            properties.append(propertyValues);
        }

        properties.append("\",");

        return properties.toString();
    }
}
