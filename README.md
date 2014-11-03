CRToastAndroid
==============


CRToastAndroid is a library that allows you to easily create notifications that appear on top.
CRToastAndroid is a port for original CRToast for Android(IOS https://github.com/cruffenach/CRToast)



#### Example

```	java
CRToast.Builder builder = new CRToast.Builder(this)
  .animationStyle(getAnimationStyle())
  .notificationMessage("Notification Message")
  .subtitleText("Subtitle")
  .duration(2000)
  .dismissWithTap(true)
  .image(getResources().getDrawable(R.drawable.ic_launcher));

crToast = builder.build();
crToast.show();
```
