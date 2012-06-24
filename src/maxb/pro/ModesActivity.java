package maxb.pro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import maxb.pro.Adapters.ModesAdapter;
import maxb.pro.Views.myButton;


public class ModesActivity extends Activity
{
    private Context mContext = null;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        mContext = this;
        setContentView(R.layout.modes);
        Gallery gallery = (Gallery)findViewById(R.id.modes_gallery);
        ModesAdapter adapter = new ModesAdapter(this);
        gallery.setAdapter(adapter);
        final myButton btn_back = (myButton)findViewById(R.id.modes_back);
        btn_back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        btn_back.startAnimation();
                        break;
                    case MotionEvent.ACTION_UP:
                        //btn_back.stopAnimation();
                        finish();
                        break;
                }
                return true;
            }
        });
        final myButton btn_info = (myButton)findViewById(R.id.modes_info);
        btn_info.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        btn_info.startAnimation();
                        break;
                    case MotionEvent.ACTION_UP:
                        // create intent
                        break;
                }
                return true;
            }
        });
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, LevelsActivity.class);
                intent.putExtra("mode", i+1);
                startActivity(intent);
            }
        });
    }
}