# Android-PromptDialog
通知窗口，正在加载中，确认对话框

<img src="screen1.gif"/>

####1、添加依赖

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}


```

```
dependencies {
    ...
    compile project(':promptlibrary')
}

```
####2、创建对象及使用方法
```
promptDialog = new PromptDialog(this);
promptDialog.showLoading("正在登录");
/**
promptDialog.showSuccess("登陆成功");
promptDialog.showError("登录失败");
promptDialog.showWarn("注意");
promptDialog.showInfo("成功了");
promptDialog.showCustom(R.mipmap.ic_launcher, "自定义图标的");
promptDialog.dismiss();
promptDialog.dismissImmediately();


        confirm.setTextColor(Color.parseColor("#DAA520"));
 //按钮的定义
        PromptButton confirm = new PromptButton("确定", new PromptButtonListener() {
            @Override
            public void onClick(PromptButton button) {
                Toast.makeText(MainActivity.this, button.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        confirm.setFocusBacColor(Color.parseColor("#FAFAD2"));
        promptDialog.showWarnAlert("你确定要退出登录？", new PromptButton("取消", new PromptButtonListener() {
                    @Override
                    public void onClick(PromptButton button) {
                        Toast.makeText(MainActivity.this, button.getText(), Toast.LENGTH_SHORT).show();
                    }
          }), confirm);
**/
```