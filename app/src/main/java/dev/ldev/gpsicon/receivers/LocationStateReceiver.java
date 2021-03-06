package dev.ldev.gpsicon.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

import dev.ldev.gpsicon.Factory;
import dev.ldev.gpsicon.notify.GpsStatusNotifier;
import dev.ldev.gpsicon.services.GpsObserveService;


public class LocationStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            LocationManager locationManager = (LocationManager) context
                    .getSystemService(Context.LOCATION_SERVICE);

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                context.startService(new Intent(context, GpsObserveService.class));
            } else {
                context.stopService(new Intent(context, GpsObserveService.class));

                //close pending icon if proccess was DIRTY killed
                GpsStatusNotifier notifier = Factory.getInstance().getGpsStatusNotifier(context);
                notifier.hide();
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(),
                    Toast.LENGTH_LONG).show();
            context.stopService(new Intent(context, GpsObserveService.class));
        }
    }


}
