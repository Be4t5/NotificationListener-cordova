package net.coconauts.notificationListener;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.os.Parcelable;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class NotificationService extends AccessibilityService {


  public static final String TAG = "volumeMaster";

  @Override
  public void onAccessibilityEvent(AccessibilityEvent event)
  {
    String pack = event.getPackageName().toString();
    String text = event.getText().toString();
    Log.v(TAG, "***** onAccessibilityEvent");
    NotificationCommands.notifyListener("Notifica");
  }

  @Override
  public void onInterrupt()
  {
    Log.v(TAG, "***** onInterrupt");
  }

  @Override
  public void onServiceConnected()
  {
    Log.v(TAG, "***** onServiceConnected");

    /*
    Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
    startActivityForResult(intent, 0);
    */

    AccessibilityServiceInfo info = new AccessibilityServiceInfo();
    info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
    info.notificationTimeout = 100;
    info.feedbackType = AccessibilityEvent.TYPES_ALL_MASK;
    setServiceInfo(info);

  }
}
