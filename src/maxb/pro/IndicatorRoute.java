package maxb.pro;

import android.content.Context;

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
    public enum Route{LEFT, TOP, RIGHT, BOTTOM}
    private OrientationInfo orientation = null;

    public IndicatorRoute(Context context)
    {
        this.orientation = new OrientationInfo(context);
    }

    public Route getRoute()
    {
        float x = orientation.getX();
        float y = orientation.getY();
        if(orientation.getX()>1)
           return Route.LEFT;
        if(orientation.getX()<-1)
            return  Route.RIGHT;
        if(orientation.getY()>1)
            return Route.BOTTOM;
        if(orientation.getY()<-1)
            return Route.TOP;
        return null;
    }
}
