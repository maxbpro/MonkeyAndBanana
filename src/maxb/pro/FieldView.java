package maxb.pro;



import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

public class FieldView extends FrameLayout
{
    private TableLayout field = null;
    private Context context = null;
    private static final int ROWNUM = 6;
    private static final int COLNUM = 6;
    private int cellWidth = 0;
    private Monkey monkey = null;


    public FieldView(Context context, AttributeSet attr)
    {
        super(context, attr);
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;


        cellWidth = Math.round(width/COLNUM);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.field, this, true);
        field = (TableLayout)findViewById(R.id.table);

        for (int i = 0; i < ROWNUM; i++)
        {
            TableRow row = new TableRow(context);
            LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, cellWidth );
            row.setLayoutParams(params);
            for (int j = 0; j < COLNUM; j++)
            {
                Actor em =  getEmptyActor(new Place(i, j));
                em.setBackgroundColor(Color.GREEN);
                row.addView(em);
            }
            field.addView(row);
        }

        monkey = new Monkey(context, new Place(0,0));
        updateElement(monkey);
        Box box = new Box(context, new Place(2,3));
        updateElement(box);
        Banana banana = new Banana(context, new Place(2, 4));
        updateElement(banana);

    }

    private void updateElement(Actor element)
    {
        int rowNum = element.getPlaceRow();
        int colNum = element.getPlaceCol();
        //if(rowNum>ROWNUM || colNum>COLNUM)
            //throw new Exception();

        TableRow row = (TableRow)field.getChildAt(rowNum);

        View view = row.getChildAt(colNum);
        view.setVisibility(GONE);

        TableRow.LayoutParams params = new TableRow.LayoutParams(cellWidth, cellWidth    );
        params.setMargins(1,1,1,1);
        element.setLayoutParams(params);

        row.addView(element, colNum);

    }

    private Actor getEmptyActor(Place place)
    {
        EmptyActor view = new EmptyActor(context, place);
        TableRow.LayoutParams params = new TableRow.LayoutParams(cellWidth, cellWidth    );
        params.setMargins(1,1,1,1);
        view.setLayoutParams(params);
        view.setBackgroundColor(Color.BLACK);
        return view;
    }

    public void toRigthMonkey()
    {
        int row = monkey.getPlaceRow();
        int col = monkey.getPlaceCol();
        updateElement(getEmptyActor(monkey.getPlace()));

        TableRow trow = (TableRow)field.getChildAt(row);
        trow.removeView(monkey);

        col++;
        monkey.setPlace(row, col);
        updateElement(monkey);
    }


}
