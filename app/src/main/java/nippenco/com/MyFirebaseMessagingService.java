package nippenco.com;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import nippenco.com.api_model.CloudMessage;

/**
 * Created by aishwarydhare on 04/02/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private String TAG = "EMR_FCM_LOG";
    JSONObject notiff_json_obj;
    private CloudMessage cloudMessage;
    static NotificationInterface notificationInterface;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            if(remoteMessage.getData() != null){
                JSONObject temp_json = new JSONObject(remoteMessage.getData());
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(temp_json.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(jsonObject == null){
                    return;
                }
                CloudMessage temp_cloudMessage = new CloudMessage(
                        jsonObject.optString("alarm_name"),
                        jsonObject.optInt("id"),
                        jsonObject.optDouble("feed_value"),
                        jsonObject.optString("device_name"),
                        jsonObject.optInt("device_id"),
                        jsonObject.optDouble("condition_value"),
                        jsonObject.optString("created_at"),
                        jsonObject.optString("description"),
                        jsonObject.optString("condition_name")
                );
                this.cloudMessage = temp_cloudMessage;
                notiff_json_obj = jsonObject;
                showNotification(cloudMessage);
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message CloudMessage Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    // method for building notification

    private void showNotification(CloudMessage cloudMessage) {
        int notiff_icon = R.mipmap.ic_launcher;

        if(MainActivity.isAlive){
            notificationInterface.showNotification(cloudMessage);
            return;
        }

        Intent intent = new Intent(getApplicationContext(), SplashActivity.class).putExtra("notification", notiff_json_obj.toString());
        try {
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    getApplicationContext(),
                    cloudMessage.id,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

            // build our notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());

            // create notification
            Notification mNotification = builder.setSmallIcon(notiff_icon)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setContentTitle("Alert !!!")
                    .setContentText(cloudMessage.description)
                    .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), notiff_icon))
                    .build();

            // notification flag for cancel notification automatically
            mNotification.flags |= Notification.FLAG_AUTO_CANCEL;

            // create notification manager to notify user
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

            // notify the user
            notificationManager.notify(cloudMessage.id, mNotification);
            Log.d("FCM_CUSTOM", "done showing notification");
            playNotificationSound();
            // call this method for build the notification in WittyFeedMyFirebaseMessagingService class
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Playing notification sound
    private void playNotificationSound() {
        try {
            final Uri notiffSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notiffSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Clears notification tray messages
    private static void clearNotifications(Context context) {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.cancelAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setNotificationInterface(NotificationInterface para_notificationInterface) {
        notificationInterface = para_notificationInterface;
    }

}
