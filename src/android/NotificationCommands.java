package net.coconauts.notificationListener;

import android.content.Intent;
import android.view.Gravity;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import org.apache.cordova.PluginResult;
import android.service.notification.StatusBarNotification;
import android.os.Bundle;

public class NotificationCommands extends CordovaPlugin {

    private static final String TAG = "NotificationCommands";

    private static final String LISTEN = "listen";

    // note that webView.isPaused() is not Xwalk compatible, so tracking it poor-man style
    private boolean isPaused;

    private static CallbackContext listener;

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {

      Log.i(TAG, "Received action " + action);

      if (LISTEN.equals(action)) {
        //setListener(callbackContext);
        listener = callbackContext;

        PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
        result.setKeepCallback(true);
        callbackContext.sendPluginResult(result);

        return true;
      } else if(action.equals("init")){
        Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
        cordova.getActivity().startActivityForResult(intent, 0);
        return true;
      }else {
        callbackContext.error(TAG+". " + action + " is not a supported function.");
        return false;
      }
    }

  public static void notifyListener(String notification){
    if (listener == null) {
      Log.e(TAG, "Must define listener first. Call notificationListener.listen(success,error) first");
      return;
    }
    try  {

      PluginResult result = new PluginResult(PluginResult.Status.OK, notification);

      result.setKeepCallback(true);

      listener.sendPluginResult(result);
    } catch (Exception e){
      Log.e(TAG, "Unable to send notification "+ e);
      listener.error(TAG+". Unable to send message: "+e.getMessage());
    }
  }

    @Override
    public void onPause(boolean multitasking) {
      this.isPaused = true;
    }

    @Override
    public void onResume(boolean multitasking) {
      this.isPaused = false;
    }
}
