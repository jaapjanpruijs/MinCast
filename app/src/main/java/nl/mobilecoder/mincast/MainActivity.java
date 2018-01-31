package nl.mobilecoder.mincast;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.MediaRouteActionProvider;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.cast.CastRemoteDisplayLocalService;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MinCast";

    private MediaRouter mMediaRouter;
    private MediaRouteSelector mMediaRouteSelector;
    private MediaRouteActionProvider mMediaRouteActionProvider;
    private CastDevice mCastDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMediaRouteSelector = new MediaRouteSelector.Builder()
                .addControlCategory(CastMediaControlIntent.categoryForCast("667CA735"))
                .build();
        mMediaRouter = MediaRouter.getInstance(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMediaRouter.addCallback(mMediaRouteSelector, mMediaRouterCallback,
                MediaRouter.CALLBACK_FLAG_REQUEST_DISCOVERY);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMediaRouter.removeCallback(mMediaRouterCallback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem mediaRouteMenuItem = menu.findItem(R.id.media_route_menu_item);
        mMediaRouteActionProvider = (MediaRouteActionProvider) MenuItemCompat.getActionProvider(mediaRouteMenuItem);
        mMediaRouteActionProvider.setRouteSelector(mMediaRouteSelector);
        // Return true to show the menu.
        return true;
    }

    private final MediaRouter.Callback mMediaRouterCallback =
            new MediaRouter.Callback() {
                @Override
                public void onRouteAdded(MediaRouter router, MediaRouter.RouteInfo route) {
                }

                @Override
                public void onRouteRemoved(MediaRouter router, MediaRouter.RouteInfo route) {
                }

                @Override
                public void onRouteSelected(MediaRouter router, MediaRouter.RouteInfo info) {
                    Log.d(TAG, "onRouteSelected");
                    mCastDevice = CastDevice.getFromBundle(info.getExtras());
                    startCastService(mCastDevice);
                }

                @Override
                public void onRouteUnselected(MediaRouter router, MediaRouter.RouteInfo info) {
                    Log.d(TAG, "onRouteUnselected");
                    mCastDevice = null;
                }
            };

    private void startCastService(CastDevice castDevice) {
        Intent intent = new Intent(MainActivity.this,
                MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(
                MainActivity.this, 0, intent, 0);

        CastRemoteDisplayLocalService.NotificationSettings settings =
                new CastRemoteDisplayLocalService.NotificationSettings.Builder()
                        .setNotificationPendingIntent(notificationPendingIntent).build();

        CastRemoteDisplayLocalService.startService(MainActivity.this,
                PresentationService.class, "667CA735",
                castDevice, settings,
                new CastRemoteDisplayLocalService.Callbacks() {
                    @Override
                    public void onServiceCreated(CastRemoteDisplayLocalService service) {
                        Log.d(TAG, "onServiceCreated");
                    }

                    @Override
                    public void onRemoteDisplaySessionStarted(CastRemoteDisplayLocalService service) {
                        Log.d(TAG, "onRemoteDisplaySessionStarted");
                    }

                    @Override
                    public void onRemoteDisplaySessionEnded(CastRemoteDisplayLocalService service) {
                        Log.d(TAG, "onRemoteDisplaySessionEnded");
                    }

                    @Override
                    public void onRemoteDisplaySessionError(Status errorReason) {
                        Log.d(TAG, "onRemoteDisplaySessionError: " + errorReason.getStatusCode());
                    }
                });
    }
}