package maxb.pro;



import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.*;

public class FieldView extends FrameLayout
{
    private TableLayout field = null;
    private Context context = null;
    private static final int ROWNUM = 6;
    private static final int COLNUM = 6;
    private int cellWidth = 0;
    private Cell monkeyCell = null;
    private Cell boxCell = null;
    private Cell bananaCell = null;


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
            TableRow row = getTableRow();
            for (int j = 0; j < COLNUM; j++)
            {
                Cell view = getEmptyCell();
                row.addView(view);

                if (i==0 && j==0)
                    monkeyCell = view;
            }
            field.addView(row);
        }
        monkeyCell.updateState(MonkeySingleton.getInstance());
        initAllCells();



    }



    private Cell getEmptyCell()
    {
        Cell cell = new Cell(context, new EmptyActor());
        TableRow.LayoutParams params = new TableRow.LayoutParams(cellWidth, cellWidth    );
        params.setMargins(1, 1, 1, 1);
        cell.setLayoutParams(params);
        cell.setBackgroundColor(Color.GREEN);
        return cell;
    }

    private TableRow getTableRow()
    {
        TableRow row = new TableRow(context);
        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, cellWidth );
        row.setLayoutParams(params);
        return row;
    }

    private void initAllCells()
    {
        for (int i = 1; i < ROWNUM-1; i++)
        {
            TableRow row = (TableRow)field.getChildAt(i);
            TableRow rowTop = (TableRow)field.getChildAt(i-1);
            TableRow rowBottom = (TableRow)field.getChildAt(i+1);
            for (int j = 1; j < COLNUM-1; j++)
            {
                Cell cellLeft = (Cell)row.getChildAt(j-1);
                Cell cellRight = (Cell)row.getChildAt(j+1);
                Cell cellTop = (Cell)rowTop.getChildAt(j);
                Cell cellBottom = (Cell)rowBottom.getChildAt(j);
                Cell cell = (Cell)row.getChildAt(j);
                cell.setRigthCell(cellRight);
                cell.setLeftCell(cellLeft);
                cell.setTopCell(cellTop);
                cell.setBottomCell(cellBottom);
            }
        }

        for (int i = 0; i < ROWNUM; i++)
        {
            TableRow row = (TableRow)field.getChildAt(i);
            Cell cell = (Cell)row.getChildAt(0);
            Cell cellRigth = (Cell)row.getChildAt(1);
            cell.setRigthCell(cellRigth);

            Cell rCell = (Cell)row.getChildAt(COLNUM-1);
            Cell rcellLeft = (Cell)row.getChildAt(COLNUM-2);
            rCell.setLeftCell(rcellLeft);

            if(i==0)
            {
                TableRow rowBottom = (TableRow)field.getChildAt(i+1);
                Cell cellBottom = (Cell)rowBottom.getChildAt(0);
                cell.setBottomCell(cellBottom);
            }
            else
            {
                if(i==ROWNUM-1)
                {
                    TableRow rowTop = (TableRow)field.getChildAt(ROWNUM-2);
                    Cell cellTop = (Cell)rowTop.getChildAt(0);
                    rCell.setTopCell(cellTop);
                }
                else
                {
                    TableRow rowTop = (TableRow)field.getChildAt(i-1);
                    TableRow rowBottom = (TableRow)field.getChildAt(i+1);

                    Cell cellTop = (Cell)rowTop.getChildAt(0);
                    Cell cellBottom = (Cell)rowBottom.getChildAt(0);
                    cell.setTopCell(cellTop);
                    cell.setBottomCell(cellBottom);

                    Cell rCellTop = (Cell)rowTop.getChildAt(COLNUM-1);
                    Cell rCellBottom = (Cell)rowBottom.getChildAt(COLNUM-1);
                    rCell.setTopCell(rCellTop);
                    rCell.setBottomCell(rCellBottom);
                }

            }

        }

        TableRow row = (TableRow)field.getChildAt(0);
        TableRow rowBottom = (TableRow)field.getChildAt(1);
        TableRow brow = (TableRow)field.getChildAt(ROWNUM-1);
        TableRow rowTop = (TableRow)field.getChildAt(ROWNUM-2);

        for(int i = 0; i < COLNUM; i++)
        {
            Cell cell = (Cell)row.getChildAt(i);
            Cell cellBottom = (Cell)rowBottom.getChildAt(i);
            cell.setBottomCell(cellBottom);

            Cell cellLeft = (Cell)row.getChildAt(i-1);
            Cell cellRigth = (Cell)row.getChildAt(i+1);
            cell.setRigthCell(cellRigth);
            cell.setLeftCell(cellLeft);

            Cell bCell = (Cell)brow.getChildAt(i);
            Cell cellTop = (Cell)rowTop.getChildAt(i);
            bCell.setTopCell(cellTop);

            Cell bCellLeft = (Cell)brow.getChildAt(ROWNUM-1);
            Cell bCellRigth = (Cell)brow.getChildAt(ROWNUM-1);
            bCell.setRigthCell(bCellRigth);
            bCell.setLeftCell(bCellLeft);





        }
    }

    private void toRigthMonkey()
    {
        toMoveMonkey(monkeyCell, monkeyCell.getRigthCell());
    }

    private void toDownMonkey()
    {
        toMoveMonkey(monkeyCell, monkeyCell.getBottomCell());
    }

    private void toLeftMonkey()
    {
         toMoveMonkey(monkeyCell, monkeyCell.getLeftCell());
    }

    private void toUpMonkey()
    {
        toMoveMonkey(monkeyCell, monkeyCell.getTopCell());
    }

    private void toMoveMonkey(Cell cell, Cell newCell)
    {
        cell.updateState(new EmptyActor());
        newCell.updateState(MonkeySingleton.getInstance());
        cell = newCell;
    }


}
