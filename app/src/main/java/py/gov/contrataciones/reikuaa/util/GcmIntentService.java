package py.gov.contrataciones.reikuaa.util;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import py.gov.contrataciones.reikuaa.util.GcmBroadcastReceiver;

/**
 * Servicio GCM
 *
 * Created by willtallpear on 25/03/15.
 */
public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if(!extras.isEmpty()) {
            //TODO Procesar mensajes
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
}
