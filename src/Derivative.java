public class Derivative extends Filter
{
    private double value;
    double previousInput=0;
    public Derivative(LinearModule input)
    {
        super(input);
    }
    double getSample()
    {
        return value;
    }
    void timeStep(double deltaTime)
    {
        super.timeStep(deltaTime);
        double in=input.getSample();
        value=(in-previousInput)/deltaTime;
        previousInput=in;
    }
}
