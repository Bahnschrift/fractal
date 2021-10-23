# Fractal
A Minecraft mod for generating 3D fractals with a single command.

![Mandelbox Scale -3](https://i.imgur.com/5IuT1RH.png)

## Command Usage
**/fractal palettes**

Prints the current block palettes as defined in fractal-palettes.json.

**/fractal mandelbox <loc1> <loc2> [palette] [scale]**

Generates a [mandelbox](https://en.wikipedia.org/wiki/Mandelbox) fractal between loc1 and loc2 with a specified palette. Scale, which defaults to -3, significantly changes the generation of the fractal.

**/fractal mandelbulb <loc1> <loc2> [palette] [A] [B] [C] [D]**

Generates a [mandelbulb](https://en.wikipedia.org/wiki/Mandelbulb) fractal between loc1 and loc2 with a specified palette using the quintic formula. A, B, C and D are constants that effect the generation of the fractal.


## Configuration
Block palettes can be configured with config/fractal-palettes.json. To add a new palette, just add it to the file. An example fractal-palettes.json is shown below:
```json
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
        ],
        "fancy": [
            "minecraft:black_stained_glass",
            "minecraft:gray_stained_glass",
            "minecraft:diamond_block",
            "minecraft:iron_block",
            "minecraft:gold_block",
            "minecraft:diamond_block",
            "minecraft:iron_block",
            "minecraft:gold_block",
            "minecraft:diamond_block",
            "minecraft:iron_block",
            "minecraft:air"
        ]
    }
}
```
