import java.util.Random;

public class Sensor {
    private String name;
    private int factor;
    private int bottomRange;
    private int upperRange;
    private String unit;
    private float sensorValue;

    {
        sensorValue = 0.0f;
    }

    //region Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFactor() {
        return factor;
    }

    public void setFactor(int factor) {
        this.factor = factor;
    }

    public int getBottomRange() {
        return bottomRange;
    }

    public void setBottomRange(int bottomRange) {
        this.bottomRange = bottomRange;
    }

    public int getUpperRange() {
        return upperRange;
    }

    public void setUpperRange(int upperRange) {
        this.upperRange = upperRange;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(float sensorValue) {
        this.sensorValue = sensorValue;
    }
    //endregion

    public Sensor() {
    }

    Sensor(String name, int factor, int bottomRange, int upperRange , String unit){
        this.name = name;
        this.factor = factor;
        this.bottomRange = bottomRange;
        this.upperRange = upperRange;
        this.unit = unit;
    }

    public void setSensorValue(){
        Random rand = new Random();
        int randomNumberInRange = rand.ints(bottomRange, upperRange).findAny().getAsInt();
        if (factor > 0) {
            this.sensorValue = (float) randomNumberInRange / factor;
        } else {
            this.sensorValue = (float) randomNumberInRange;
        }
    }
}
