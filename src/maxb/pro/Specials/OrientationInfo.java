package maxb.pro.Specials;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class OrientationInfo
{
    private float[] accelerometerValues = null;
    private float[] magneticValues = null;
    private float[] values = null;
    private Boolean magneticFieldExists = true;


    public OrientationInfo(Context context)
    {
        accelerometerValues = new float[]{0,0,0};
        magneticValues = new float[]{0,0,0};
        values = new float[]{0,0,0};

        SensorManager mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        final SensorEventListener SensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event)
            {
                if(magneticFieldExists)
                {
                    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
                        System.arraycopy(event.values,0,accelerometerValues,0, 3);
                    if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
                        System.arraycopy(event.values,0,magneticValues,0, 3);
                    calculateOrientation();
                }
                else
                {
                    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
                        System.arraycopy(event.values,0,values,0, 3);
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i){}
        };

        mSensorManager.registerListener(SensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) == null)
            magneticFieldExists = false;
        else
            mSensorManager.registerListener(SensorListener,
                    mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                    SensorManager.SENSOR_DELAY_UI);
    }

    private void calculateOrientation()
    {
        float[] R = new float[9];
        //float[] outR = new float[9];
        SensorManager.getRotationMatrix(R, null, accelerometerValues,
                magneticValues);
        //SensorManager.remapCoordinateSystem(R,SensorManager.AXIS_X,SensorManager.AXIS_Z,outR);
        SensorManager.getOrientation(R, values);
        values[0] = (float) Math.toDegrees(values[0]);
        values[1] = (float) Math.toDegrees(values[1]);
        values[2] = (float) Math.toDegrees(values[2]);
    }

    public float[] getValues()
    {
        return  values;
    }

    public float getX()
    {
        return values[0];
    }

    public float getY()
    {
        return values[1];
    }

}
