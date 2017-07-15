public class Constant extends LinearModule
{
    private double value;
    public double getValue()
    {
        return value;
    }
    public void setValue(double value)
    {
        this.value=value;
    }
    public double getSample()
    {
        return value;
    }
    public Constant()
    {
    }
    public Constant(double value)
    {
        this.value=value;
    }
}
