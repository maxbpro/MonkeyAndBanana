package maxb.pro.app.Views;

import maxb.pro.*;
import maxb.pro.app.Actors.*;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.*;
import maxb.pro.app.R;
import maxb.pro.app.SceneActivity;
import maxb.pro.app.Specials.IndicatorRoute;
import maxb.pro.app.Specials.RandomPoints;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public class FieldView extends FrameLayout
{
    private SceneActivity scene = null;
    private TableLayout field = null;
    private Context context = null;
    private int NUM = 0;
    private int cellSizePixels = 0;
    private int fieldSizePixels = 0;
    private boolean isBoxFound = false;
    private Thing isThingReturn = null;
    private int deltaScores = 0;

    private Cell monkeyCell = null;
    private Cell boxCell = null;
    private ArrayList<Point> pointsBananas = null;
    private Map<Point, IHasName> actorsMap = null;

    public static final String SNAKE = "SNAKE";
    public static final String TELEPORT = "TELEPORT";
    public static final String LIME = "LIME";

    public FieldView(Context context, AttributeSet attr)
    {
        super(context, attr);
        this.context = context;
        scene = (SceneActivity)context;
        setWillNotDraw(false);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.field, this, true);
        field = (TableLayout)findViewById(R.id.table);
    }

    public void initField(int NUM, int bananasCount, ArrayList<IHasName> actors)
    {
        this.NUM = NUM;
        initPointsCollections(bananasCount, actors);
        determineSize();

        for (int i = 0; i < NUM; i++)
        {
            TableRow row = getTableRow();
            for (int j = 0; j < NUM; j++)
            {
                Cell cell = getEmptyCell();
                row.addView(cell);

                Point currentPoint = new Point(i,j);
                if (i==0 && j==0)
                    monkeyCell = cell;
                if (i==0 && j==1)
                    boxCell = cell;
                if(pointsBananas.contains(currentPoint))
                    cell.updateState(new Banana());
                if(actorsMap.containsKey(currentPoint))
                {
                    IHasName activeted = actorsMap.get(currentPoint);
                    if(activeted instanceof Snake)
                        cell.updateState(new Snake());
                    else
                        if (activeted instanceof Teleport)
                            cell.updateState(new Teleport());
                }
            }
            field.addView(row);
        }
        monkeyCell.updateState(MonkeySingleton.getInstance());
        boxCell.updateState(BoxSingleton.getInstance());
        initAllCells();
    }

    public int getSize()
    {
        return fieldSizePixels;
    }

    public int getDeltaScores()
    {
        return deltaScores;
    }

    // Monkey's movements

    public IndicatorView.Smiles moveMonkey(IndicatorRoute.Route route)
    {
        switch (monkeyCell.getPosition())
        {
            case LEFTTOP:
            {
                switch (route)
                {
                    case RIGHT: return toRigthMonkey();
                    case BOTTOM: return toDownMonkey();
                    case RIGHT_BOTTOM: return toRightDownMonkey();
                }
                break;
            }
            case LEFTBOTTOM:
            {
                switch (route)
                {
                    case RIGHT: return toRigthMonkey();
                    case TOP: return toUpMonkey();
                    case RIGHT_TOP: return toRightUpMonkey();
                }
                break;
            }
            case RIGTHTOP:
            {
                switch (route)
                {
                    case LEFT: return toLeftMonkey();
                    case BOTTOM: return toDownMonkey();
                    case LEFT_BOTTOM: return toLeftDownMonkey();
                }
                break;
            }
            case RIGTHBOTTOM:
            {
                switch (route)
                {
                    case LEFT: return toLeftMonkey();
                    case TOP: return toUpMonkey();
                    case LEFT_TOP: return toLeftUpMonkey();
                }
                break;
            }
            case LEFT:
            {
                switch (route)
                {
                    case RIGHT: return toRigthMonkey();
                    case BOTTOM: return toDownMonkey();
                    case TOP: return toUpMonkey();
                    case RIGHT_BOTTOM: return toRightDownMonkey();
                    case RIGHT_TOP: return toRightUpMonkey();
                }
                break;
            }
            case RIGHT:
            {
                switch (route)
                {
                    case LEFT: return toLeftMonkey();
                    case BOTTOM: return toDownMonkey();
                    case TOP: return toUpMonkey();
                    case LEFT_BOTTOM: return toLeftDownMonkey();
                    case LEFT_TOP: return toLeftUpMonkey();
                }
                break;
            }
            case TOP:
            {
                switch (route)
                {
                    case RIGHT: return toRigthMonkey();
                    case BOTTOM: return toDownMonkey();
                    case LEFT: return toLeftMonkey();
                    case LEFT_BOTTOM: return toLeftDownMonkey();
                    case RIGHT_BOTTOM: return toRightDownMonkey();
                }
                break;
            }
            case BOTTOM:
            {
                switch (route)
                {
                    case RIGHT: return toRigthMonkey();
                    case LEFT: return toLeftMonkey();
                    case TOP: return toUpMonkey();
                    case LEFT_TOP: return toLeftUpMonkey();
                    case RIGHT_TOP: return toRightUpMonkey();
                }
                break;
            }
            case CENTER:
            {
                switch (route)
                {
                    case RIGHT: return toRigthMonkey();
                    case BOTTOM: return toDownMonkey();
                    case TOP: return toUpMonkey();
                    case LEFT: return toLeftMonkey();
                    case LEFT_TOP: return toLeftUpMonkey();
                    case LEFT_BOTTOM: return toLeftDownMonkey();
                    case RIGHT_TOP: return toRightUpMonkey();
                    case RIGHT_BOTTOM: return toRightDownMonkey();
                }
                break;
            }
        }
        return IndicatorView.Smiles.NORMAL;
    }

    private IndicatorView.Smiles toRigthMonkey()
    {
        return toMoveMonkey(monkeyCell.getRightCell());
    }

    private IndicatorView.Smiles toDownMonkey()
    {
        return toMoveMonkey(monkeyCell.getBottomCell());
    }

    private IndicatorView.Smiles toLeftMonkey()
    {
        return toMoveMonkey(monkeyCell.getLeftCell());
    }

    private IndicatorView.Smiles toUpMonkey()
    {
        return toMoveMonkey(monkeyCell.getTopCell());
    }

    private IndicatorView.Smiles toLeftUpMonkey()
    {
        return toMoveMonkey(monkeyCell.getLeft_TopCell());
    }

    private IndicatorView.Smiles toLeftDownMonkey()
    {
        return toMoveMonkey(monkeyCell.getLeft_BottomCell());
    }

    private IndicatorView.Smiles toRightUpMonkey()
    {
        return toMoveMonkey(monkeyCell.getRight_TopCell());
    }

    private IndicatorView.Smiles toRightDownMonkey()
    {
        return toMoveMonkey(monkeyCell.getRight_BottomCell());
    }


    private IndicatorView.Smiles toMoveMonkey(Cell newCell)
    {
        IndicatorView.Smiles smile = IndicatorView.Smiles.NORMAL;

        if (isThingReturn != null)
        {
            // it is returning old Thing
            if(isThingReturn instanceof Banana)
               monkeyCell.updateState(new Banana());
            else
               if(isThingReturn instanceof Teleport)
                   monkeyCell.updateState(new Teleport());
            isThingReturn = null;
        }
        else
            monkeyCell.updateState(new EmptyActor());

        // Essential block

        if(isBoxFound)
        {
           if (newCell.getState() instanceof IActivate)
               deltaScores =  ((IActivate) newCell.getState()).activate();

           if(newCell.getState() instanceof Banana)
               smile = IndicatorView.Smiles.IN_LOVE;
        }
        else
        {

            if (newCell.getState() instanceof BoxSingleton)
            {
                smile = IndicatorView.Smiles.WINK;
                isBoxFound = true;
            }
            else
            {
                if (newCell.getState() instanceof Enemy)
                {
                    deltaScores = ((Enemy) newCell.getState()).activate();
                    smile = IndicatorView.Smiles.CRY;
                }
                else
                {
                    if (newCell.getState() instanceof Thing)
                        isThingReturn = (Thing)newCell.getState();
                    smile = IndicatorView.Smiles.ANRGY;
                }


            }


        }
        newCell.updateState(MonkeySingleton.getInstance());
        monkeyCell = newCell;

        return smile;
    }

    // View

    private void initPointsCollections(int bananasCount, ArrayList<IHasName> actors)
    {
        RandomPoints rn = new RandomPoints(NUM);
        pointsBananas = new ArrayList<Point>();
        for (int i = 0; i < bananasCount; i++)
        {
            Point point = rn.getPoint();
            pointsBananas.add(point);
        }
        actorsMap = new LinkedHashMap<Point, IHasName>();
        for(IHasName actor : actors)
        {
            Point point = rn.getPoint();
            actorsMap.put(point, actor);
        }
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

                Cell cellLeft_Top = (Cell)rowTop.getChildAt(j-1);
                Cell cellRight_Top = (Cell)rowTop.getChildAt(j+1);
                Cell cellLeft_Bottom = (Cell)rowBottom.getChildAt(j-1);
                Cell cellRight_Bottom = (Cell)rowBottom.getChildAt(j+1);

                cell.setRightCell(cellRight);
                cell.setLeftCell(cellLeft);
                cell.setTopCell(cellTop);
                cell.setBottomCell(cellBottom);

                cell.setLeft_TopCell(cellLeft_Top);
                cell.setRight_TopCell(cellRight_Top);
                cell.setLeft_BottomCell(cellLeft_Bottom);
                cell.setRight_BottomCell(cellRight_Bottom);
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
            Cell cellRigth = (Cell)row.getChildAt(1);
            Cell cellRightTop = (Cell)rowTop.getChildAt(1);
            Cell cellRightBottom = (Cell)rowBottom.getChildAt(1);
            cell.setTopCell(cellTop);
            cell.setBottomCell(cellBottom);
            cell.setRightCell(cellRigth);
            cell.setRight_TopCell(cellRightTop);
            cell.setRight_BottomCell(cellRightBottom);
            cell.setPosition(Cell.Position.LEFT);

            Cell rCellTop = (Cell)rowTop.getChildAt(NUM-1);
            Cell rCellBottom = (Cell)rowBottom.getChildAt(NUM-1);
            Cell rcellLeft = (Cell)row.getChildAt(NUM-2);
            Cell rcellLeftTop = (Cell)rowTop.getChildAt(NUM-2);
            Cell rcellLeftBottom = (Cell)rowBottom.getChildAt(NUM-2);
            rCell.setTopCell(rCellTop);
            rCell.setBottomCell(rCellBottom);
            rCell.setLeftCell(rcellLeft);
            rCell.setLeft_BottomCell(rcellLeftBottom);
            rCell.setLeft_TopCell(rcellLeftTop);
            rCell.setPosition(Cell.Position.RIGHT);
        }

        TableRow row = (TableRow)field.getChildAt(0);
        TableRow rowBottom = (TableRow)field.getChildAt(1);
        TableRow brow = (TableRow)field.getChildAt(NUM-1);
        TableRow rowTop = (TableRow)field.getChildAt(NUM-2);

        Cell leftTopCell = (Cell)row.getChildAt(0);
        Cell rigthCell = (Cell)row.getChildAt(1);
        leftTopCell.setRightCell(rigthCell);
        Cell bottomCell = (Cell)rowBottom.getChildAt(0);
        leftTopCell.setBottomCell(bottomCell);
        Cell rightbottomCell = (Cell)rowBottom.getChildAt(1);
        leftTopCell.setRight_BottomCell(rightbottomCell);
        leftTopCell.setPosition(Cell.Position.LEFTTOP);

        Cell rigthTopCell = (Cell)row.getChildAt(NUM-1);
        Cell leftCell = (Cell)row.getChildAt(NUM-2);
        rigthTopCell.setLeftCell(leftCell);
        bottomCell = (Cell)rowBottom.getChildAt(NUM-1);
        rigthTopCell.setBottomCell(bottomCell);
        Cell leftbottomCell = (Cell)rowBottom.getChildAt(NUM-2);
        rigthTopCell.setLeft_BottomCell(leftbottomCell);
        rigthTopCell.setPosition(Cell.Position.RIGTHTOP);

        Cell bottomLeftCell = (Cell)brow.getChildAt(0);
        rigthCell = (Cell)brow.getChildAt(1);
        bottomLeftCell.setRightCell(rigthCell);
        Cell topCell = (Cell)rowTop.getChildAt(0);
        bottomLeftCell.setTopCell(topCell);
        Cell righttopCell = (Cell)rowTop.getChildAt(1);
        bottomLeftCell.setRight_TopCell(righttopCell);
        bottomLeftCell.setPosition(Cell.Position.LEFTBOTTOM);

        Cell bottomRightCell = (Cell)brow.getChildAt(NUM-1);
        leftCell = (Cell)brow.getChildAt(NUM-2);
        bottomRightCell.setLeftCell(leftCell);
        topCell = (Cell)rowTop.getChildAt(NUM-1);
        bottomRightCell.setTopCell(topCell);
        Cell lefttopCell = (Cell)rowTop.getChildAt(NUM-2);
        bottomRightCell.setLeft_TopCell(lefttopCell);
        bottomRightCell.setPosition(Cell.Position.RIGTHBOTTOM);

        for(int i = 1; i < NUM-1; i++)
        {
            Cell cell = (Cell)row.getChildAt(i);
            Cell cellBottom = (Cell)rowBottom.getChildAt(i);
            cell.setBottomCell(cellBottom);

            Cell cellLeft = (Cell)row.getChildAt(i-1);
            Cell cellRigth = (Cell)row.getChildAt(i+1);
            Cell cellLeftBottom = (Cell)rowBottom.getChildAt(i-1);
            Cell cellRightBottom = (Cell)rowBottom.getChildAt(i+1);
            cell.setRightCell(cellRigth);
            cell.setLeftCell(cellLeft);
            cell.setLeft_BottomCell(cellLeftBottom);
            cell.setRight_BottomCell(cellRightBottom);
            cell.setPosition(Cell.Position.TOP);

            Cell bCell = (Cell)brow.getChildAt(i);
            Cell cellTop = (Cell)rowTop.getChildAt(i);
            bCell.setTopCell(cellTop);

            Cell bCellLeft = (Cell)brow.getChildAt(i-1);
            Cell bCellRigth = (Cell)brow.getChildAt(i+1);
            Cell bCellLeftTop = (Cell)rowTop.getChildAt(i-1);
            Cell bCellRightTop = (Cell)rowTop.getChildAt(i+1);
            bCell.setRightCell(bCellRigth);
            bCell.setLeftCell(bCellLeft);
            bCell.setLeft_TopCell(bCellLeftTop);
            bCell.setRight_TopCell(bCellRightTop);
            bCell.setPosition(Cell.Position.BOTTOM);
        }
    }

    private void determineSize()
    {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
        // - 10*2 for margin
        int heightPixels = dm.heightPixels - 20;
        cellSizePixels = Math.round(heightPixels/NUM)-2;
        fieldSizePixels = heightPixels;
    }




}
