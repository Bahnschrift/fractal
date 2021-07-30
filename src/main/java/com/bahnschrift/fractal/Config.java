package com.bahnschrift.fractal;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Config {
    private static final String FILE_NAME = "config/fractal-palettes.json";
    private static final String DEFAULT_FILE =
            """
{
  "palettes": {
    "default": [
      "minecraft:black_stained_glass",
      "minecraft:gray_stained_glass",
      "minecraft:purple_stained_glass",
      "minecraft:red_stained_glass",
      "minecraft:orange_stained_glass",
      "minecraft:lime_stained_glass",
      "minecraft:green_stained_glass",
      "minecraft:blue_stained_glass",
      "minecraft:light_blue_stained_glass",
      "minecraft:light_gray_stained_glass",
      "minecraft:air"
    ]
  }
}
            """;

    public static HashMap<String, ArrayList<String>> get() {
        JsonParser parser = new JsonParser();
        File configFile = new File(FILE_NAME);
        JsonObject palettesJson = (JsonObject) parser.parse(DEFAULT_FILE);

        try {
            if (configFile.createNewFile()) {
                Main.LOGGER.info("Fractal palette file not found. Creating");
                FileWriter configWriter = new FileWriter(configFile);
                configWriter.write(DEFAULT_FILE);
                configWriter.close();
            } else {
                FileReader configReader = new FileReader(configFile);
                palettesJson = (JsonObject) parser.parse(configReader);
                configReader.close();
            }
        } catch (IOException e) {
            Main.LOGGER.error("Error reading fractal palette file.");
            e.printStackTrace();
        }
        return getFromJson(palettesJson);
    }

    private static HashMap<String, ArrayList<String>> getFromJson(JsonObject palettesJson) {
        HashMap<String, ArrayList<String>> palettes = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : palettesJson.getAsJsonObject("palettes").entrySet()) {
            String paletteName = entry.getKey();
            ArrayList<String> palette = new ArrayList<>();
            JsonArray paletteJsonArray = entry.getValue().getAsJsonArray();
            if (paletteJsonArray != null)
                for (JsonElement block : paletteJsonArray)
                    palette.add(block.getAsString());
            palettes.put(paletteName, palette);
        }
        return palettes;
    }
}
