package app.myapp.findlocation;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.Random;

import app.myapp.findlocation.Model.ListItem;

public class MyReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String ACTION_SMS_NEW = "android.provider.Telephony.SMS_DELIVER";

    String senderNum;
    String message;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.


        String action = intent.getAction();
        if(action.equals(SMS_RECEIVED)){
        final Bundle bundle = intent.getExtras();


        if (bundle != null) {

            final Object[] pdusObj = (Object[]) bundle.get("pdus");

            SmsMessage[] messages = new SmsMessage[pdusObj.length];

            for (int i = 0; i < messages.length; i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String format = bundle.getString("format");
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i], format);
                } else {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                }

                 senderNum = messages[i].getOriginatingAddress();
                 message = messages[i].getMessageBody();



                ListItem listItem = new Database(context).getOneData(senderNum.substring(senderNum.length()-5,senderNum.length()));



                  if (listItem != null) {


                    if (message.equals("?&I_need_location_you?&")) {

                        ShowNotificationn(listItem.name,context,message);
                        Location location=myLocation(context);
                        if (location!=null) {






                            SmsManager smsManagersend = SmsManager.getDefault();
                            smsManagersend.sendTextMessage(listItem.phone, null, "*$%"+location.getLatitude()+"%"+location.getLongitude()+"%$*", null, null);


                            Toast.makeText(context,context.getString(R.string.done_send), Toast.LENGTH_LONG).show();

                            return;

                        }else {
                            Toast.makeText(context,context.getString(R.string.error_gps),Toast.LENGTH_LONG).show();
                        }


                    }else if (message.charAt(0)=='*' && message.charAt(1)=='$' && message.charAt(message.length()-1)=='*'){

//
                        String[] mm=message.split("%");
                        Database database=new Database(context);

                        database.upData(new ListItem(listItem.id,mm[1],mm[2]));
                        ShowNotificationn(listItem.name, context,context.getString(R.string.done_add));


                    }

                }else {
                      Toast.makeText(context,"empaty",Toast.LENGTH_SHORT).show();
                  }
            }



        }else {
            Toast.makeText(context,"null",Toast.LENGTH_SHORT).show();
        }
//            throw new UnsupportedOperationException("Not yet implemented");
        }else {

        }
    }












    public void ShowNotificationn(String name,Context context,String content)
    {


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){


            NotificationChannel channel=new NotificationChannel("chanel_id","name",NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);

            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"chanel_id")
                .setContentTitle(name)
                .setContentText(content)
                .setVibrate(new long[]{500, 1000, 500, 1000, 500})
                .setSmallIcon(R.drawable.gpson)
                .setDefaults(Notification.DEFAULT_ALL);



        Intent intent = new Intent(context, MainActivity.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addNextIntent(intent);
        taskStackBuilder.addParentStack(List_Main.class);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.addAction(R.drawable.ic_location, context.getString(R.string.show), pendingIntent);



        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(new Random().nextInt(),builder.build());




    }








    public Location myLocation(Context context){


        try {

            LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {

                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);

                criteria.setCostAllowed(false);

                String provider = locationManager.getBestProvider(criteria, false);

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                }
                Location lastKnownLocationGPS = locationManager.getLastKnownLocation(provider); //(LocationManager.GPS_PROVIDER);
                if (lastKnownLocationGPS != null) {
                    return lastKnownLocationGPS;
                } else {
                    Location loc =  locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);


                    return loc;
                }
            } else {
                return null;
            }
        }
        catch (Exception ex){return null;}
    }










    }






