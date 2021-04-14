import org.testng.Assert;
import org.testng.annotations.Test;

public class UnitTests {
    @Test
    public void testDecode(){
        System.out.println("Testing decode()");

        String hexColor = "0x1FF0FF";
        Color color = Color.decode(hexColor);

        Assert.assertEquals(color.getBlue(), 255);
        System.out.println("Blue set correctly");

        Assert.assertEquals(color.getGreen(), 240);
        System.out.println("Green set correctly");

        Assert.assertEquals(color.getRed(), 31);
        System.out.println("Red set correctly");

        System.out.println("End decode()");
    }
    @Test
    public void testRGBtoHSB(){
        System.out.println("Testing RGBtoHSB()");

        float[] hsbValues = new float[3];
        float[] testValues = new float[]{0.51116073f, 0.8784314f, 1.0f};

        Color color = Color.decode("0x1FF0FF");
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsbValues);

        Assert.assertEquals(hsbValues, testValues);
        System.out.println("HSB set correctly");

        System.out.println("End RGBtoHSB()");
    }
    @Test
    public void testRGBtoCMYK(){
        System.out.println("Testing RGBtoCMYK()");

        float[] cmykValues = new float[4];
        float[] testValues = new float[]{0.8784314f, 0.058823526f, 0.0f, 0.0f};

        Color color = Color.decode("0x1FF0FF");
        Color.RGBtoCMYK(color.getRed(), color.getGreen(), color.getBlue(), cmykValues);

        Assert.assertEquals(cmykValues, testValues);
        System.out.println("CMYK set correctly");

        System.out.println("End RGBtoHSB()");
    }
    @Test
    public void testRGBtoHSL(){
        System.out.println("Testing RGBtoHSL()");

        float[] hslValues = new float[3];
        float[] testValues = new float[]{308.01785f, 1.0f, 0.4392157f};

        Color color = Color.decode("0x1FF0FF");
        Color.RGBtoHSL(color.getRed(), color.getGreen(), color.getBlue(), hslValues);

        Assert.assertEquals(hslValues, testValues);
        System.out.println("HSB set correctly");

        System.out.println("End RGBtoHSB()");
    }
}
