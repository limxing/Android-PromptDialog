package me.leefeng.promptlibrary;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;


/**
 * Created by limxing on 2017/5/7.
 * https://www.github.com/limxing
 */

public class PromptDialog {
    private final String TAG = "PromptDialog";

    private InputMethodManager inputmanger;
    private Animation outAnim;
    private Animation inAnim;
    private PromptView promptView;
    private ViewGroup decorView;
    private ValueAnimator dissmissAnim;
    private boolean dissmissAnimCancle;
    private boolean outAnimRunning;
    private long viewAnimDuration = 200;
    private boolean isShowing;

    public void setViewAnimDuration(long viewAnimDuration) {
        this.viewAnimDuration = viewAnimDuration;
    }

    public void onDetach() {
        isShowing = false;
    }


    public PromptDialog(Activity context) {
        this(Builder.getDefaultBuilder(), context);
    }

   public  PromptDialog(Builder builder, Activity context) {
        decorView = (ViewGroup) context.getWindow().getDecorView().findViewById(android.R.id.content);

        promptView = new PromptView(context, builder, this);
       initAnim(context.getResources().getDisplayMetrics().widthPixels, context.getResources().getDisplayMetrics().heightPixels);
        inputmanger = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    private void initAnim(int widthPixels, int heightPixels) {

        AnimationSet inAnim = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(2, 1f, 2,
                1f, widthPixels * 0.5f, heightPixels * 0.45f);
        inAnim.addAnimation(scaleAnimation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        inAnim.addAnimation(alphaAnimation);
        inAnim.setDuration(viewAnimDuration);
        inAnim.setInterpolator(new DecelerateInterpolator());
        this.inAnim = inAnim;
        AnimationSet outAnim = new AnimationSet(true);
        scaleAnimation = new ScaleAnimation(1, 2, 1,
                2, widthPixels * 0.5f, heightPixels * 0.45f);
        alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(200);
        outAnim.addAnimation(scaleAnimation);
        outAnim.addAnimation(alphaAnimation);
        outAnim.setDuration(300);
        outAnim.setInterpolator(new AccelerateInterpolator());
        this.outAnim = outAnim;

    }

    public void setOutAnim(Animation outAnim) {
        this.outAnim = outAnim;
    }

    public void setInAnim(Animation inAnim) {
        this.inAnim = inAnim;
    }

    /**
     * 立刻关闭窗口
     */
    public void dismissImmediately() {
        if (isShowing) {
            decorView.removeView(promptView);
            isShowing = false;
        }
    }

    /**
     * close,程序调用的关闭，非LOADING状态
     */
    public void dismiss() {
        if (isShowing && !outAnimRunning) {
            if (promptView.getBuilder().withAnim) {
//                outAnim.setStartOffset(delayTime);
                promptView.startAnimation(outAnim);
                outAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        outAnimRunning = true;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        decorView.removeView(promptView);
                        outAnimRunning = false;
                        isShowing = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else {
                dismissImmediately();
            }

        }

    }

    public void showError(String msg) {
        showSomthing(R.drawable.ic_prompt_error, PromptView.PROMPT_ERROR, msg);

    }

    public void showInfo(String msg) {
        showSomthing(R.drawable.ic_prompt_info, PromptView.PROMPT_INFO, msg);
    }

    public void showWarn(String msg) {
        showSomthing(R.drawable.ic_prompt_warn, PromptView.PROMPT_WARN, msg);
    }

    public void showSuccess(String msg) {

        showSomthing(R.drawable.ic_prompt_success, PromptView.PROMPT_SUCCESS, msg);
    }

    /**
     * 展示自定义的状态提示框
     *
     * @param icon
     * @param msg
     */
    public void showCustom(int icon, String msg) {
//        Builder builder = Builder.getDefaultBuilder();
//        builder.icon(icon);
//        builder.text(msg);

        showSomthing(icon, PromptView.PROMPT_CUSTOM, msg);
    }

    private void showSomthing(int icon, int promptError, String msg) {
        Builder builder = Builder.getDefaultBuilder();
        builder.text(msg);
        builder.icon(icon);
        closeInput();
        checkLoadView();
        if (isShowing) {
            promptView.setBuilder(builder);
            promptView.showSomthing(promptError);
            dissmissAni(false);
        }
    }

    public void showWarnAlert(String text, PromptButton button) {
        showAlert(text, button);
    }

    public void showWarnAlert(String text, PromptButton button1, PromptButton button2) {
        showAlert(text, button1, button2);
    }

    private void showAlert(String text, PromptButton... button) {
        Builder builder = Builder.getAlertDefaultBuilder();
        builder.text(text);
        builder.icon(R.drawable.ic_prompt_alert_warn);
        closeInput();
        checkLoadView();
        promptView.setBuilder(builder);
        promptView.showSomthingAlert(button);
        dissmissAni(true);

    }

    public void showLoading(String msg) {
        Builder builder = Builder.getDefaultBuilder();
        builder.icon(R.drawable.ic_prompt_loading);
        builder.text(msg);
        promptView.setBuilder(builder);
        closeInput();
        checkLoadView();
        promptView.showLoading();
        dissmissAni(true);

    }

    /**
     * 检查 loadview是否咋屏幕中，没有就添加
     */
    private void checkLoadView() {
        if (!isShowing) {
            decorView.addView(promptView);
            isShowing = true;
            if (promptView.getBuilder().withAnim)
                promptView.startAnimation(inAnim);
        }
    }

    /**
     * 消失停留一秒的动画,如正在执行动画 停止
     */
    private void dissmissAni(boolean isCancle) {
        if (dissmissAnim == null) {
            dissmissAnim = ValueAnimator.ofInt(0, 1);
            dissmissAnim.setDuration(promptView.getBuilder().stayDuration);
            dissmissAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (!dissmissAnimCancle) {
                        dismiss();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        } else if (dissmissAnim.isRunning()) {
            dissmissAnimCancle = true;
            dissmissAnim.end();

        }
        if (!isCancle) {
            dissmissAnim.start();
            dissmissAnimCancle = false;
        }
    }

    protected void closeInput() {
        if (decorView != null) {
            inputmanger.hideSoftInputFromWindow(decorView.getWindowToken(), 0);

        }
//        window.closeAllPanels();
    }

    public Builder getDefaultBuilder() {
        return Builder.getDefaultBuilder();
    }

    public Builder getAlertDefaultBuilder() {
        return Builder.getAlertDefaultBuilder();
    }

    public boolean onBackPressed() {
        if (promptView.getCurrentType() == PromptView.PROMPT_LOADING) {
            return false;
        }
        if (isShowing && promptView.getCurrentType() == PromptView.PROMPT_ALERT_WARN) {
            dismiss();
            return false;
        } else {
            return true;
        }
    }
}
