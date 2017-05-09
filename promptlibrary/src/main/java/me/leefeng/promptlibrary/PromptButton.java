package me.leefeng.promptlibrary;

import android.graphics.Color;
import android.graphics.RectF;

/**
 * Created by FengTing on 2017/5/8.
 */

public class PromptButton {
    private String text = "确定";
    private boolean focus;
    private int textColor = Color.BLACK;
    private float textSize = 18;
    private RectF rect;
    private PromptButtonListener listener;
    private int focusBacColor= Color.parseColor("#DCDCDC");

    public PromptButton(String text, PromptButtonListener listener) {
        this.text = text;
        this.listener = listener;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextColor() {
        return textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTouchRect(RectF rectF) {
        this.rect = rectF;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public RectF getRect() {
        return rect;
    }

    public void setRect(RectF rect) {
        this.rect = rect;
    }

    public PromptButtonListener getListener() {
        return listener;
    }

    public void setListener(PromptButtonListener listener) {
        this.listener = listener;
    }

    public int getFocusBacColor() {
        return focusBacColor;
    }

    public void setFocusBacColor(int focusBacColor) {
        this.focusBacColor = focusBacColor;
    }
}
