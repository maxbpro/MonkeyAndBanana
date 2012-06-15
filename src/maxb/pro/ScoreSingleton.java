package maxb.pro;


public class ScoreSingleton
{
    private static ScoreSingleton instance = new ScoreSingleton();
    private ScoreSingleton(){}

    public static ScoreSingleton getInstance()
    {
        return instance;
    }

    private int mValue = 0;

    public int getValue()
    {
        return mValue;
    }

    public void setValue(int value)
    {
        mValue = value;
    }
}
