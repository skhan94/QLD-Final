package com.fsd.logodesign;


import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.e("Testing", "From: " + remoteMessage.getFrom());
        Log.e("Testing", "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Log.e("Testing", "Payload: " + remoteMessage.getData().toString());

     /*   sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));*/
     //   sendNotification(remoteMessage.getData().get("title"), remoteMessage.getNotification().getBody(),remoteMessage.getData().toString());
      Log.e("Testing", "TYPE " + remoteMessage.getData().get("type"));
        Log.e("Testing","PKG " + remoteMessage.getData().get("pkg"));
        Log.e("Testing","TITLE " + remoteMessage.getData().get("title"));
        Log.e("Testing","CONTENT " + remoteMessage.getData().get("content"));
        if(remoteMessage.getData().get("type").equals("1")) {
            showAppsDialog(SVG_View_Pager.activity, remoteMessage.getData().get("pkg"), remoteMessage.getData().get("title"), remoteMessage.getData().get("content"));
        }
    }
    public void showAppsDialog(Context context, final String pkg, final String title, final String text) {
        SVG_View_Pager.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = new Dialog(SVG_View_Pager.activity, R.style.Dialog);
                dialog.setContentView(R.layout.dialog_apps);

                TextView notification_title = (TextView) dialog.findViewById(R.id.notification_title);
                notification_title.setText(title);

                TextView notification_Text = (TextView) dialog.findViewById(R.id.notification_Text);
                notification_Text.setText(text);


                TextView notification_ok = (TextView) dialog.findViewById(R.id.notification_ok);
                notification_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + pkg));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } catch (android.content.ActivityNotFoundException anfe) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + pkg));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + pkg)));
                        }
                        if (dialog.isShowing())
                            dialog.dismiss();
                    }
                });

                TextView notification_cancel = (TextView) dialog.findViewById(R.id.notification_cancel);
                notification_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dialog.isShowing())
                            dialog.dismiss();
                    }
                });

                if (!dialog.isShowing())
                    dialog.show();
            }
        });


    }
    private void sendNotification(String messageTitle,String messageBody, String messageDetail) {
        Intent intent = new Intent(this, Splash_Screen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0 /* request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long[] pattern = {500,500,500,500,500};

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setLights(Color.BLUE,1,1)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
