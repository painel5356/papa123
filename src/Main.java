package com.papa123.debug;
import android.content.Context;
import android.graphics.*;
import android.os.*;
import android.view.*;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        Looper.prepareMainLooper();
        try {
            Class<?> atClass = Class.forName("android.app.ActivityThread");
            Object at = atClass.getMethod("currentActivityThread").invoke(null);
            Context context = (Context) atClass.getMethod("getSystemContext").invoke(at);
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                2038, // TYPE_APPLICATION_OVERLAY
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT
            );

            new Handler(Looper.getMainLooper()).post(() -> {
                View overlay = new View(context) {
                    Paint p = new Paint();
                    @Override
                    protected void onDraw(Canvas canvas) {
                        super.onDraw(canvas);
                        p.setColor(Color.RED);
                        p.setStrokeWidth(4);
                        p.setStyle(Paint.Style.STROKE);
                        canvas.drawRect(400, 500, 600, 900, p); // Exemplo de Box
                        p.setStyle(Paint.Style.FILL);
                        p.setTextSize(40);
                        canvas.drawText("INIMIGO | HP: 100", 400, 480, p);
                        invalidate();
                    }
                };
                wm.addView(overlay, params);
            });
            Looper.loop();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
