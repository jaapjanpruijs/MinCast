package nl.mobilecoder.mincast;

/**
 * Created by jaap on 22/12/2017.
 */

import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.google.android.gms.cast.CastPresentation;
import com.google.android.gms.cast.CastRemoteDisplayLocalService;

/**
 * Service to keep the remote display running even when the app goes into the background
 */
public class PresentationService extends CastRemoteDisplayLocalService {

    private static final String TAG = "PresentationService";

    private CastPresentation mPresentation;

    @Override
    public void onCreatePresentation(Display display) {
        dismissPresentation();
        mPresentation = new HelloWorldPresentation(this, display);

        try {
            mPresentation.show();
        } catch (WindowManager.InvalidDisplayException ex) {
            Log.e(TAG, "Unable to show presentation, display was removed.", ex);
            dismissPresentation();
        }
    }

    @Override
    public void onDismissPresentation() {
        dismissPresentation();
    }

    private void dismissPresentation() {
        if (mPresentation != null) {
            mPresentation.dismiss();
            mPresentation = null;
        }
    }
}