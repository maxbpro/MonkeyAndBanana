package maxb.pro.app.Specials;


import android.graphics.Point;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomPoints
{
    private Set<Point> points = null;
    private final int limit;

    public RandomPoints(int limit)
    {
         points = new HashSet<Point>();
         this.limit = limit;
    }

    public Point getPoint()
    {
        Random rn = new Random();

        Point point = new Point(rn.nextInt(limit), rn.nextInt(limit));
        while(!points.add(point))
            point = new Point(rn.nextInt(limit), rn.nextInt(limit));

        return point;
    }
}
