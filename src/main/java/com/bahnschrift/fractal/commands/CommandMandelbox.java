package com.bahnschrift.fractal.commands;


import com.bahnschrift.fractal.FractalHelper;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;


public class CommandMandelbox {
    private static final int MAX_ITER = 100;

    public static int buildFractal(CommandContext<CommandSourceStack> commandContext) throws CommandSyntaxException {
        Player player = (Player) commandContext.getSource().getEntity();
        assert player != null;
        Level world = player.level;

        BlockPos pos1 = BlockPosArgument.getLoadedBlockPos(commandContext, "loc1");
        BlockPos pos2 = BlockPosArgument.getLoadedBlockPos(commandContext, "loc2");
        int startX = Math.min(pos1.getX(), pos2.getX());
        int endX = Math.max(pos1.getX(), pos2.getX());
        int startY = Math.min(pos1.getY(), pos2.getY());
        int endY = Math.max(pos1.getY(), pos2.getY());
        int startZ = Math.min(pos1.getZ(), pos2.getZ());
        int endZ = Math.max(pos1.getZ(), pos2.getZ());

        ArrayList<Block> palette = FractalHelper.getPalette(commandContext);
        if (palette == null)
            return 0;

        float scale = -3;
        try {
            scale = FloatArgumentType.getFloat(commandContext, "scale");
        } catch (IllegalArgumentException ignored) {
        }

        double size = 2;
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    double fracX = ((x - startX) / (double) (endX - startX)) * size * 2 - size;
                    double fracY = ((y - startY) / (double) (endY - startY)) * size * 2 - size;
                    double fracZ = ((z - startZ) / (double) (endZ - startZ)) * size * 2 - size;

                    int c = mandelbox(fracX, fracY, fracZ, scale);
                    if (c != MAX_ITER) {
                        BlockPos pos = new BlockPos(x, y, z);
                        Block block = palette.get(c % palette.size());
                        world.setBlockAndUpdate(pos, block.defaultBlockState());
                    }
                }
            }
        }
        return 1;
    }

    private static int mandelbox(double x, double y, double z, float scale) {
        double cx = x;
        double cy = y;
        double cz = z;
        int c = 0;
        double magnitude = 0;
        while (c < MAX_ITER && magnitude < 4096) {
            if (x > 1) {
                x = 2 - x;
            } else if (x < -1) {
                x = -2 - x;
            } if (y > 1) {
                y = 2 - y;
            } else if (y < -1) {
                y = -2 - y;
            } if (z > 1) {
                z = 2 - z;
            } else if (z < -1) {
                z = -2 - z;
            }

            magnitude = Math.sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2));
            if (magnitude < 0.5) {
                x *= 4;
                y *= 4;
                z *= 4;
            } else if (magnitude < 1) {
                x = x / pow(magnitude, 2);
                y = y / pow(magnitude, 2);
                z = z / pow(magnitude, 2);
            }

            x = scale * x + cx;
            y = scale * y + cy;
            z = scale * z + cz;
            magnitude = Math.sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2));
            c++;
        }

        return c;
    }

    private static double pow(double a, double b) {
        return Math.pow(a, b);
    }
}
