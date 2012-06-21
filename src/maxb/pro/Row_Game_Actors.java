package maxb.pro;


public class Row_Game_Actors
{
    private String name = null;
    private int number = 0;
    private Row_Game_Levels level = null;

    public Row_Game_Actors(String name, int number, Row_Game_Levels level)
    {
        this.name = name;
        this.number = number;
        this.level = level;
    }

    public void set_name(String name)
    {
        this.name = name;
    }

    public void set_number(int number)
    {
        this.number = number;
    }

    public void set_level_id(Row_Game_Levels level)
    {
        this.level = level;
    }

    public String get_name()
    {
        return name;
    }

    public int get_number()
    {
        return number;
    }

    public Row_Game_Levels get_level()
    {
        return level;
    }
}
