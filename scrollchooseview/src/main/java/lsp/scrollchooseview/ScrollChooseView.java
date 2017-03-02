package lsp.scrollchooseview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;


/**
 * Created by lsp on 2017/2/24.
 */

public class ScrollChooseView extends View {
    private String[] titles = null;
    private Paint paint;
    private Rect textBound;
    private int downX;

    private Scroller mScroller;
    private int lastScrollX = 0;
    private boolean isScroll = false;

    private boolean isClick = false;
    private OnScrollEndListener onScrollEndListener;
    private int picIds[] = null;

    public ScrollChooseView(Context context) {
        this(context, null);
    }

    public ScrollChooseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollChooseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        textBound = new Rect();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setTextSize(sp2px(getContext(), 10));

        mScroller = new Scroller(context);

    }

    public void setPicIds(int[] picIds) {
        this.picIds = picIds;
    }

    public void setTitles(String titles[]) {
        this.titles = titles;
        invalidate();
    }

    public void setOnScrollEndListener(OnScrollEndListener onScrollEndListener) {
        this.onScrollEndListener = onScrollEndListener;
    }


    private int getCurrentPosition() {
        int position = 1;

        if (getScrollX() > getMeasuredWidth() / 6 * 1 * -3 && getScrollX() <= getMeasuredWidth() / 6 * 1 * -1) {
            position = 0;
        } else if (getScrollX() > getMeasuredWidth() / 6 * 1 * -1 && getScrollX() <= getMeasuredWidth() / 6 * 1 * 1) {
            position = 1;
        } else if (getScrollX() > getMeasuredWidth() / 6 * 1 * 1 && getScrollX() <= getMeasuredWidth() / 6 * 1 * 3) {
            position = 2;
        } else if (getScrollX() > getMeasuredWidth() / 6 * 1 * 3 && getScrollX() <= getMeasuredWidth() / 6 * 1 * 5) {
            position = 3;
        } else if (getScrollX() > getMeasuredWidth() / 6 * 1 * 5 && getScrollX() <= getMeasuredWidth() / 6 * 1 * 7) {
            position = 4;
        } else if (getScrollX() > getMeasuredWidth() / 6 * 1 * 7 && getScrollX() <= getMeasuredWidth() / 6 * 1 * 9) {
            position = 5;
        } else if (getScrollX() > getMeasuredWidth() / 6 * 1 * 9 && getScrollX() <= getMeasuredWidth() / 6 * 1 * 11) {
            position = 6;
        } else if (getScrollX() > getMeasuredWidth() / 6 * 1 * 11 && getScrollX() <= getMeasuredWidth() / 6 * 1 * 13) {
            position = 7;
        } else if (getScrollX() > getMeasuredWidth() / 6 * 1 * 13 && getScrollX() <= getMeasuredWidth() / 6 * 1 * 15) {
            position = 8;
        }

        return position;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (titles.length <= 1) {
            return super.onTouchEvent(event);
        }
        int x = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isClick = true;
                downX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                isClick = false;
                int offset = x - downX;
                if (getScrollX() < -getMeasuredWidth() / 3.0 || getScrollX() > getMeasuredWidth() / 3.0 * titles.length - getMeasuredWidth() + getMeasuredWidth() / 3.0) {
                    return super.onTouchEvent(event);
                } else {
                    scrollX(lastScrollX - offset, true);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isClick) {
                    return true;
                }
                if (getScrollX() < -getMeasuredWidth() / 3.0) {
                    scrollX((int) (-getMeasuredWidth() / 3.0), true);
                }
                if (getScrollX() > getMeasuredWidth() / 3.0 * titles.length - getMeasuredWidth() + getMeasuredWidth() / 3.0) {
                    scrollX((int) (getMeasuredWidth() / 3.0 * titles.length - getMeasuredWidth() + getMeasuredWidth() / 3.0), true);
                }

                scrollX(getMeasuredWidth() / 3 * (getCurrentPosition() - 1), false);

                break;
        }
        return true;
    }

    private void scrollX(int endX, boolean b) {
        if (b) {
            scrollTo(endX, 0);
        } else {
            isScroll = true;
            lastScrollX = endX;
            smoothScrollTo(endX, 0);
        }
    }

    public void smoothScrollTo(int destX, int destY) {
        mScroller.startScroll(getScrollX(), 0, destX - getScrollX(), 0, 200);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        } else {
            if (isScroll) {
                setScrollX(lastScrollX);
                isScroll = false;
                if (onScrollEndListener != null) {
                    onScrollEndListener.currentPosition(getCurrentPosition());
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widhtMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        setMeasuredDimension(widhtMode == MeasureSpec.EXACTLY ? widthSize : widthSize, heightMode == MeasureSpec.EXACTLY ? heightSize : dp2px(getContext(), 100));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (titles == null) {
            return;
        }
        drawText(canvas);

    }

    private void drawText(Canvas canvas) {
        for (int i = 0; i < titles.length; i++) {
            if (getCurrentPosition() == i) {
                paint.setTextSize(sp2px(getContext(), 20));
                paint.getTextBounds(titles[i], 0, titles[i].length(), textBound);
                canvas.drawText(titles[i], getMeasuredWidth() / 6 * (2 * i + 1) - (textBound.width() / 2), (getMeasuredHeight() / 2) + textBound.height() / 2, paint);
                paint.setStrokeWidth(3);
                canvas.drawLine(getMeasuredWidth() / 6 * (2 * i + 1) - (textBound.width() / 2) - 10, (getMeasuredHeight() / 2) + textBound.height(),
                        getMeasuredWidth() / 6 * (2 * i + 1) + (textBound.width() / 2) + 10, (getMeasuredHeight() / 2) + textBound.height(), paint);
            } else {
                paint.setTextSize(sp2px(getContext(), 10));
                paint.getTextBounds(titles[i], 0, titles[i].length(), textBound);
                canvas.drawText(titles[i], getMeasuredWidth() / 6 * (2 * i + 1) - (textBound.width() / 2), (getMeasuredHeight() / 2) + textBound.height() / 2, paint);
            }
            if (getCurrentPosition() >= picIds.length) {
                return;
            }
            setBackgroundResource(picIds[getCurrentPosition()]);
        }
    }

    public interface OnScrollEndListener {
        void currentPosition(int position);
    }


    /**
     * dp转px
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }
}

