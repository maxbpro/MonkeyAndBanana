package maxb.pro;


public class Row_User_Actors
{
    private int id = 0;
    private String name = null;
    private int number = 0;
    private Row_User_Levels level = null;

    public Row_User_Actors(int id, String name, int number, Row_User_Levels level)
    {
        this.id = id;
        this.name = name;
        this.number = number;
        this.level = level;
    }

    public void set_id(int id)
    {
        this.id = id;
    }

    public void set_name(String name)
    {
        this.name = name;
    }

    public void set_number(int number)
    {
        this.number = number;
    }

    public void set_level(Row_User_Levels level)
    {
        this.level = level;
    }

    public int get_id()
    {
        return id;
    }

    public String get_name()
    {
        return name;
    }

    public int get_number()
    {
        return number;
    }

    public Row_User_Levels get_level()
    {
        return level;
    }
}
