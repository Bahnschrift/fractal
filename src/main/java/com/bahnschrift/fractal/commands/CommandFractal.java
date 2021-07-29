package com.bahnschrift.fractal.commands;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;


public class CommandFractal {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // TODO: Rewrite the fuck out of this... Like seriously... 9 closing brackets?!?!
        LiteralArgumentBuilder<CommandSourceStack> commandFractal = Commands.literal("fractal")
                .requires((commandSource) -> commandSource.hasPermission(4))
                .then(Commands.literal("mandelbox")
                        .then(Commands.argument("loc1", BlockPosArgument.blockPos())
                                .then(Commands.argument("loc2", BlockPosArgument.blockPos())
                                        .executes(CommandMandelbox::buildFractal)
                                        .then(Commands.argument("palette", StringArgumentType.string())
                                                .executes(CommandMandelbox::buildFractal)
                                                .then(Commands.argument("scale", FloatArgumentType.floatArg())
                                                        .executes(CommandMandelbox::buildFractal))))))
                .then(Commands.literal("mandelbulb")
                        .then(Commands.argument("loc1", BlockPosArgument.blockPos())
                                .then(Commands.argument("loc2", BlockPosArgument.blockPos())
                                        .executes(CommandMandelbulb::buildFractal)
                                        .then(Commands.argument("palette", StringArgumentType.string())
                                                .executes(CommandMandelbulb::buildFractal)
                                                .then(Commands.argument("A", FloatArgumentType.floatArg())
                                                        .then(Commands.argument("B", FloatArgumentType.floatArg())
                                                                .then(Commands.argument("C", FloatArgumentType.floatArg())
                                                                        .then(Commands.argument("D", FloatArgumentType.floatArg())
                                                                                .executes(CommandMandelbulb::buildFractal)))))))))
                .then(Commands.literal("palettes")
                        .executes(CommandPalettes::sendPalettes));
        dispatcher.register(commandFractal);
    }
}
