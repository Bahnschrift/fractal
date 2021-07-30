package com.bahnschrift.fractal.commands;


import com.bahnschrift.fractal.Config;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;


public class CommandPalettes {
    public static int sendPalettes(CommandContext<CommandSourceStack> commandContext) {
        HashMap<String, ArrayList<String>> palettes = Config.get();
        ArrayList<Component> components = new ArrayList<>();
        Player player = (Player) commandContext.getSource().getEntity();
        assert player != null;

        components.add(new TranslatableComponent("fractal.chat.palettes_heading")
                .withStyle(ChatFormatting.AQUA)
                .withStyle(ChatFormatting.UNDERLINE)
                .withStyle(ChatFormatting.BOLD)
                .withStyle(Style.EMPTY.withHoverEvent(new HoverEvent(
                        HoverEvent.Action.SHOW_TEXT,
                        new TextComponent("./" + Config.CONFIG_FILE).withStyle(ChatFormatting.ITALIC))))
                .withStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, Config.CONFIG_FILE))));

        int n = 0;
        for (String paletteName : palettes.keySet()) {
            components.add(new TextComponent(paletteName)
                    .withStyle(ChatFormatting.DARK_GREEN)
                    .withStyle(ChatFormatting.BOLD));

            ArrayList<String> palette = palettes.get(paletteName);
            int i = 0;
            for (String blockName : palette) {
                i++;
                components.add(
                        new TranslatableComponent(
                                "fractal.chat.block_message",
                                new TextComponent(String.format("%d.", i)).withStyle(ChatFormatting.BOLD),
                                new TextComponent(blockName)
                        )
                );
            }
            n++;
            if (n != palettes.size()) {
                components.add(new TextComponent(""));
            }
         }
        for (Component component : components) {
            player.sendMessage(component, Util.NIL_UUID);
        }
        return 1;
    }
}
