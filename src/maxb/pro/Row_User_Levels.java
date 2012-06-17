package maxb.pro;

public class Row_User_Levels
{
    private int id = 0;
    private int level = 0;
    private int mode = 0;
    private int bananas = 0;
    private String time = null;
    private int scores = 0;

    public Row_User_Levels(int id, int level, int mode, int bananas, String time,int scores)
    {
        this.id = id;
        this.level = level;
        this.mode = mode;
        this.bananas = bananas;
        this.time = time;
        this.scores = scores;
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

    public void set_time(String time)
    {
        this.time = time;
    }

    public void set_scores(int scores)
    {
        this.scores = scores;
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

    public String get_time()
    {
        return time;
    }

    public int get_scores()
    {
        return scores;
    }

}
