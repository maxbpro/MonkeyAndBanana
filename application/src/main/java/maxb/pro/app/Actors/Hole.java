package maxb.pro.app.Actors;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import maxb.pro.app.Actors.Extra;

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
public class Hole extends Extra
{
    private static final String NAME = "HOLE";

    @Override
    public String getClassName() {
        return NAME;
    }

    @Override
    public Drawable getImage(Resources resources) {
        return null;
    }
}
