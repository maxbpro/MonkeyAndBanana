package maxb.pro;



import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class FieldView extends FrameLayout
{
    private TableLayout field = null;
    private Context context = null;
    private static final int ROWNUM = 6;
    private static final int COLNUM = 6;
    private int cellWidth = 0;


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
        field = (TableLayout)findViewById(R.id.field);

        for (int i = 0; i < ROWNUM; i++)
        {
            TableRow row = new TableRow(context);
            LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, cellWidth );
            row.setLayoutParams(params);
            for (int j = 0; j < COLNUM; j++)
            {
                row.addView(getEmptyView());
            }
            field.addView(row);
        }

        Monkey monkey = new Monkey(context,0,0);
        addElement(monkey, 0, 0);
        Box box = new Box(context, new Place(2,3));
        addElement(box, 2, 3);
        Banana banana = new Banana(context, new Place(2, 4));
        addElement(banana, 2, 4);

    }

    private void addElement(View element, int rowNum, int colNum)
    {
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

    private View getEmptyView()
    {
        View view = new View(context);
        TableRow.LayoutParams params = new TableRow.LayoutParams(cellWidth, cellWidth    );
        params.setMargins(1,1,1,1);
        view.setLayoutParams(params);
        view.setBackgroundColor(Color.BLACK);
        return view;
    }


}
