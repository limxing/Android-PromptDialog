package me.leefeng.promptlibrary;

import android.graphics.Color;

/**
 * Created by FengTing on 2017/5/8.
 * https://www.github.com/limxing
 */

public class Builder {
    private static Builder defaultBuilder;
    private static Builder alertDefaultBuilder;
    int backColor = Color.BLACK;
    int backAlpha = 90;
    int textColor = Color.WHITE;
    float textSize = 14;
    float padding = 15;
    float round = 8;
    int roundColor = Color.BLACK;
    int roundAlpha = 120;
    boolean touchAble = false;
    boolean withAnim = true;//
    long stayDuration = 1000;
    boolean cancleAble;
    int icon;
    String text;
    long loadingDuration;

    int sheetPressAlph=15;
    int sheetCellHeight=48;
    int sheetCellPad=13;

    public Builder sheetCellPad(int pad){
        this.sheetCellPad=pad;
        return this;
    }

    public Builder sheetCellHeight(int height){
        this.sheetCellHeight=height;
        return this;
    }


    public Builder sheetPressAlph(int alpha){
        this.sheetPressAlph=alpha;
        return this;
    }

    public Builder backColor(int backColor) {
        this.backColor = backColor;
        return this;
    }

    public Builder backAlpha(int backAlpha) {
        this.backAlpha = backAlpha;
        return this;
    }

    public Builder textColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public Builder textSize(float textSize) {
        this.textSize = textSize;
        return this;
    }

    public Builder padding(float padding) {
        this.padding = padding;
        return this;
    }

    public Builder round(float round) {
        this.round = round;
        return this;
    }

    public Builder roundColor(int roundColor) {
        this.roundColor = roundColor;
        return this;
    }

    public Builder roundAlpha(int roundAlpha) {
        this.roundAlpha = roundAlpha;
        return this;
    }

    public Builder touchAble(boolean touchAble) {
        this.touchAble = touchAble;
        return this;
    }

    public Builder withAnim(boolean withAnim) {
        this.withAnim = withAnim;
        return this;
    }

    public Builder stayDuration(long time) {
        this.stayDuration = time;
        return this;
    }

    public Builder cancleAble(boolean time) {
        this.cancleAble = time;
        return this;
    }

    public Builder() {
    }

    /**
     * @return
     */
    static Builder getDefaultBuilder() {
        if (defaultBuilder == null)
            defaultBuilder = new Builder();
        return defaultBuilder;
    }

    static Builder getAlertDefaultBuilder() {
        if (alertDefaultBuilder == null)
            alertDefaultBuilder = new Builder().roundColor(Color.WHITE).roundAlpha(255).
                    textColor(Color.GRAY).textSize(15).cancleAble(true);
        return alertDefaultBuilder;
    }

    public Builder icon(int icon) {
        this.icon = icon;
        return this;
    }

    public Builder text(String msg) {
        this.text = msg;
        return this;
    }

    public Builder loadingDuration(long duration) {
        this.loadingDuration = duration;
        return this;
    }
}
