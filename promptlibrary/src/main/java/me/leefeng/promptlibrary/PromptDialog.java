package me.leefeng.promptlibrary;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
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
    public static long viewAnimDuration = 300;
    private boolean isShowing;

    /**
     * 设置进入 进出动画持续的事件默认300毫秒
     *
     * @param viewAnimDuration 毫秒
     */
    public void setViewAnimDuration(long viewAnimDuration) {
        this.viewAnimDuration = viewAnimDuration;
    }

    public long getViewAnimDuration() {
        return viewAnimDuration;
    }

    public void onDetach() {
        isShowing = false;
    }


    public PromptDialog(Activity context) {
        this(Builder.getDefaultBuilder(), context);
    }

    public PromptDialog(Builder builder, Activity context) {
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
        outAnim.setDuration(viewAnimDuration);
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
        if (isShowing && !outAnimRunning) {
            decorView.removeView(promptView);
            isShowing = false;
        }
    }

    /**
     * close
     */
    public void dismiss() {

        if (isShowing && !outAnimRunning) {
            if (promptView.getBuilder().withAnim && outAnim != null) {
//                outAnim.setStartOffset(delayTime);
                if (promptView.getCurrentType() == PromptView.PROMPT_LOADING) {
                    outAnim.setStartOffset(promptView.getBuilder().loadingDuration);
                } else {
                    outAnim.setStartOffset(0);
                }
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
        showError(msg, true);

    }

    public void showError(String msg, boolean withAnim) {
        showSomthing(R.drawable.ic_prompt_error, PromptView.PROMPT_ERROR, msg, withAnim);

    }

    public void showInfo(String msg) {
        showInfo(msg, true);
    }

    public void showInfo(String msg, boolean withAnim) {
        showSomthing(R.drawable.ic_prompt_info, PromptView.PROMPT_INFO, msg, withAnim);
    }

    public void showWarn(String msg) {
        showWarn(msg, true);
    }

    public void showWarn(String msg, boolean withAnim) {
        showSomthing(R.drawable.ic_prompt_warn, PromptView.PROMPT_WARN, msg, withAnim);
    }

    public void showSuccess(String msg) {
        showSuccess(msg, true);
    }

    public void showSuccess(String msg, boolean withAnim) {
        showSomthing(R.drawable.ic_prompt_success, PromptView.PROMPT_SUCCESS, msg, withAnim);
    }

    public void showSuccessDelay(final String msg, long delay) {
        decorView.postDelayed(new Runnable() {
            @Override
            public void run() {
                showSuccess( msg);
            }
        },delay);

    }

    /**
     * show custome dialog
     *
     * @param icon
     * @param msg
     */
    public void showCustom(int icon, String msg) {
        showCustom(icon, msg, true);
    }

    public void showCustom(int icon, String msg, boolean withAnim) {
        showSomthing(icon, PromptView.PROMPT_CUSTOM, msg, withAnim);
    }

    private void showSomthing(int icon, int promptError, String msg, boolean withAnim) {
        Builder builder = Builder.getDefaultBuilder();
        builder.text(msg);
        builder.icon(icon);
        closeInput();
        checkLoadView(withAnim);
        if (isShowing) {
            promptView.setBuilder(builder);
            promptView.showSomthing(promptError);
            dissmissAni(false);
        }
    }

    public void showWarnAlert(String text, PromptButton button) {
        showWarnAlert(text, button, false);
    }

    public void showWarnAlert(String text, PromptButton button, boolean withAnim) {
        showAlert(text, withAnim, button);
    }

    /**
     * 展示警告对话框
     *
     * @param text
     * @param button1
     * @param button2
     * @param withAnim 是否动画进入
     */
    public void showWarnAlert(String text, PromptButton button1, PromptButton button2, boolean withAnim) {
        showAlert(text, withAnim, button1, button2);
    }

    public void showWarnAlert(String text, PromptButton button1, PromptButton button2) {
        showWarnAlert(text, button1, button2, true);
    }

    private void showAlert(String text, boolean withAnim, PromptButton... button) {
        Builder builder = Builder.getAlertDefaultBuilder();
        builder.text(text);
        builder.icon(R.drawable.ic_prompt_alert_warn);
        closeInput();
        checkLoadView(withAnim);
        promptView.setBuilder(builder);
        promptView.showSomthingAlert(button);
        dissmissAni(true);

    }

    /**
     * 展示loading
     *
     * @param msg      信息
     * @param withAnim 是否动画进入
     */
    public void showLoading(String msg, boolean withAnim) {
        Builder builder = Builder.getDefaultBuilder();
        builder.icon(R.drawable.ic_prompt_loading);
        builder.text(msg);
        promptView.setBuilder(builder);
        closeInput();
        checkLoadView(withAnim);
        promptView.showLoading();
        dissmissAni(true);
    }

    public void showLoading(String msg) {
        showLoading(msg, true);

    }

    /**
     * promptview isshowing
     *
     * @param withAnim
     */
    private void checkLoadView(boolean withAnim) {
        if (!isShowing) {
            decorView.addView(promptView);
            isShowing = true;
            if (promptView.getBuilder().withAnim && inAnim != null && withAnim)
                promptView.startAnimation(inAnim);
        }
    }

    /**
     * dismiss dialog and start animation
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

    /**
     * 获取提示框的配置类
     *
     * @return
     */
    public Builder getDefaultBuilder() {
        return Builder.getDefaultBuilder();
    }

    /**
     * 获取对话框的配置类
     *
     * @return
     */
    public Builder getAlertDefaultBuilder() {
        return Builder.getAlertDefaultBuilder();
    }

    /**
     * 处理返回键，需要用户自行调用
     *
     * @return
     */
    public boolean onBackPressed() {
        if (isShowing && promptView.getCurrentType() == PromptView.PROMPT_LOADING) {
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
