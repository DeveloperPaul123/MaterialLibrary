package com.devpaul.materiallibrary.utils;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Paul T. on 6/16/2015.
 *
 * This is an all inclusive random color generator. You can also access all material design colors
 * through this class.
 */
public class ColorGenerator {

    /**
     * Returns a random color with a random red, blue, and green value. Alpha is
     * by default 255.
     * @return a random color.
     */
    public static int getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return Color.rgb(r, g, b);
    }

    /**
     * Returns a random material color. Saturation level is 500.
     * @return a random material color.
     */
    public static int getRandomMaterialColor() {
        Random r = new Random();
        int pos = r.nextInt(MATERIAL_COLORS_500.length);
        return Color.parseColor(MATERIAL_COLORS_500[pos]);
    }

    /**
     * Returns a random material design color given a saturation level.
     * The options are 50, 100, 200, 300, 400, 500, 600, 700, 800 and 900.
     * @param saturation the saturation level.
     * @return a random color.
     */
    public static int getRandomMaterialColor(int saturation) {
        Random r = new Random();
        int pos = 0;
        switch (saturation) {
            case 50:
                pos = r.nextInt(MATERIAL_COLORS_50.length);
                return Color.parseColor(MATERIAL_COLORS_50[pos]);
            case 100:
                pos = r.nextInt(MATERIAL_COLORS_100.length);
                return Color.parseColor(MATERIAL_COLORS_100[pos]);
            case 200:
                pos = r.nextInt(MATERIAL_COLORS_200.length);
                return Color.parseColor(MATERIAL_COLORS_200[pos]);
            case 300:
                pos = r.nextInt(MATERIAL_COLORS_300.length);
                return Color.parseColor(MATERIAL_COLORS_300[pos]);
            case 400:
                pos = r.nextInt(MATERIAL_COLORS_400.length);
                return Color.parseColor(MATERIAL_COLORS_400[pos]);
            case 500:
                pos = r.nextInt(MATERIAL_COLORS_500.length);
                return Color.parseColor(MATERIAL_COLORS_500[pos]);
            case 600:
                pos = r.nextInt(MATERIAL_COLORS_600.length);
                return Color.parseColor(MATERIAL_COLORS_600[pos]);
            case 700:
                pos = r.nextInt(MATERIAL_COLORS_700.length);
                return Color.parseColor(MATERIAL_COLORS_700[pos]);
            case 800:
                pos = r.nextInt(MATERIAL_COLORS_800.length);
                return Color.parseColor(MATERIAL_COLORS_800[pos]);
            case 900:
                pos = r.nextInt(MATERIAL_COLORS_900.length);
                return Color.parseColor(MATERIAL_COLORS_900[pos]);
            default:
                return getRandomMaterialColor();
        }
    }

    /**
     * Generates an array of random colors given the number of colors to create.
     * @param numOfColors, the number of colors to create.
     * @return an array of the random colors.
     */
    public static int[] getRandomColors(int numOfColors) {
        Random random = new Random();
        int colors[] = new int[numOfColors];
        for(int i = 0; i < numOfColors; i++) {
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);

            if((r+g+b) > 450) {
                r  = 110;
                b = 110;
                g = 110;
            }
            colors[i] = Color.rgb(r, g, b);
        }
        return colors;
    }

    public static final String COLOR_RED_50 = "#fde0dc";
    public static final String COLOR_RED_100 = "#f9bdbb";
    public static final String COLOR_RED_200 = "#f69988";
    public static final String COLOR_RED_300 = "#f36c60";
    public static final String COLOR_RED_400 = "#e84e40";
    public static final String COLOR_RED_500 = "#e51c23";
    public static final String COLOR_RED_600 = "#dd191d";
    public static final String COLOR_RED_700 = "#d01716";
    public static final String COLOR_RED_800 = "#c41411";
    public static final String COLOR_RED_900 = "#b0120a";
    public static final String COLOR_RED_A100 = "#ff7997";
    public static final String COLOR_RED_A200 = "#ff5177";
    public static final String COLOR_RED_A400 = "#ff2d6f";
    public static final String COLOR_RED_A700 = "#e00032";
    public static final String COLOR_PINK_50 = "#fce4ec";
    public static final String COLOR_PINK_100 = "#f8bbd0";
    public static final String COLOR_PINK_200 = "#f48fb1";
    public static final String COLOR_PINK_300 = "#f06292";
    public static final String COLOR_PINK_400 = "#ec407a";
    public static final String COLOR_PINK_500 = "#e91e63";
    public static final String COLOR_PINK_600 = "#d81b60";
    public static final String COLOR_PINK_700 = "#c2185b";
    public static final String COLOR_PINK_800 = "#ad1457";
    public static final String COLOR_PINK_900 = "#880e4f";
    public static final String COLOR_PINK_A100 = "#ff80ab";
    public static final String COLOR_PINK_A200 = "#ff4081";
    public static final String COLOR_PINK_A400 = "#f50057";
    public static final String COLOR_PINK_A700 = "#c51162";
    public static final String COLOR_PURPLE_50 = "#f3e5f5";
    public static final String COLOR_PURPLE_100 = "#e1bee7";
    public static final String COLOR_PURPLE_200 = "#cd93d8";
    public static final String COLOR_PURPLE_300 = "#ba68c8";
    public static final String COLOR_PURPLE_400 = "#ab47bc";
    public static final String COLOR_PURPLE_500 = "#9c27b0";
    public static final String COLOR_PURPLE_600 = "#8e24aa";
    public static final String COLOR_PURPLE_700 = "#7b1fa2";
    public static final String COLOR_PURPLE_800 = "#6a1b9a";
    public static final String COLOR_PURPLE_900 = "#4a148c";
    public static final String COLOR_PURPLE_A100 = "#ea80fc";
    public static final String COLOR_PURPLE_A200 = "#e040fb";
    public static final String COLOR_PURPLE_A400 = "#d500f9";
    public static final String COLOR_PURPLE_A700 = "#aa00ff";
    public static final String COLOR_DEEP_PURPLE_50 = "#ede7f6";
    public static final String COLOR_DEEP_PURPLE_100 = "#d1c4e9";
    public static final String COLOR_DEEP_PURPLE_200 = "#b39ddb";
    public static final String COLOR_DEEP_PURPLE_300 = "#9575cd";
    public static final String COLOR_DEEP_PURPLE_400 = "#7e57c2";
    public static final String COLOR_DEEP_PURPLE_500 = "#673ab7";
    public static final String COLOR_DEEP_PURPLE_600 = "#5e35b1";
    public static final String COLOR_DEEP_PURPLE_700 = "#512da8";
    public static final String COLOR_DEEP_PURPLE_800 = "#4527a0";
    public static final String COLOR_DEEP_PURPLE_900 = "#311b92";
    public static final String COLOR_DEEP_PURPLE_A100 = "#b388ff";
    public static final String COLOR_DEEP_PURPLE_A200 = "#7c4dff";
    public static final String COLOR_DEEP_PURPLE_A400 = "#651fff";
    public static final String COLOR_DEEP_PURPLE_A700 = "#6200ea";
    public static final String COLOR_INDIGO_50 = "#e8eaf6";
    public static final String COLOR_INDIGO_100 = "#c5cae9";
    public static final String COLOR_INDIGO_200 = "#9fa8da";
    public static final String COLOR_INDIGO_300 = "#7986cb";
    public static final String COLOR_INDIGO_400 = "#5c6bc0";
    public static final String COLOR_INDIGO_500 = "#3f51b5";
    public static final String COLOR_INDIGO_600 = "#3949ab";
    public static final String COLOR_INDIGO_700 = "#303f9f";
    public static final String COLOR_INDIGO_800 = "#283593";
    public static final String COLOR_INDIGO_900 = "#1a237e";
    public static final String COLOR_INDIGO_A100 = "#8c9eff";
    public static final String COLOR_INDIGO_A200 = "#536dfe";
    public static final String COLOR_INDIGO_A400 = "#3d5afe";
    public static final String COLOR_INDIGO_A700 = "#304ffe";
    public static final String COLOR_BLUE_50 = "#e7e9fd";
    public static final String COLOR_BLUE_100 = "#d0d9ff";
    public static final String COLOR_BLUE_200 = "#afbfff";
    public static final String COLOR_BLUE_300 = "#91a7ff";
    public static final String COLOR_BLUE_400 = "#738ffe";
    public static final String COLOR_BLUE_500 = "#5677fc";
    public static final String COLOR_BLUE_600 = "#4e6cef";
    public static final String COLOR_BLUE_700 = "#455ede";
    public static final String COLOR_BLUE_800 = "#3b50ce";
    public static final String COLOR_BLUE_900 = "#2a36b1";
    public static final String COLOR_BLUE_A100 = "#a6baff";
    public static final String COLOR_BLUE_A200 = "#6889ff";
    public static final String COLOR_BLUE_A400 = "#4d73ff";
    public static final String COLOR_BLUE_A700 = "#4d69ff";
    public static final String COLOR_LIGHT_BLUE_50 = "#e1f5f3";
    public static final String COLOR_LIGHT_BLUE_100 = "#b3e5fc";
    public static final String COLOR_LIGHT_BLUE_200 = "#81d4fa";
    public static final String COLOR_LIGHT_BLUE_300 = "#4fc3f7";
    public static final String COLOR_LIGHT_BLUE_400 = "#29b6f6";
    public static final String COLOR_LIGHT_BLUE_500 = "#03a9f4";
    public static final String COLOR_LIGHT_BLUE_600 = "#039be5";
    public static final String COLOR_LIGHT_BLUE_700 = "#0288d1";
    public static final String COLOR_LIGHT_BLUE_800 = "#0277bd";
    public static final String COLOR_LIGHT_BLUE_900 = "#01579b";
    public static final String COLOR_LIGHT_BLUE_A100 = "#80d8ff";
    public static final String COLOR_LIGHT_BLUE_A200 = "#40c4ff";
    public static final String COLOR_LIGHT_BLUE_A400 = "#00b0ff";
    public static final String COLOR_LIGHT_BLUE_A700 = "#0091ea";
    public static final String COLOR_CYAN_50 = "#e0f7fa";
    public static final String COLOR_CYAN_100 = "#b2ebf2";
    public static final String COLOR_CYAN_200 = "#80deea";
    public static final String COLOR_CYAN_300 = "#4dd0e1";
    public static final String COLOR_CYAN_400 = "#26c6da";
    public static final String COLOR_CYAN_500 = "#00bcd4";
    public static final String COLOR_CYAN_600 = "#00acc1";
    public static final String COLOR_CYAN_700 = "#0097a7";
    public static final String COLOR_CYAN_800 = "#00838f";
    public static final String COLOR_CYAN_900 = "#006064";
    public static final String COLOR_CYAN_A100 = "#84ffff";
    public static final String COLOR_CYAN_A200 = "#18ffff";
    public static final String COLOR_CYAN_A400 = "#00e5ff";
    public static final String COLOR_CYAN_A700 = "#00b8d4";
    public static final String COLOR_TEAL_50 = "#e0f2f1";
    public static final String COLOR_TEAL_100 = "#b2dfdb";
    public static final String COLOR_TEAL_200 = "#80cbc4";
    public static final String COLOR_TEAL_300 = "#4db6ac";
    public static final String COLOR_TEAL_400 = "#26a69a";
    public static final String COLOR_TEAL_500 = "#009688";
    public static final String COLOR_TEAL_600 = "#00897b";
    public static final String COLOR_TEAL_700 = "#00796b";
    public static final String COLOR_TEAL_800 = "#00695c";
    public static final String COLOR_TEAL_900 = "#004d40";
    public static final String COLOR_TEAL_A100 = "#a7ffeb";
    public static final String COLOR_TEAL_A200 = "#64ffda";
    public static final String COLOR_TEAL_A400 = "#1de9b6";
    public static final String COLOR_TEAL_A700 = "#00bfa5";
    public static final String COLOR_GREEN_50 = "#d0f8ce";
    public static final String COLOR_GREEN_100 = "#a3e9a4";
    public static final String COLOR_GREEN_200 = "#72d572";
    public static final String COLOR_GREEN_300 = "#42bd41";
    public static final String COLOR_GREEN_400 = "#2baf2b";
    public static final String COLOR_GREEN_500 = "#259b24";
    public static final String COLOR_GREEN_600 = "#0a8f08";
    public static final String COLOR_GREEN_700 = "#0a7e07";
    public static final String COLOR_GREEN_800 = "#056f00";
    public static final String COLOR_GREEN_900 = "#0d5302";
    public static final String COLOR_GREEN_A100 = "#a2f78d";
    public static final String COLOR_GREEN_A200 = "#5af158";
    public static final String COLOR_GREEN_A400 = "#14e715";
    public static final String COLOR_GREEN_A700 = "#12c700";
    public static final String COLOR_LIGHT_GREEN_50 = "#f1f8e9";
    public static final String COLOR_LIGHT_GREEN_100 = "#dcedc8";
    public static final String COLOR_LIGHT_GREEN_200 = "#c5e1a5";
    public static final String COLOR_LIGHT_GREEN_300 = "#aed581";
    public static final String COLOR_LIGHT_GREEN_400 = "#9ccc65";
    public static final String COLOR_LIGHT_GREEN_500 = "#8bc34a";
    public static final String COLOR_LIGHT_GREEN_600 = "#7cb342";
    public static final String COLOR_LIGHT_GREEN_700 = "#689f38";
    public static final String COLOR_LIGHT_GREEN_800 = "#558b2f";
    public static final String COLOR_LIGHT_GREEN_900 = "#33691e";
    public static final String COLOR_LIGHT_GREEN_A100 = "#ccff90";
    public static final String COLOR_LIGHT_GREEN_A200 = "#b2ff59";
    public static final String COLOR_LIGHT_GREEN_A400 = "#76ff03";
    public static final String COLOR_LIGHT_GREEN_A700 = "#64dd17";
    public static final String COLOR_LIME_50 = "#f9fbe7";
    public static final String COLOR_LIME_100 = "#f0f4c3";
    public static final String COLOR_LIME_200 = "#e6ee9c";
    public static final String COLOR_LIME_300 = "#dce775";
    public static final String COLOR_LIME_400 = "#d4e157";
    public static final String COLOR_LIME_500 = "#cddc39";
    public static final String COLOR_LIME_600 = "#c0ca33";
    public static final String COLOR_LIME_700 = "#afb42b";
    public static final String COLOR_LIME_800 = "#9e9d24";
    public static final String COLOR_LIME_900 = "#827717";
    public static final String COLOR_LIME_A100 = "#f4ff81";
    public static final String COLOR_LIME_A200 = "#eeff41";
    public static final String COLOR_LIME_A400 = "#c6ff00";
    public static final String COLOR_LIME_A700 = "#aeea00";
    public static final String COLOR_YELLOW_50 = "#fffde7";
    public static final String COLOR_YELLOW_100 = "#fff9c4";
    public static final String COLOR_YELLOW_200 = "#fff59d";
    public static final String COLOR_YELLOW_300 = "#fff176";
    public static final String COLOR_YELLOW_400 = "#ffee58";
    public static final String COLOR_YELLOW_500 = "#ffeb3b";
    public static final String COLOR_YELLOW_600 = "#fdd835";
    public static final String COLOR_YELLOW_700 = "#fbc02d";
    public static final String COLOR_YELLOW_800 = "#f9a825";
    public static final String COLOR_YELLOW_900 = "#f57f17";
    public static final String COLOR_YELLOW_A100 = "#ffff8d";
    public static final String COLOR_YELLOW_A200 = "#ffff00";
    public static final String COLOR_YELLOW_A400 = "#ffea00";
    public static final String COLOR_YELLOW_A700 = "#ffd600";
    public static final String COLOR_AMBER_50 = "#fff8e1";
    public static final String COLOR_AMBER_100 = "#ffecb3";
    public static final String COLOR_AMBER_200 = "#ffe082";
    public static final String COLOR_AMBER_300 = "#ffd54f";
    public static final String COLOR_AMBER_400 = "#ffca28";
    public static final String COLOR_AMBER_500 = "#ffc107";
    public static final String COLOR_AMBER_600 = "#ffb300";
    public static final String COLOR_AMBER_700 = "#ffa000";
    public static final String COLOR_AMBER_800 = "#ff8f00";
    public static final String COLOR_AMBER_900 = "#ff6f00";
    public static final String COLOR_AMBER_A100 = "#ffe57f";
    public static final String COLOR_AMBER_A200 = "#ffd740";
    public static final String COLOR_AMBER_A400 = "#ffc400";
    public static final String COLOR_AMBER_A700 = "#ffab00";
    public static final String COLOR_ORANGE_50 = "#fff3e0";
    public static final String COLOR_ORANGE_100 = "#ffe0b2";
    public static final String COLOR_ORANGE_200 = "#ffcc80";
    public static final String COLOR_ORANGE_300 = "#ffb74d";
    public static final String COLOR_ORANGE_400 = "#ffa726";
    public static final String COLOR_ORANGE_500 = "#ff9800";
    public static final String COLOR_ORANGE_600 = "#fb8c00";
    public static final String COLOR_ORANGE_700 = "#f57c00";
    public static final String COLOR_ORANGE_800 = "#ef6c00";
    public static final String COLOR_ORANGE_900 = "#e65100";
    public static final String COLOR_ORANGE_A100 = "#ffd180";
    public static final String COLOR_ORANGE_A200 = "#ffab40";
    public static final String COLOR_ORANGE_A400 = "#ff9100";
    public static final String COLOR_ORANGE_A700 = "#ff6d00";
    public static final String COLOR_DEEP_ORANGE_50 = "#fbe9e7";
    public static final String COLOR_DEEP_ORANGE_100 = "#ffccbc";
    public static final String COLOR_DEEP_ORANGE_200 = "#ffab91";
    public static final String COLOR_DEEP_ORANGE_300 = "#ff8a65";
    public static final String COLOR_DEEP_ORANGE_400 = "#ff7043";
    public static final String COLOR_DEEP_ORANGE_500 = "#ff5722";
    public static final String COLOR_DEEP_ORANGE_600 = "#f4511e";
    public static final String COLOR_DEEP_ORANGE_700 = "#e64a19";
    public static final String COLOR_DEEP_ORANGE_800 = "#d84315";
    public static final String COLOR_DEEP_ORANGE_900 = "#bf360c";
    public static final String COLOR_DEEP_ORANGE_A100 = "#ff9e80";
    public static final String COLOR_DEEP_ORANGE_A200 = "#ff6e40";
    public static final String COLOR_DEEP_ORANGE_A400 = "#ff3d00";
    public static final String COLOR_DEEP_ORANGE_A700 = "#dd2c00";
    public static final String COLOR_GREY_0 = "#ffffff";
    public static final String COLOR_GREY_50 = "#fafafa";
    public static final String COLOR_GREY_100 = "#f5f5f5";
    public static final String COLOR_GREY_200 = "#eeeeee";
    public static final String COLOR_GREY_300 = "#e0e0e0";
    public static final String COLOR_GREY_400 = "#bdbdbd";
    public static final String COLOR_GREY_500 = "#9e9e9e";
    public static final String COLOR_GREY_600 = "#757575";
    public static final String COLOR_GREY_700 = "#616161";
    public static final String COLOR_GREY_800 = "#424242";
    public static final String COLOR_GREY_900 = "#212121";
    public static final String COLOR_GREY_1000 = "#ffffff";
    public static final String COLOR_BROWN_50 = "#eceff1";
    public static final String COLOR_BROWN_100 = "#cfd8dc";
    public static final String COLOR_BROWN_200 = "#b0bef5";
    public static final String COLOR_BROWN_300 = "#90a4ae";
    public static final String COLOR_BROWN_400 = "#78909c";
    public static final String COLOR_BROWN_500 = "#607d8b";
    public static final String COLOR_BROWN_600 = "#6546e7a";
    public static final String COLOR_BROWN_700 = "#455a64";
    public static final String COLOR_BROWN_800 = "#37474f";
    public static final String COLOR_BROWN_900 = "#263238";

    public static final String[] MATERIAL_COLORS_100 = {COLOR_RED_100
            ,COLOR_RED_A100
            ,COLOR_PINK_100
            ,COLOR_PINK_A100
            ,COLOR_PURPLE_100
            ,COLOR_PURPLE_A100
            ,COLOR_DEEP_PURPLE_100
            ,COLOR_DEEP_PURPLE_A100
            ,COLOR_INDIGO_100
            ,COLOR_INDIGO_A100
            ,COLOR_BLUE_100
            ,COLOR_BLUE_A100
            ,COLOR_LIGHT_BLUE_100
            ,COLOR_LIGHT_BLUE_A100
            ,COLOR_CYAN_100
            ,COLOR_CYAN_A100
            ,COLOR_TEAL_100
            ,COLOR_TEAL_A100
            ,COLOR_GREEN_100
            ,COLOR_GREEN_A100
            ,COLOR_LIGHT_GREEN_100
            ,COLOR_LIGHT_GREEN_A100
            ,COLOR_LIME_100
            ,COLOR_LIME_A100
            ,COLOR_YELLOW_100
            ,COLOR_YELLOW_A100
            ,COLOR_AMBER_100
            ,COLOR_AMBER_A100
            ,COLOR_ORANGE_100
            ,COLOR_ORANGE_A100
            ,COLOR_DEEP_ORANGE_100
            ,COLOR_DEEP_ORANGE_A100
            ,COLOR_BROWN_100
            ,COLOR_GREY_100
            ,COLOR_GREY_1000
            ,COLOR_BROWN_100
    };

    public static final String[] MATERIAL_COLORS_200 = {COLOR_RED_200
            ,COLOR_RED_A200
            ,COLOR_PINK_200
            ,COLOR_PINK_A200
            ,COLOR_PURPLE_200
            ,COLOR_PURPLE_A200
            ,COLOR_DEEP_PURPLE_200
            ,COLOR_DEEP_PURPLE_A200
            ,COLOR_INDIGO_200
            ,COLOR_INDIGO_A200
            ,COLOR_BLUE_200
            ,COLOR_BLUE_A200
            ,COLOR_LIGHT_BLUE_200
            ,COLOR_LIGHT_BLUE_A200
            ,COLOR_CYAN_200
            ,COLOR_CYAN_A200
            ,COLOR_TEAL_200
            ,COLOR_TEAL_A200
            ,COLOR_GREEN_200
            ,COLOR_GREEN_A200
            ,COLOR_LIGHT_GREEN_200
            ,COLOR_LIGHT_GREEN_A200
            ,COLOR_LIME_200
            ,COLOR_LIME_A200
            ,COLOR_YELLOW_200
            ,COLOR_YELLOW_A200
            ,COLOR_AMBER_200
            ,COLOR_AMBER_A200
            ,COLOR_ORANGE_200
            ,COLOR_ORANGE_A200
            ,COLOR_DEEP_ORANGE_200
            ,COLOR_DEEP_ORANGE_A200
            ,COLOR_BROWN_200
            ,COLOR_GREY_200
            ,COLOR_BROWN_200
    };

    public static final String[] MATERIAL_COLORS_300 = {COLOR_RED_300
            ,COLOR_PINK_300
            ,COLOR_PURPLE_300
            ,COLOR_DEEP_PURPLE_300
            ,COLOR_INDIGO_300
            ,COLOR_BLUE_300
            ,COLOR_LIGHT_BLUE_300
            ,COLOR_CYAN_300
            ,COLOR_TEAL_300
            ,COLOR_GREEN_300
            ,COLOR_LIGHT_GREEN_300
            ,COLOR_LIME_300
            ,COLOR_YELLOW_300
            ,COLOR_AMBER_300
            ,COLOR_ORANGE_300
            ,COLOR_DEEP_ORANGE_300
            ,COLOR_BROWN_300
            ,COLOR_GREY_300
            ,COLOR_BROWN_300
    };

    public static final String[] MATERIAL_COLORS_400 = {COLOR_RED_400
            ,COLOR_RED_A400
            ,COLOR_PINK_400
            ,COLOR_PINK_A400
            ,COLOR_PURPLE_400
            ,COLOR_PURPLE_A400
            ,COLOR_DEEP_PURPLE_400
            ,COLOR_DEEP_PURPLE_A400
            ,COLOR_INDIGO_400
            ,COLOR_INDIGO_A400
            ,COLOR_BLUE_400
            ,COLOR_BLUE_A400
            ,COLOR_LIGHT_BLUE_400
            ,COLOR_LIGHT_BLUE_A400
            ,COLOR_CYAN_400
            ,COLOR_CYAN_A400
            ,COLOR_TEAL_400
            ,COLOR_TEAL_A400
            ,COLOR_GREEN_400
            ,COLOR_GREEN_A400
            ,COLOR_LIGHT_GREEN_400
            ,COLOR_LIGHT_GREEN_A400
            ,COLOR_LIME_400
            ,COLOR_LIME_A400
            ,COLOR_YELLOW_400
            ,COLOR_YELLOW_A400
            ,COLOR_AMBER_400
            ,COLOR_AMBER_A400
            ,COLOR_ORANGE_400
            ,COLOR_ORANGE_A400
            ,COLOR_DEEP_ORANGE_400
            ,COLOR_DEEP_ORANGE_A400
            ,COLOR_BROWN_400
            ,COLOR_GREY_400
            ,COLOR_BROWN_400
    };

    public static final String[] MATERIAL_COLORS_50 = {COLOR_RED_50
            ,COLOR_RED_500
            ,COLOR_PINK_50
            ,COLOR_PURPLE_50
            ,COLOR_DEEP_PURPLE_50
            ,COLOR_INDIGO_50
            ,COLOR_BLUE_50
            ,COLOR_LIGHT_BLUE_50
            ,COLOR_CYAN_50
            ,COLOR_TEAL_50
            ,COLOR_GREEN_50
            ,COLOR_LIGHT_GREEN_50
            ,COLOR_LIME_50
            ,COLOR_YELLOW_50
            ,COLOR_AMBER_50
            ,COLOR_ORANGE_50
            ,COLOR_DEEP_ORANGE_50
            ,COLOR_BROWN_50
            ,COLOR_GREY_50
            ,COLOR_BROWN_50
    };

    public static final String[] MATERIAL_COLORS_500 = {
             COLOR_RED_500
            ,COLOR_PINK_500
            ,COLOR_PURPLE_500
            ,COLOR_DEEP_PURPLE_500
            ,COLOR_INDIGO_500
            ,COLOR_BLUE_500
            ,COLOR_LIGHT_BLUE_500
            ,COLOR_CYAN_500
            ,COLOR_TEAL_500
            ,COLOR_GREEN_500
            ,COLOR_LIGHT_GREEN_500
            ,COLOR_LIME_500
            ,COLOR_YELLOW_500
            ,COLOR_AMBER_500
            ,COLOR_ORANGE_500
            ,COLOR_DEEP_ORANGE_500
            ,COLOR_BROWN_500
            ,COLOR_GREY_500
    };

    public static final String[] MATERIAL_COLORS_600 = {COLOR_RED_600
            ,COLOR_PINK_600
            ,COLOR_PURPLE_600
            ,COLOR_DEEP_PURPLE_600
            ,COLOR_INDIGO_600
            ,COLOR_BLUE_600
            ,COLOR_LIGHT_BLUE_600
            ,COLOR_CYAN_600
            ,COLOR_TEAL_600
            ,COLOR_GREEN_600
            ,COLOR_LIGHT_GREEN_600
            ,COLOR_LIME_600
            ,COLOR_YELLOW_600
            ,COLOR_AMBER_600
            ,COLOR_ORANGE_600
            ,COLOR_DEEP_ORANGE_600
            ,COLOR_GREY_600
    };

    public static final String[] MATERIAL_COLORS_700 = {COLOR_RED_700
            ,COLOR_RED_A700
            ,COLOR_PINK_700
            ,COLOR_PINK_A700
            ,COLOR_PURPLE_700
            ,COLOR_PURPLE_A700
            ,COLOR_DEEP_PURPLE_700
            ,COLOR_DEEP_PURPLE_A700
            ,COLOR_INDIGO_700
            ,COLOR_INDIGO_A700
            ,COLOR_BLUE_700
            ,COLOR_BLUE_A700
            ,COLOR_LIGHT_BLUE_700
            ,COLOR_LIGHT_BLUE_A700
            ,COLOR_CYAN_700
            ,COLOR_CYAN_A700
            ,COLOR_TEAL_700
            ,COLOR_TEAL_A700
            ,COLOR_GREEN_700
            ,COLOR_GREEN_A700
            ,COLOR_LIGHT_GREEN_700
            ,COLOR_LIGHT_GREEN_A700
            ,COLOR_LIME_700
            ,COLOR_LIME_A700
            ,COLOR_YELLOW_700
            ,COLOR_YELLOW_A700
            ,COLOR_AMBER_700
            ,COLOR_AMBER_A700
            ,COLOR_ORANGE_700
            ,COLOR_ORANGE_A700
            ,COLOR_DEEP_ORANGE_700
            ,COLOR_DEEP_ORANGE_A700
            ,COLOR_BROWN_700
            ,COLOR_GREY_700
            ,COLOR_BROWN_700
    };

    public static final String[] MATERIAL_COLORS_800 = {COLOR_RED_800
            ,COLOR_PINK_800
            ,COLOR_PURPLE_800
            ,COLOR_DEEP_PURPLE_800
            ,COLOR_INDIGO_800
            ,COLOR_BLUE_800
            ,COLOR_LIGHT_BLUE_800
            ,COLOR_CYAN_800
            ,COLOR_TEAL_800
            ,COLOR_GREEN_800
            ,COLOR_LIGHT_GREEN_800
            ,COLOR_LIME_800
            ,COLOR_YELLOW_800
            ,COLOR_AMBER_800
            ,COLOR_ORANGE_800
            ,COLOR_DEEP_ORANGE_800
            ,COLOR_BROWN_800
            ,COLOR_GREY_800
            ,COLOR_BROWN_800
    };

    public static final String[] MATERIAL_COLORS_900 = {COLOR_RED_900
            ,COLOR_PINK_900
            ,COLOR_PURPLE_900
            ,COLOR_DEEP_PURPLE_900
            ,COLOR_INDIGO_900
            ,COLOR_BLUE_900
            ,COLOR_LIGHT_BLUE_900
            ,COLOR_CYAN_900
            ,COLOR_TEAL_900
            ,COLOR_GREEN_900
            ,COLOR_LIGHT_GREEN_900
            ,COLOR_LIME_900
            ,COLOR_YELLOW_900
            ,COLOR_AMBER_900
            ,COLOR_ORANGE_900
            ,COLOR_DEEP_ORANGE_900
            ,COLOR_BROWN_900
            ,COLOR_GREY_900
            ,COLOR_BROWN_900
    };

}