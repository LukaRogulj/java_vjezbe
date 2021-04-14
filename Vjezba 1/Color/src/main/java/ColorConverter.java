public class ColorConverter {
    public static void main(String[] args) {

            String hexColor = "0x1FF0FF";
            Color c = Color.decode(hexColor);

            float[] hsb = new float[3];
            float[] cmyk = new float[4];
            float[] hsl = new float[3];

            Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsb);
            Color.RGBtoCMYK(c.getRed(), c.getGreen(), c.getBlue(), cmyk);
            Color.RGBtoHSL(c.getRed(), c.getGreen(), c.getBlue(),hsl);

            System.out.println("Boja u HEX formatu: 0x" + Integer.toHexString(c.getRGB()));
            System.out.println("Boja u RGB formatu: " + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue());
            System.out.println("Boja u HSB formatu: " + hsb[0] * 360 + "°, " + hsb[1] * 100 + "%, " + hsb[2] * 100 + "%");
            System.out.println("Boja u CMYK formatu: " + "Cyan: " + cmyk[0] + " Magenta: " + cmyk[1] + " Yellow: " + cmyk[2] + " Black: " + cmyk[3]);
            System.out.println("Boja u HSL test formatu: " + hsl[0]  + "°, " + hsl[1] * 100 + "%, " + hsl[2] * 100 + "%");
    }
}

