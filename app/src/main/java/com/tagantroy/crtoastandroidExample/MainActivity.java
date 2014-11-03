package com.tagantroy.crtoastandroidExample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.tagantroy.crtoast.AnimationStyle;
import com.tagantroy.crtoast.CRToast;

import info.hoang8f.android.segmented.SegmentedGroup;

public class MainActivity extends Activity implements RadioGroup.OnCheckedChangeListener {

    CRToast crToast;
    EditText notificationEditText;
    EditText subtitleEditText;
    TextView showNotification;
    TextView dismiss;
    SegmentedGroup segmentedFrom;
    SegmentedGroup segmentedTo;
    SeekBar seekBar;
    Switch imageSwitch;
    Switch dismissSwitch;


    private FROM from = FROM.Top;
    private TO to = TO.Top;

    enum FROM {
        Bottom,
        Left,
        Right,
        Top
    }

    enum TO {
        Bottom,
        Left,
        Right,
        Top
    }

    int currentDurationValue = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        segmentedFrom = (SegmentedGroup) findViewById(R.id.segmentedFrom);
        segmentedTo = (SegmentedGroup) findViewById(R.id.segmentedTo);
        segmentedFrom.setOnCheckedChangeListener(this);
        segmentedTo.setOnCheckedChangeListener(this);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        imageSwitch = (Switch) findViewById(R.id.imageSwitch);
        dismissSwitch = (Switch) findViewById(R.id.dismissSwitch);
        final TextView longTextView = (TextView) findViewById(R.id.longTextView);
        seekBar.setMax(10);
        longTextView.setText(String.valueOf(currentDurationValue) + " sec");
        seekBar.setProgress(currentDurationValue);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                currentDurationValue = i;
                longTextView.setText(i + " sec");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        notificationEditText = (EditText) findViewById(R.id.notificationEditText);
        subtitleEditText = (EditText) findViewById(R.id.subtitleEditText);


        notificationEditText.setText("Notification");
        subtitleEditText.setText("SubTitle");

        showNotification = (TextView) findViewById(R.id.showNotification);
        showNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast();
            }
        });
        dismiss = (TextView) findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissToast();
            }
        });
    }

    private void dismissToast() {
        crToast.dismiss();
    }

    private void showToast() {
        CRToast.Builder builder = new CRToast.Builder(this);
        builder.animationStyle(getAnimationStyle())
                .notificationMessage(notificationEditText.getText().toString())
                .subtitleText(subtitleEditText.getText().toString())
                .duration(currentDurationValue*1000)
                .dismissWithTap(dismissSwitch.isChecked());
        if(imageSwitch.isChecked()){
            builder.image(getResources().getDrawable(R.drawable.ic_launcher));
        }
        crToast = builder.build();
        crToast.show();

    }

    private AnimationStyle getAnimationStyle() {
        return AnimationStyle.valueOf(from.name() + "To" + to.name());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.fromBottomButton:
                from = FROM.Bottom;
                break;
            case R.id.fromLeftButton:
                from = FROM.Left;
                break;
            case R.id.fromRightButton:
                from = FROM.Right;
                break;
            case R.id.fromTopButton:
                from = FROM.Top;
                break;
            case R.id.toBottomButton:
                to = TO.Bottom;
                break;
            case R.id.toLeftButton:
                to = TO.Left;
                break;
            case R.id.toRightButton:
                to = TO.Right;
                break;
            case R.id.toTopButton:
                to = TO.Top;
                break;
        }
    }
}
