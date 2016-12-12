package com.example.joo.mytaste;

/**
 * Created by Joo on 2016-09-17.
 */
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.google.android.gms.maps.model.Marker;

public abstract class OnInfoWindowElemTouchListener implements OnTouchListener {
    private final View view;
    private final Drawable bgDrawableNormal;
    private final Drawable bgDrawablePressed;
    private final Handler handler = new Handler();

    private Marker marker;
    private boolean pressed = false;

    public OnInfoWindowElemTouchListener(View view, Drawable bgDrawableNormal, Drawable bgDrawablePressed) {
        this.view = view;
        this.bgDrawableNormal = bgDrawableNormal;
        this.bgDrawablePressed = bgDrawablePressed;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    @Override
    public boolean onTouch(View vv, MotionEvent event) {
        if (0 <= event.getX() && event.getX() <= view.getWidth() &&
                0 <= event.getY() && event.getY() <= view.getHeight())
        {/*
            if (event.getAction() == MotionEvent.ACTION_DOWN ){
                Intent Pass = new Intent(, AddPlaceDialogActivity.class);
                Pass.putExtra("latitude", marker.getPosition().latitude);
                Pass.putExtra("longitude", marker.getPosition().longitude);
                startActivity(Pass);
            }
            */

            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN: startPress(); break;

                // We need to delay releasing of the view a little so it shows the pressed state on the screen
                case MotionEvent.ACTION_UP: handler.postDelayed(confirmClickRunnable, 150); break;

                case MotionEvent.ACTION_CANCEL: endPress(); break;
                default: break;
            }

        }
        else {
            // If the touch goes outside of the view's area
            // (like when moving finger out of the pressed button)
            // just release the press
            endPress();
        }
        return false;
    }

    private void startPress() {
        if (!pressed) {
            pressed = true;
            handler.removeCallbacks(confirmClickRunnable);
            view.setBackground(bgDrawablePressed);
            if (marker != null)
                marker.showInfoWindow();
        }
    }

    private boolean endPress() {
        if (pressed) {
            this.pressed = false;
            handler.removeCallbacks(confirmClickRunnable);
            //view.setBackgroundColor(0xFF000000);
            view.setBackground(bgDrawableNormal);
            if (marker != null)
                marker.showInfoWindow();
            return true;
        }
        else
            return false;
    }

    private final Runnable confirmClickRunnable = new Runnable() {
        public void run() {
            if (endPress()) {
                onClickConfirmed(view, marker);
            }
        }
    };

    /**
     * This is called after a successful click
     */
    protected abstract void onClickConfirmed(View v, Marker marker);
}