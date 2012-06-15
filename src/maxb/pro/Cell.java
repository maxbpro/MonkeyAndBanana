package maxb.pro;


import android.content.Context;
import android.widget.ImageView;

public class Cell extends ImageView
{
    private Actor mState = null;
    private Cell rigthCell = null;
    private Cell leftCell = null;
    private Cell topCell = null;
    private Cell bottomCell = null;
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

    public Cell getRigthCell()
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

    public void setRigthCell(Cell cell)
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




}
