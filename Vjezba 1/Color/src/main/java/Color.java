public class Color {
    int red, green, blue;

    Color(){
        this.red = 0;
        this.green = 0;
        this.blue = 0;
    }

    public Color(int r, int g, int b) {
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public static Color decode(String hexColor) {
        int temp = Integer.decode(hexColor);
        return new Color((temp >> 16) & 255, (temp >> 8) & 255, temp & 255);
    }

    public static void RGBtoHSB(int red, int green, int blue, float[] hsbValues) {
        float hue, saturation, brightness;
        if (hsbValues == null) {
            hsbValues = new float[3];
        }

        int maxColorValue = Math.max(Math.max(red, green), blue);
        int minColorValue = Math.min(Math.min(red, green), blue);
        int maxMinDiff = maxColorValue - minColorValue;

        brightness = ((float) maxColorValue) / 255.0f;
        saturation = (float) maxMinDiff / (float) maxColorValue;

        if (red == maxColorValue) {
            hue = 60 * ((float) (green - blue) / (float) maxMinDiff);
        } else if (green == maxColorValue) {
            hue = 60 * ( 2 + (float) (blue - red) / (float) maxMinDiff);
        } else {
            hue = 60 * ( 4 + ((float) (red - green) / (float) maxMinDiff));
        }

        if(hue < 0){
            hue += 360;
        }

        hsbValues[0] = hue / 360;
        hsbValues[1] = saturation;
        hsbValues[2] = brightness;
    }

    public int getRGB(){
        String rgb = Integer.toHexString(this.red) + Integer.toHexString(this.green) + Integer.toHexString(this.blue);
        return Integer.parseInt(rgb, 16);
    }

    public static void RGBtoCMYK(int red, int green, int blue, float[] cmykValues){
        float cyan, magenta, yellow, black;
        int maxValue = Math.max(Math.max(red, green), blue);

        if(maxValue == 0){
            cyan = 0.0f;
            magenta = 0.0f;
            yellow = 0.0f;
            black = 1.0f;

        } else {

            float white = maxValue / 255.0f;
            cyan = (white - red / 255.f) / white;
            magenta = (white - green / 255.0f) / white;
            yellow = (white - blue / 255.f) / white;
            black = 1.0f - white;
        }

        cmykValues[0] = cyan;
        cmykValues[1] = magenta;
        cmykValues[2] = yellow;
        cmykValues[3] = black;
    }

    public static void RGBtoHSL(int r, int g, int b, float[] hslValues){
        float hue = 0.0f, saturation, lightness;
        float red = (float) r / 255.0f;
        float green = (float) g / 255.0f;
        float blue = (float) b / 255.0f;

        float maxValue = Math.max(Math.max(red, green), blue);
        float minValue = Math.min(Math.min(red, green), blue);
        float maxMinDiff = maxValue - minValue;

        if (maxMinDiff == 0)
            hue = 0.0f;
        else if (maxValue == red)
            hue = 60.0f * (((green - blue) / maxMinDiff) % 6);
        else if (maxValue == green)
            hue = 60.0f * (((blue - red) / maxMinDiff) + 2.0f);
        else if (maxValue == blue)
            hue = (60.0f * ((red - green) / maxMinDiff) + 4.0f);

        lightness = maxMinDiff / 2.0f;

        if (maxMinDiff == 0)
            saturation = 0.0f;
        else
            saturation = maxMinDiff / (1.0f - Math.abs(2.0f * lightness - 1.0f));

        if(hue < 0){
            hue += 360;
        }

        hslValues[0] = hue;
        hslValues[1] = saturation;
        hslValues[2] = lightness;
    }
}
