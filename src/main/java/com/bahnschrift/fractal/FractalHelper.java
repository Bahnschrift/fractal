package com.bahnschrift.fractal;


import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;


public class FractalHelper {
    private static final ArrayList<Block> DEFAULT_PALETTE = new ArrayList<>() {{
        add(Blocks.BLACK_STAINED_GLASS);
        add(Blocks.GRAY_STAINED_GLASS);
        add(Blocks.PURPLE_STAINED_GLASS);
        add(Blocks.RED_STAINED_GLASS);
        add(Blocks.ORANGE_STAINED_GLASS);
        add(Blocks.LIME_STAINED_GLASS);
        add(Blocks.GREEN_STAINED_GLASS);
        add(Blocks.BLUE_STAINED_GLASS);
        add(Blocks.LIGHT_BLUE_STAINED_GLASS);
        add(Blocks.LIGHT_GRAY_STAINED_GLASS);
        add(Blocks.AIR);
    }};

    public static ArrayList<Block> getPalette(CommandContext<CommandSourceStack> commandContext) {
        HashMap<String, ArrayList<String>> palettes = Config.get();
        ArrayList<Block> palette = DEFAULT_PALETTE;
        try {
            String paletteArg = StringArgumentType.getString(commandContext, "palette");
            if (!palettes.containsKey(paletteArg)) {
                commandContext.getSource().sendFailure(
                        new TranslatableComponent(
                                "fractal.chat.invalid_palette",
                                paletteArg
                        ).withStyle(ChatFormatting.RED));
                return null;
            }

            palette = new ArrayList<>();
            for (String blockName : palettes.get(paletteArg)) {
                ResourceLocation loc = new ResourceLocation(blockName);

                if (!ForgeRegistries.BLOCKS.containsKey(loc)) {
                    commandContext.getSource().sendFailure(
                            new TranslatableComponent(
                                    "fractal.chat.invalid_block",
                                    blockName
                            ).withStyle(ChatFormatting.RED));
                    return null;
                }

                palette.add(ForgeRegistries.BLOCKS.getValue(loc));
            }
        } catch (IllegalArgumentException ignored) {
        }
        return palette;
    }
}
