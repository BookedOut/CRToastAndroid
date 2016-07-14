CRToastAndroid 
==============
rev 0.1.1

CRToastAndroid is a library that allows you to easily create notifications that appear on top. <br>
This library is implementation [CRToast](https://github.com/cruffenach/CRToast) for android 

Original project [CRToast](https://github.com/cruffenach/CRToast)

#### Example

```	java
CRToast.Builder builder = new CRToast.Builder(this);
        builder.animationStyle(getAnimationStyle())
                .notificationMessage(notificationEditText.getText().toString())
                .subtitleText(subtitleEditText.getText().toString())
                .duration(2000)
                .dismissWithTap(true)
                .insideActionBar(true);
        if(imageSwitch.isChecked()){
            builder.image(getResources().getDrawable(R.drawable.ic_launcher));
        }
        crToast = builder.build();
        CRToastManager.show(crToast);
```
#### Using customView

```	java
LinearLayout view = new LinearLayout(this);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView textView = new TextView(this);
        textView.setText("Test");
        view.setBackgroundColor(Color.BLUE);
        view.addView(textView);

        CRToast.Builder builder = new CRToast.Builder(this);
        builder.animationStyle(getAnimationStyle())
                .duration(currentDurationValue*1000)
                .dismissWithTap(dismissSwitch.isChecked())
                .customView(view)
                .customHeight(20)
                .statusBarVisible(true);
        crToast = builder.build();
        CRToastManager.show(crToast);
```
### Added Handler for onTapped

Dismiss with Tap wont work out if handler is implemented

``` java
 builder.animationStyle(getAnimationStyle())
                .notificationMessage(notificationEditText.getText().toString())
                .subtitleText(subtitleEditText.getText().toString())
                .insideActionBar(true)
                .setOnTapped(new CRToast.ICRToast(){
                        @Override
                        public boolean onTapped(){
                            return true;// to dismiss
                        }
                    });
```
