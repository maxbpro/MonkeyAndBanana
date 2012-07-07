package maxb.pro.Specials;

import android.app.Activity;
import android.content.Context;
import android.view.*;

/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class IndicatorRoute
{
    public enum Route{LEFT, TOP, RIGHT, BOTTOM, LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM}
    private enum DefaultOrientations{LANDSCAPE, PORTRAIT}
    private OrientationInfo orientation = null;
    private DefaultOrientations defaultOrientation = DefaultOrientations.LANDSCAPE;

    public IndicatorRoute(Context context)
    {
        this.orientation = new OrientationInfo(context);
        int default_orientation = (((Activity)context).getWindowManager().getDefaultDisplay().getOrientation());
        switch (default_orientation)
        {
            case Surface.ROTATION_0:
                defaultOrientation = DefaultOrientations.LANDSCAPE;
                break;
            case Surface.ROTATION_90:
                defaultOrientation = DefaultOrientations.PORTRAIT;
                break;
        }
    }

    public Route getRoute()
    {
        //float x = orientation.getX();
        //float y = orientation.getY();
        if(defaultOrientation == DefaultOrientations.LANDSCAPE)
        {
            if(orientation.getX()>1 && orientation.getY()>1)
                return Route.LEFT_BOTTOM;
            if(orientation.getX()<-1 && orientation.getY()<-1)
                return Route.RIGHT_TOP;
            if(orientation.getY()<-1 && orientation.getX()>1)
                return Route.LEFT_TOP;
            if(orientation.getY()>1 && orientation.getX()<-1)
                return Route.RIGHT_BOTTOM;
           if(orientation.getX()>1 && orientation.getY()<1)
              return Route.LEFT;
           if(orientation.getX()<-1 && orientation.getY()>-1)
               return  Route.RIGHT;
           if(orientation.getY()>1 && orientation.getX()<1)
               return Route.BOTTOM;
           if(orientation.getY()<-1 && orientation.getX()>-1)
               return Route.TOP;


        }
        else
        {
            if(orientation.getX()>1 && orientation.getY()>1)
                return Route.RIGHT_BOTTOM;
            if(orientation.getX()<-1 && orientation.getY()<-1)
                return Route.LEFT_TOP;
            if(orientation.getY()<-1 && orientation.getX()>1)
                return Route.LEFT_BOTTOM;
            if(orientation.getY()>1 && orientation.getX()<-1)
                return Route.RIGHT_TOP;
            if(orientation.getX()>1 && orientation.getY()<1)
                return Route.BOTTOM;
            if(orientation.getX()<-1 && orientation.getY()>-1)
                return  Route.TOP;
            if(orientation.getY()>1 && orientation.getX()<1)
                return Route.RIGHT;
            if(orientation.getY()<-1 && orientation.getX()>-1)
                return Route.LEFT;
        }
        return null;
    }

}
