package mhmdsabdlh.text;

import java.awt.Color;

public class ColorUtil {

    public static Color getContrastingColor(Color color) {
        // Calculate the perceived brightness of the color
        double brightness = (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue()) / 255;

        // Return black for light colors and white for dark colors
        return brightness > 0.5 ? Color.BLACK : Color.WHITE;
    }

    public static Color adjustBrightness(Color color, float factor) {
        int red = Math.min(255, Math.max(0, (int) (color.getRed() * factor)));
        int green = Math.min(255, Math.max(0, (int) (color.getGreen() * factor)));
        int blue = Math.min(255, Math.max(0, (int) (color.getBlue() * factor)));
        return new Color(red, green, blue);
    }

    public static Color getDarkerColor(Color color, float factor) {
        return adjustBrightness(color, 1 - factor);
    }

    public static Color getLighterColor(Color color, float factor) {
        return adjustBrightness(color, 1 + factor);
    }
}
