public abstract class Filter extends LinearModule
{
    public LinearModule input;
    public Filter(LinearModule input)
    {
        this.input=input;
    }
    void timeStep(double deltaTime)
    {
        input.timeStep(deltaTime);
    }
}
