package nl.mobilecoder.mincast;

import android.content.Context;
import android.os.Bundle;
import android.view.Display;

import com.google.android.gms.cast.CastPresentation;

/**
 * Created by jaap on 22/12/2017.
 */

public class HelloWorldPresentation extends CastPresentation {
    public HelloWorldPresentation(Context context, Display display) {
        super(context, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_layout);
    }
}
