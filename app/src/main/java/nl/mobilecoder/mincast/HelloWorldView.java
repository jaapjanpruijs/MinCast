package nl.mobilecoder.mincast;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jaap on 22/12/2017.
 */

public class HelloWorldView extends View {
    private Paint mFillPaint;

    public HelloWorldView(Context context) {
        this(context, null, 0);
    }

    public HelloWorldView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HelloWorldView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFillPaint.setStyle(Paint.Style.FILL);
        mFillPaint.setColor(Color.BLACK);
        mFillPaint.setStrokeWidth(3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(0, 0, 1920, 1080, mFillPaint);
    }
}
