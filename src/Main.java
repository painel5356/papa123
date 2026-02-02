package com.papa123.debug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import java.lang.reflect.Method;

public class Main {
    static WindowManager wm;
    static OverlayView overlay;

    public static void main(String[] args) {
        Looper.prepareMainLooper();
        try {
            // Obtendo Contexto do Sistema (Invisível para o Jogo)
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Object thread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Context context = (Context) activityThreadClass.getMethod("getSystemContext").invoke(thread);

            wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                2038, // TYPE_APPLICATION_OVERLAY
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | 
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT
            );
            params.gravity = Gravity.TOP | Gravity.LEFT;

            new Handler(Looper.getMainLooper()).post(() -> {
                overlay = new OverlayView(context);
                wm.addView(overlay, params);
            });

            Looper.loop();
        } catch (Exception e) { e.printStackTrace(); }
    }
}

// Classe que DESENHA o ESP na tela
class OverlayView extends View {
    Paint paint = new Paint();
    public OverlayView(Context context) { super(context); }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);

        // Exemplo: Desenha uma caixa (BOX) e Nome
        canvas.drawRect(100, 100, 300, 500, paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(30);
        canvas.drawText("PLAYER: PAPA_TESTE | HP: 100", 100, 90, paint);
        
        invalidate(); // Atualiza a tela constantemente
    }
                           }
