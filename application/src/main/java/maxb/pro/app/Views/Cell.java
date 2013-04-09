package maxb.pro.app.Views;


import android.content.Context;
import android.widget.ImageView;
import maxb.pro.app.Actors.Actor;

public class Cell extends ImageView
{
    private Actor mState = null;
    private Cell rigthCell = null;
    private Cell leftCell = null;
    private Cell topCell = null;
    private Cell bottomCell = null;

    private Cell left_bottomCell = null;
    private Cell right_bottomCell = null;
    private Cell left_topCell = null;
    private Cell right_topCell = null;
    public enum Position {LEFT, RIGHT, TOP, BOTTOM, LEFTTOP,LEFTBOTTOM, RIGTHTOP, RIGTHBOTTOM, CENTER }
    private Position mPosition = Position.CENTER;

    public Cell(Context context, Actor mState)
    {
        super(context);
        this.mState = mState;
    }

    public void updateState(Actor mState)
    {
        this.mState = mState;
        setImageDrawable(mState.getImage(getResources()));
    }

    public Actor getState()
    {
        return mState;
    }

    public void setPosition(Position position)
    {
        mPosition = position;
    }

    public Position getPosition()
    {
        return mPosition;
    }

    public Cell getRightCell()
    {
        return rigthCell;
    }

    public Cell getLeftCell()
    {
        return leftCell;
    }

    public Cell getTopCell()
    {
        return topCell;
    }

    public Cell getBottomCell()
    {
        return bottomCell;
    }

    public void setRightCell(Cell cell)
    {
        this.rigthCell = cell;
    }

    public void setLeftCell(Cell cell)
    {
        this.leftCell = cell;
    }

    public void setTopCell(Cell cell)
    {
        this.topCell = cell;
    }

    public void setBottomCell(Cell cell)
    {
         this.bottomCell = cell;
    }

    public Cell getLeft_BottomCell()
    {
        return left_bottomCell;
    }

    public Cell getLeft_TopCell()
    {
        return left_topCell;
    }

    public Cell getRight_BottomCell()
    {
        return right_bottomCell;
    }

    public Cell getRight_TopCell()
    {
        return right_topCell;
    }

    public void setLeft_BottomCell(Cell cell)
    {
        left_bottomCell = cell;
    }

    public void setLeft_TopCell(Cell cell)
    {
        left_topCell = cell;
    }

    public void setRight_BottomCell(Cell cell)
    {
        right_bottomCell = cell;
    }

    public void setRight_TopCell(Cell cell)
    {
        right_topCell = cell;
    }




}
