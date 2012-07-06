package maxb.pro.DataBaseInteract;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Row_User_Levels
{
    private int level;
    private int mode;
    private int bananas = 0;
    private long time = 0;
    private int scores = 0;

    // For reading from DB
    public Row_User_Levels( int level, int mode, int bananas, long time,int scores)
    {
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
    }


    public void set_bananas(int bananas)
    {
        this.bananas = bananas;
    }

    public void set_time(long time)
    {
        this.time = time;
    }


    public int get_level()
    {
        return level;
    }

    public void set_level(int level)
    {
        this.level = level;
    }

    public int get_mode()
    {
        return mode;
    }

    public void set_mode(int mode)
    {
        this.mode = mode;
    }

    public int get_bananas()
    {
        return bananas;
    }

    public  void set_scores(int scores)
    {
        this.scores = scores;
    }

    public long get_time_like_integer()
    {
        return time;
    }

    public String get_time_like_string()
    {
        return formatIntoMMSSMS(time);
    }

    public int get_scores()
    {
        return scores;
    }

    public void add_to_scores(int delta)
    {
        scores = scores + delta;
    }

    public void Bananas_increment()
    {
        bananas++;
    }

    private String formatIntoMMSSMS(long msIn)
    {
        long ms = msIn % 100;
        long s = msIn / 100;
        long minutes = s / 60;
        long seconds = s % 60;

        String st =  (minutes < 10 ? "0" : "") + minutes
                + ":" + (seconds < 10 ? "0" : "") + seconds
                + ":" + (ms < 10 ? "0" : "") + ms ;
        return st;
    }


}
