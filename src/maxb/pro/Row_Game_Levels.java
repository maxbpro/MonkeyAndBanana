package maxb.pro;


public class Row_Game_Levels
{
    private int id = 0;
    private int level = 0;
    private int mode = 0;
    private int bananas = 0;

    public Row_Game_Levels(int id, int level, int mode, int bananas)
    {
        this.id = id;
        this.level = level;
        this.mode = mode;
        this.bananas = bananas;
    }

    public void set_id(int id)
    {
        this.id = id;
    }

    public void set_level(int level)
    {
        this.level = level;
    }

    public void set_mode(int mode)
    {
        this.mode = mode;
    }

    public void set_bananas(int bananas)
    {
        this.bananas = bananas;
    }

    public int get_id()
    {
        return id;
    }

    public int get_level()
    {
        return level;
    }

    public int get_mode()
    {
        return mode;
    }

    public int get_bananas()
    {
        return bananas;
    }
}
