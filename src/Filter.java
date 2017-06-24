public abstract class Filter extends AudioModule
{
    public AudioModule input;
    public Filter(AudioModule input)
    {
        this.input=input;
    }

}
