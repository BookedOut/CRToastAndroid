package com.tagantroy.crtoastandroidExample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tagantroy.crtoast.CRToast;

import info.hoang8f.android.segmented.SegmentedGroup;


public class MainActivity extends Activity implements RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = getLayoutInflater().inflate(R.layout.activity_main,null);
        SegmentedGroup segmentedFrom = (SegmentedGroup) view.findViewById(R.id.segmentedFrom);
        SegmentedGroup segmentedTo = (SegmentedGroup) view.findViewById(R.id.segmentedTo);
        SegmentedGroup segmentedEntranceAnimation =
                (SegmentedGroup) view.findViewById(R.id.segmentedEntranceAnimationType);
        SegmentedGroup segmentedExitAnimation =
                (SegmentedGroup) view.findViewById(R.id.segmentedExitAnimationType);
        SegmentedGroup segmentedNotificationText =
                (SegmentedGroup) view.findViewById(R.id.segmentedNotificationText);
        SegmentedGroup segmentedSubtitleText =
                (SegmentedGroup) view.findViewById(R.id.segmentedSubtitleText);
        segmentedFrom.setOnCheckedChangeListener(this);

        final CRToast crToast = new CRToast.Builder(this).build();
        crToast.show();
        TextView showNotification = (TextView) view.findViewById(R.id.showNotification);
        showNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"before show");
                crToast.show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

    }
}
