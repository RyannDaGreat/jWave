public class Legato extends Filter
{
    private double proportionPerSecond;
    private double value;
    public Legato(LinearModule input)
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
        double newValue=input.getSample();
        double alpha=proportionPerSecond;//Math.pow(proportionPerSecond,deltaTime);
        value=newValue*alpha+value*(1-alpha);
    }
    public double getAlphaPerSecond()
    {
        return proportionPerSecond;
    }
    public void setAlphaPerSecond(double proportionPerSecond)
    {
        this.proportionPerSecond=proportionPerSecond;
    }
}
