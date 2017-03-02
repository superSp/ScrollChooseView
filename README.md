# ScrollChooseView
横向滑动选择view，类似ViewPager+clipChildren实现的可以同时显示3个页面的View。

####图纸

![图片.png](http://upload-images.jianshu.io/upload_images/1168278-53a7e6ea6843d815.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


#####使用viewpager的效果图
![ScreenGif.gif](https://github.com/superSp/ScrollChooseView/blob/master/gif1.gif)

#####自定义View的效果图
![ScreenGif.gif](https://github.com/superSp/ScrollChooseView/blob/master/gif1.gif2)

###简书地址
[点击这里](http://www.jianshu.com/p/49b14d2574b1)
#使用
    https://github.com/superSp/ScrollChooseView/edit/master/README.md
````
 String titles[] = new String[]{"早餐前", "早餐后", "午餐前", "午餐后", "晚餐前", "晚餐后", "睡前"};
 private int picIds[] = new int[]{
            R.mipmap.time_bg_breakfastbefore, R.mipmap.time_bg_breakfastafter,
            R.mipmap.time_bg_lunchbefore, R.mipmap.time_bg_lunchafter,
            R.mipmap.time_bg_dinnerbefor, R.mipmap.time_bg_dinnerafter,
            R.mipmap.time_bg_sleep
    };

 scrollChooseView.setTitles(titles);
 scrollChooseView.setPicIds(picIds);
 scrollChooseView.setOnScrollEndListener(new ScrollChooseView.OnScrollEndListener() {
            @Override
            public void currentPosition(int position) {
                Log.d(TAG, "当前positin=" + position + " " + titles[position]);
            }
        });
````
