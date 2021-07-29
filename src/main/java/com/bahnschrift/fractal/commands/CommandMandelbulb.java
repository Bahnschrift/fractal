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


public class CommandMandelbulb {
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

        double A, B, C, D;
        A = B = C = D = 0;
        try {
            A = FloatArgumentType.getFloat(commandContext, "A");
            B = FloatArgumentType.getFloat(commandContext, "B");
            C = FloatArgumentType.getFloat(commandContext, "C");
            D = FloatArgumentType.getFloat(commandContext, "D");
        } catch (IllegalArgumentException ignored) {
        }

        double size = 1.5;
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    double fracX = ((x - startX) / (double) (endX - startX)) * size * 2 - size;
                    double fracY = ((y - startY) / (double) (endY - startY)) * size * 2 - size;
                    double fracZ = ((z - startZ) / (double) (endZ - startZ)) * size * 2 - size;

                    int c = mandelbulb(fracX, fracY, fracZ, A, B, C, D);
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

    private static int mandelbulb(double x, double y, double z, double A, double B, double C, double D) {
        double cx = x;
        double cy = y;
        double cz = z;
        int c = 0;
        double magnitude = 0;
        while (c < MAX_ITER && magnitude < 4096) {
            double ax = x;
            double ay = y;
            double az = z;
            // Quadratic
            // x = Math.pow(ax, 2) - Math.pow(ay, 2) - Math.pow(az, 2) + cx;
            // y = 2 * ax * az + cy;
            // z = 2 * ax * ay + cz;
            // Cubic
            // x = Math.pow(ax, 3) - 3 * ax * (Math.pow(ay, 2) - Math.pow(az, 2)) + cx;
            // y = -Math.pow(ay, 3) + 3 * ay * Math.pow(ax, 2) - ay * Math.pow(az, 2) + cy;
            // z = Math.pow(az, 3) + 3 * az * Math.pow(ax, 2) + az * Math.pow(ay, 2) + cz;
            // Quintic
            x = (Math.pow(ax, 5) -
                    10 * pow(ax, 3) * (pow(ay, 2) + A * ay * az + pow(az, 2)) +
                    5 * ax * (pow(ay, 4) + B * pow(ay, 3) * az + C * pow(ay, 2) * pow(az, 2) + B * ay * pow(az, 3) + pow(az, 4)) +
                    D * pow(ax, 2) * ay * az * (ay + az) +
                    cx);
            y = Math.pow(ay, 5) -
                    10 * pow(ay, 3) * (pow(az, 2) + A * ax * az + pow(ax, 2)) +
                    5 * ay * (pow(az, 4) + B * pow(az, 3) * ax + C * pow(az, 2) * pow(ax, 2) + B * az * pow(ax, 3) + pow(ax, 4)) +
                    D * pow(ay, 2) * az * ax * (az + ax) +
                    cy;
            z = Math.pow(az, 5) -
                    10 * pow(az, 3) * (pow(ax, 2) + A * ax * ay + pow(ay, 2)) +
                    5 * az * (pow(ax, 4) + B * pow(ax, 3) * ay + C * pow(ax, 2) * pow(ay, 2) + B * ax * pow(ay, 3) + pow(ay, 4)) +
                    D * pow(az, 2) * ax * ay * (ax + ay) +
                    cz;

            magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
            c++;
        }
        return c;
    }

    private static double pow(double a, double b) {
        return Math.pow(a, b);
    }
}
