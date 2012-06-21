package maxb.pro;

import java.sql.Time;

public class Row_User_Levels
{
    private final int id;
    private final int level;
    private final int mode;
    private int bananas = 0;
    private long time = 0;
    private Integer scores = 0;

    // For reading from DB
    public Row_User_Levels(int id, int level, int mode, int bananas, long time,int scores)
    {
        this.id = id;
        this.level = level;
        this.mode = mode;
        this.bananas = bananas;
        this.time = time;
        this.scores = scores;
    }

    // for store in the game
    public Row_User_Levels(int level, int mode)
    {
        this.level = level;
        this.mode = mode;
        this.bananas = 0;
        this.time = 0;
        this.scores = 0;
        this.id = 0;
    }


    public void set_bananas(int bananas)
    {
        this.bananas = bananas;
    }

    public void set_time(long time)
    {
        this.time = time;
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

    public long get_time_like_integer()
    {
        return time;
    }

    public String get_time_like_string()
    {
        String t = "0:00:00";
        //Time time = Time.valueOf(t);
        return t;
    }

    public Integer get_scores()
    {
        return scores;
    }

    public void Bananas_increment()
    {
        bananas++;
    }

}
