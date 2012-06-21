package maxb.pro;


import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

public class RandomPoints
{
    private ArrayList<Point> points = null;
    private final int limit;

    public RandomPoints(int limit)
    {
         points = new ArrayList<Point>();
         this.limit = limit;
    }

    public Point getPoint()
    {
        Random rn = new Random();
        Point point = null;
        do
        {
           point = new Point(rn.nextInt(limit), rn.nextInt(limit));
        }
        while(points.contains(point));
        points.add(point);
        return point;
    }
}
