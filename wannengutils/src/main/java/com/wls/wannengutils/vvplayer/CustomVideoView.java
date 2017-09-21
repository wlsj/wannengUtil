package com.wls.wannengutils.vvplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.VideoView;


public class CustomVideoView extends VideoView implements View.OnTouchListener{

    private float lastX;
    private float lastY;
    private int thresold = 30;
    Context mContext;
    private StateListener mStateListener;

    public interface StateListener{
        void changeVolumn(float detlaY);
        void changeBrightness(float detlaX);
        void hideHint();
    }

    public void setStateListener(StateListener stateListener) {
        this.mStateListener = stateListener;
    }

    public CustomVideoView(Context context) {
        this(context,null);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(1920,widthMeasureSpec);
        int height = getDefaultSize(1080,heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float detlaX = event.getX() - lastX;
                float detlaY = event.getY() - lastY;
                if(Math.abs(detlaX) > thresold && Math.abs(detlaY) < thresold) {
                    mStateListener.changeBrightness(detlaX);
                }
                if(Math.abs(detlaX) < thresold && Math.abs(detlaY) > thresold) {
                    mStateListener.changeVolumn(detlaY);
                }

                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                mStateListener.hideHint();
                break;
        }
        return true;
    }
}
