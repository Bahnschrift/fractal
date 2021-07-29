package com.bahnschrift.fractal;


import com.bahnschrift.fractal.commands.CommandFractal;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class RegisterCommandEvent {
    @SubscribeEvent
    public static void onRegisterCommandEvent(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> commandDispatcher = event.getDispatcher();
        CommandFractal.register(commandDispatcher);
    }
}
