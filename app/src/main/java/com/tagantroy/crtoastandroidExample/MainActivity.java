package com.tagantroy.crtoastandroidExample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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
import com.tagantroy.crtoast.CRToastManager;

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
    Switch statusBarVisibleSwitch;
    Switch insideActionBarSwitch;


    private Gravity from = Gravity.Top;
    private Gravity to = Gravity.Top;

    enum Gravity {
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
        statusBarVisibleSwitch = (Switch) findViewById(R.id.statusBarVisibleSwitch);
        insideActionBarSwitch = (Switch) findViewById(R.id.insideActionBarSwitch);
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
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
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
        CRToastManager.dismiss();
    }

    private void showToast() {
//        TextView notificationTextView = new TextView(this);
//        notificationTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        notificationTextView.setTextColor(ContextCompat.getColor(this,android.R.color.white));
//        //notificationTextView.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Asap-Bold.otf"));
//        notificationTextView.setTextSize((int)getResources().getDimension(R.dimen.new_message_notification_font_size));
//        notificationTextView.setText("Test");
//        notificationTextView.setBackgroundColor(ContextCompat.getColor(this,R.color.new_message_notification_background_color));
//        notificationTextView.setGravity(android.view.Gravity.CENTER);
//
//        CRToast.Builder builder = new CRToast.Builder(this);
//        builder.animationStyle(getAnimationStyle())
//                .notificationMessage(notificationEditText.getText().toString())
//                .subtitleText(subtitleEditText.getText().toString())
//                //.duration(currentDurationValue*1000)
//                .backButtonEnabled(true)
//                .dismissWithTap(dismissSwitch.isChecked())
//                .customHeight((int)getResources().getDimension(R.dimen.new_message_notification_height))
//                .customView(notificationTextView)
//                .onTapped(new CRToast.ICRToast() {
//                    @Override
//                    public boolean onTapped() {
//                        Log.v("TESt","Dismissed");
//                        return true;
//                    }
//                });
//
//        if(imageSwitch.isChecked()){
//            builder.image(getResources().getDrawable(R.drawable.ic_launcher));
//        }
//        crToast = builder.build();
//        CRToastManager.show(crToast);

            try {
                CRToast.Builder builder = new CRToast.Builder(this);
                builder.animationStyle(AnimationStyle.TopToTop)
                        .notificationMessage("You have a new message!")
                        .backgroundColor(ContextCompat.getColor(this, R.color.new_message_notification_background_color))
                        .customHeight((int) this.getResources().getDimension(R.dimen.new_message_notification_height))
                        .notificationMessageColor(Color.WHITE)
                        .notificationLayoutGravity(android.view.Gravity.CENTER)
                        .notificationMessageGravity(android.view.Gravity.CENTER)
                        .onTapped(new CRToast.ICRToast() {
                            @Override
                            public boolean onTapped() {
                                Log.v("TESt","Dismissed");
                                return true;
                            }
                        });
                CRToast crToast = builder.build();
                CRToastManager.show(crToast);
            } catch (Exception e) {
                Log.d(MainActivity.class.getSimpleName(), "Exception showing notification: " + e.getMessage());
            }
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
                from = Gravity.Bottom;
                break;
            case R.id.fromLeftButton:
                from = Gravity.Left;
                break;
            case R.id.fromRightButton:
                from = Gravity.Right;
                break;
            case R.id.fromTopButton:
                from = Gravity.Top;
                break;
            case R.id.toBottomButton:
                to = Gravity.Bottom;
                break;
            case R.id.toLeftButton:
                to = Gravity.Left;
                break;
            case R.id.toRightButton:
                to = Gravity.Right;
                break;
            case R.id.toTopButton:
                to = Gravity.Top;
                break;
        }
    }
}
