package maxb.pro;



import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.*;

import java.util.ArrayList;
import java.util.Random;

public class FieldView extends FrameLayout
{
    private TableLayout field = null;
    private Context context = null;
    private int NUM = 0;
    private int cellSizePixels = 0;
    private int fieldSizePixels = 0;

    private ScoreSingleton score = null;
    private Cell monkeyCell = null;
    private Cell boxCell = null;
    private ArrayList<Cell> bananaCells = null;
    private int currentBananasCount = 0;
    private int BananasCount = 0;


    public FieldView(Context context, AttributeSet attr)
    {
        super(context, attr);
        this.context = context;
        setWillNotDraw(false);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.field, this, true);
        field = (TableLayout)findViewById(R.id.table);
    }

    public void initField(int NUM, ArrayList<Point> bananas)
    {

        this.NUM = NUM;
        this.BananasCount = bananas.size();
        score = ScoreSingleton.getInstance();
        determineSize();
        bananaCells = new ArrayList<Cell>();
        for (int i = 0; i < NUM; i++)
        {
            TableRow row = getTableRow();
            for (int j = 0; j < NUM; j++)
            {
                Cell view = getEmptyCell();
                row.addView(view);

                if (i==0 && j==0)
                    monkeyCell = view;
                if (i==0 && j==1)
                    boxCell = view;
                if(bananas.contains(new Point(i,j)))
                    bananaCells.add(view);
            }
            field.addView(row);
        }
        monkeyCell.updateState(MonkeySingleton.getInstance());
        boxCell.updateState(BoxSingleton.getInstance());
        for(Cell cell : bananaCells)
              cell.updateState(new Banana());
        initAllCells();
        //invalidate();
    }





    private Cell getEmptyCell()
    {
        Cell cell = new Cell(context, new EmptyActor());
        TableRow.LayoutParams params = new TableRow.LayoutParams(cellSizePixels, cellSizePixels);
        params.setMargins(1, 1, 1, 1);
        cell.setLayoutParams(params);
        cell.setBackgroundColor(Color.WHITE);
        cell.getBackground().setAlpha(50);
        return cell;
    }

    private TableRow getTableRow()
    {
        TableRow row = new TableRow(context);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT );
        row.setLayoutParams(params);
        row.setBackgroundColor(Color.WHITE);
        row.getBackground().setAlpha(50);
        return row;
    }

    private void initAllCells()
    {
        for (int i = 1; i < NUM-1; i++)
        {
            TableRow row = (TableRow)field.getChildAt(i);
            TableRow rowTop = (TableRow)field.getChildAt(i-1);
            TableRow rowBottom = (TableRow)field.getChildAt(i+1);
            for (int j = 1; j < NUM-1; j++)
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
                cell.setPosition(Cell.Position.CENTER);
            }
        }

        for (int i = 1; i < NUM-1; i++)
        {
            TableRow row = (TableRow)field.getChildAt(i);
            Cell cell = (Cell)row.getChildAt(0);
            Cell rCell = (Cell)row.getChildAt(NUM-1);

            TableRow rowTop = (TableRow)field.getChildAt(i-1);
            TableRow rowBottom = (TableRow)field.getChildAt(i+1);

            Cell cellTop = (Cell)rowTop.getChildAt(0);
            Cell cellBottom = (Cell)rowBottom.getChildAt(0);
            cell.setTopCell(cellTop);
            cell.setBottomCell(cellBottom);
            Cell cellRigth = (Cell)row.getChildAt(1);
            cell.setRigthCell(cellRigth);
            cell.setPosition(Cell.Position.LEFT);

            Cell rCellTop = (Cell)rowTop.getChildAt(NUM-1);
            Cell rCellBottom = (Cell)rowBottom.getChildAt(NUM-1);
            rCell.setTopCell(rCellTop);
            rCell.setBottomCell(rCellBottom);
            Cell rcellLeft = (Cell)row.getChildAt(NUM-2);
            rCell.setLeftCell(rcellLeft);
            rCell.setPosition(Cell.Position.RIGHT);
        }

        TableRow row = (TableRow)field.getChildAt(0);
        TableRow rowBottom = (TableRow)field.getChildAt(1);
        TableRow brow = (TableRow)field.getChildAt(NUM-1);
        TableRow rowTop = (TableRow)field.getChildAt(NUM-2);

        Cell leftTopCell = (Cell)row.getChildAt(0);
        Cell rigthCell = (Cell)row.getChildAt(1);
        leftTopCell.setRigthCell(rigthCell);
        Cell bottomCell = (Cell)rowBottom.getChildAt(0);
        leftTopCell.setBottomCell(bottomCell);
        leftTopCell.setPosition(Cell.Position.LEFTTOP);

        Cell rigthTopCell = (Cell)row.getChildAt(NUM-1);
        Cell leftCell = (Cell)row.getChildAt(NUM-2);
        rigthTopCell.setLeftCell(leftCell);
        bottomCell = (Cell)rowBottom.getChildAt(NUM-1);
        rigthTopCell.setBottomCell(bottomCell);
        rigthTopCell.setPosition(Cell.Position.RIGTHTOP);

        Cell bottomLeftCell = (Cell)brow.getChildAt(0);
        rigthCell = (Cell)brow.getChildAt(1);
        bottomLeftCell.setRigthCell(rigthCell);
        Cell topCell = (Cell)rowTop.getChildAt(0);
        bottomLeftCell.setTopCell(topCell);
        bottomLeftCell.setPosition(Cell.Position.LEFTBOTTOM);

        Cell bottomRightCell = (Cell)brow.getChildAt(NUM-1);
        leftCell = (Cell)brow.getChildAt(NUM-2);
        bottomRightCell.setLeftCell(leftCell);
        topCell = (Cell)rowTop.getChildAt(NUM-1);
        bottomRightCell.setTopCell(topCell);
        bottomRightCell.setPosition(Cell.Position.RIGTHBOTTOM);

        for(int i = 1; i < NUM-1; i++)
        {
            Cell cell = (Cell)row.getChildAt(i);
            Cell cellBottom = (Cell)rowBottom.getChildAt(i);
            cell.setBottomCell(cellBottom);

            Cell cellLeft = (Cell)row.getChildAt(i-1);
            Cell cellRigth = (Cell)row.getChildAt(i+1);
            cell.setRigthCell(cellRigth);
            cell.setLeftCell(cellLeft);
            cell.setPosition(Cell.Position.TOP);

            Cell bCell = (Cell)brow.getChildAt(i);
            Cell cellTop = (Cell)rowTop.getChildAt(i);
            bCell.setTopCell(cellTop);

            Cell bCellLeft = (Cell)brow.getChildAt(NUM-1);
            Cell bCellRigth = (Cell)brow.getChildAt(NUM-1);
            bCell.setRigthCell(bCellRigth);
            bCell.setLeftCell(bCellLeft);
            bCell.setPosition(Cell.Position.BOTTOM);
        }
    }



    private boolean toRigthMonkey()
    {
       return toMoveMonkey(monkeyCell.getRigthCell());
    }

    private boolean toDownMonkey()
    {
        return toMoveMonkey(monkeyCell.getBottomCell());
    }

    private boolean toLeftMonkey()
    {
       return  toMoveMonkey(monkeyCell.getLeftCell());
    }

    private boolean toUpMonkey()
    {
       return toMoveMonkey(monkeyCell.getTopCell());
    }

    private boolean toMoveMonkey(Cell newCell)
    {
        boolean result = true;
        monkeyCell.updateState(new EmptyActor());
        if (newCell.getState() instanceof IActivate)
             result = ((IActivate) newCell.getState()).activate(score);
        if(newCell.getState() instanceof Banana)
            currentBananasCount++;
        newCell.updateState(MonkeySingleton.getInstance());
        monkeyCell = newCell;
        return result;
    }



    public void moveMonkey()
    {
        boolean result = true;
        Random rn = new Random();
        switch (monkeyCell.getPosition())
        {
            case LEFTTOP:
            {
                int num = rn.nextInt(2);
                if(num==0)
                    result = toRigthMonkey();
                else
                    result = toDownMonkey();
                break;
            }
            case LEFTBOTTOM:
            {
                int num = rn.nextInt(2);
                if(num==0)
                    result = toRigthMonkey();
                else
                    result = toUpMonkey();
                break;
            }
            case RIGTHTOP:
            {
                int num = rn.nextInt(2);
                if (num==0)
                    result = toLeftMonkey();
                else
                    result = toDownMonkey();
                break;
            }
            case RIGTHBOTTOM:
            {
                int num = rn.nextInt(2);
                if (num==0)
                    result = toLeftMonkey();
                else
                    result = toUpMonkey();
                break;
            }
            case LEFT:
            {
                int num = rn.nextInt(3);
                switch (num)
                {
                    case 0:
                        result = toUpMonkey();
                        break;
                    case 1:
                        result = toRigthMonkey();
                        break;
                    case 2:
                        result = toDownMonkey();
                        break;
                }
                break;
            }
            case RIGHT:
            {
                int num = rn.nextInt(3);
                switch (num)
                {
                    case 0:
                        result = toUpMonkey();
                        break;
                    case 1:
                        result = toDownMonkey();
                        break;
                    case 2:
                        result = toLeftMonkey();
                        break;
                }
                break;
            }
            case TOP:
            {
                int num = rn.nextInt(3);
                switch (num)
                {
                    case 0:
                        result = toDownMonkey();
                        break;
                    case 1:
                        result = toRigthMonkey();
                        break;
                    case 2:
                        result = toLeftMonkey();
                        break;
                }
                break;
            }
            case BOTTOM:
            {
                int num = rn.nextInt(3);
                switch (num)
                {
                    case 0:
                        result = toUpMonkey();
                        break;
                    case 1:
                        result = toRigthMonkey();
                        break;
                    case 2:
                        result = toLeftMonkey();
                        break;
                }
                break;
            }
            case CENTER:
            {
                int num = rn.nextInt(4);
                switch (num)
                {
                    case 0:
                        result = toLeftMonkey();
                        break;
                    case 1:
                        result = toUpMonkey();
                        break;
                    case 2:
                        result = toRigthMonkey();
                        break;
                    case 3:
                        result = toDownMonkey();
                        break;
                }
                break;
            }
        }
        //
        isLostLevel(result);
        isFinishLevel();
    }

    private void isLostLevel(boolean result)
    {
         if(result == false)
         {
             // exit from the level like fail
             final LostDialog dialog = new LostDialog(context, R.style.DialogTheme);
             ((SceneActivity)context).pause();
             dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                 @Override
                 public void onDismiss(DialogInterface dialogInterface) {
                     //if(dialog.getResult()==LostDialog.Result.REFRESH)

                 }
             });
             dialog.show();
         }
    }

    private void isFinishLevel()
    {
        if (currentBananasCount == BananasCount)
        {
            // exit from the level like success
            ((SceneActivity)context).pause();
            final ResultDialog dialog = new ResultDialog(context, R.style.DialogTheme,
                    ResultDialog.Mode.FROM_GAME, null);
            // pause
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                        switch (dialog.getResult())
                        {
                            case  REFRESH:
                                break;
                            case NEXT_LEVEL:
                                break;
                        }
                }
            });
            dialog.show();

        }
             // save result
    }

    private void determineSize()
    {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);

        // - 10*2 for margin of all device
        int heightPixels = dm.heightPixels - 20 - 50;
        cellSizePixels = Math.round(heightPixels/NUM)-2;
        fieldSizePixels = heightPixels;
    }

    private int convertPixelsToDP(int pixels)
    {
        final float scale = getResources().getDisplayMetrics().density;
        int dp = Math.round(pixels/scale);
        return dp;
    }

    public int getSize()
    {
        return fieldSizePixels;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        /*Paint pen = new Paint(Paint.ANTI_ALIAS_FLAG);
        pen.setStrokeWidth(2);
        int counter = 0;
        for (int i=0; i<NUM+1; i++)
        {
            //canvas.drawLine(counter, 0, counter, getHeight(), pen);
            //canvas.drawLine(0, counter, getWidth(), counter, pen);
            counter+=(cellSizePixels+2);
        }
        */
    }
}
