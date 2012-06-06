package maxb.pro;


import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

public class IndicatorView extends FrameLayout
{
    private float[] accelerometerValues = null;
    private float[] magneticValues = null;


    private TextView txt1 = null;
    private TextView txt2 = null;
    private TextView txt3 = null;

    public IndicatorView(Context context, AttributeSet attr)
    {
        super(context, attr);
        //updateOrientation(new float[]{0,0,0});
        accelerometerValues = new float[]{0,0,0};
        magneticValues = new float[]{0,0,0};
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.indicator, this, true);
        txt1 = (TextView)findViewById(R.id.txt1);
        txt2 = (TextView)findViewById(R.id.txt2);
        txt3 = (TextView)findViewById(R.id.txt3);
        SensorManager mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);


        List<Sensor> list = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        final SensorEventListener SensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event)
            {
                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
                    System.arraycopy(event.values,0,accelerometerValues,0, event.values.length);
                if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
                    System.arraycopy(event.values,0,magneticValues,0, event.values.length);
                updateOrientation(calculateOrientation());
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i){}
        };


        mSensorManager.registerListener(SensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(SensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_UI);


    }

    private float[] calculateOrientation()
    {

        float[] values = new float[3];
        float[] R = new float[16];
        float[] outR = new float[16];
        SensorManager.getRotationMatrix(R, null, accelerometerValues,
                magneticValues);
        SensorManager.remapCoordinateSystem(R,
                SensorManager.AXIS_X,
                SensorManager.AXIS_Z,
                outR);
        SensorManager.getOrientation(outR, values);

        values[0] = (float) Math.toDegrees(values[0]);
        values[1] = (float) Math.toDegrees(values[1]);
        values[2] = (float) Math.toDegrees(values[2]);

        return  values;
    }

    private void updateOrientation(float[] values)
    {
        txt1.setText("x: " + Math.round(values[0]));
        txt2.setText("x: " + Math.round(values[1]));
        txt3.setText("x: " + Math.round(values[2]));
    }
}
