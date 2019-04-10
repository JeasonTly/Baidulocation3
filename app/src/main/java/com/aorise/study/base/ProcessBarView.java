package com.aorise.study.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.aorise.study.R;

/**
 * Created by Tuliyuan.
 * Date: 2019/3/26.
 */
public class ProcessBarView extends View {
    private final String TAG = "ProcessBarView";
    private int foregroundColor;
    private int backgroundColor;
    private int textColor;
    private float strokewidth;

    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private int mPaddingBottom;
    private RectF mFrameRect;
    private int mViewHeight;
    private int mViewWidth;

    private int mCenterX;
    private int mCenterY;
    private int mCircleRadius;


    private Paint mBackgroundPaint = new Paint();
    private Paint mForegroundPaint = new Paint();

    public ProcessBarView(Context context) {
        super(context);
    }

    public ProcessBarView(Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
        initStyle(context, attrs);
        initPaint();
    }

    public ProcessBarView(Context context, @NonNull AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyle(context, attrs);
        initPaint();
    }

    public ProcessBarView(Context context, @NonNull AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initStyle(context, attrs);
        initPaint();
    }

    private void initStyle(Context context, AttributeSet attrs) {
        LogT.d("initStyle...");
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.ProcessBar);
        try {
            foregroundColor = mTypedArray.getColor(R.styleable.ProcessBar_foreground_color, getResources().getColor(R.color.color_status_bar));
            backgroundColor = mTypedArray.getColor(R.styleable.ProcessBar_background_color, getResources().getColor(R.color.color_status_bar_black));
            textColor = mTypedArray.getColor(R.styleable.ProcessBar_text_color, getResources().getColor(R.color.color_status_bar_black));
            strokewidth = mTypedArray.getDimension(R.styleable.ProcessBar_stroke_width, getResources().getDimension(R.dimen.bb_10dp));
        } catch (Exception e) {
            e.printStackTrace();
            LogT.d("exception  " + e);
        }

    }

    private void initPaint() {
        // foreground paint
        mFrameRect = new RectF();
        mForegroundPaint.setColor(foregroundColor);
        mForegroundPaint.setStrokeWidth(strokewidth);
        mForegroundPaint.setStyle(Paint.Style.STROKE);
        mForegroundPaint.setStrokeCap(Paint.Cap.ROUND);
        mForegroundPaint.setAntiAlias(true);

        // background paint
        mBackgroundPaint.setColor(backgroundColor);
        mBackgroundPaint.setStrokeWidth(strokewidth);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setAntiAlias(true);
//
//        // end out circle paint
//        mEndOutCirclePaint.setColor(mForegroundColor);
//        mEndOutCirclePaint.setStyle(Paint.Style.FILL);
//        mEndOutCirclePaint.setAntiAlias(true);
//
//        // end inner circle paint
//        mEndInnerCirclePaint.setColor(mBackgroundColor);
//        mEndInnerCirclePaint.setStyle(Paint.Style.FILL);
//        mEndInnerCirclePaint.setAntiAlias(true);
//
//        // title text paint
//        mTitleTextPaint.setColor(mTitleTextColor);
//        mTitleTextPaint.setTextSize(getResources().getDimension(R.dimen.circle_progress_default_title_size));
//        mTitleTextPaint.setAntiAlias(true);
//        mTitleTextPaint.setTextAlign(Paint.Align.CENTER);
//
//        // value text paint
//        mValueTextPaint.setColor(mForegroundColor);
//        mValueTextPaint.setTextSize(getResources().getDimension(R.dimen.circle_progress_default_value_size));
//        mValueTextPaint.setAntiAlias(true);
//        mValueTextPaint.setTextAlign(Paint.Align.CENTER);
//
//        // unit text paint
//        mUnitTextPaint.setColor(mForegroundColor);
//        mUnitTextPaint.setTextSize(getResources().getDimension(R.dimen.circle_progress_default_unit_size));
//        mUnitTextPaint.setAntiAlias(true);
//        mUnitTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogT.d("onMeasure widthMeasureSpec " + widthMeasureSpec + " heightMeasureSpec " + heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        LogT.d("onMeasure widthMeasureSize " + widthSize + " heightMeasureSpec " + heightSize);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension((int) getResources().getDimension(R.dimen.bb_108dp), (int) getResources().getDimension(R.dimen.bb_108dp));
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension((int) getResources().getDimension(R.dimen.bb_108dp), heightSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, (int) getResources().getDimension(R.dimen.bb_108dp));
        }
    }

    //    触摸事件：手指和屏幕的触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mCenterX = (int) event.getX();  //手指在x轴的位置
        mCenterY = (int) event.getY();  //手指在y轴的位置
        Log.d(TAG, "onTouchEvent: mCenterX " + mCenterX + " mCenterY " + mCenterY);
        mFrameRect.set(mCenterX - mCircleRadius + strokewidth,
                mCenterY - mCircleRadius + strokewidth,
                mCenterX + mCircleRadius - strokewidth,
                mCenterY + mCircleRadius - strokewidth);
        invalidate();   //重绘当前界面的方法
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogT.d("onLayout 只走一次 getPaddingLeft() " + getPaddingLeft() + " getPaddingRight " + getPaddingRight());

        mPaddingLeft = getPaddingLeft() + (int) strokewidth;
        mPaddingRight = getPaddingRight() + (int) strokewidth;
        mPaddingTop = getPaddingTop() + (int) strokewidth;
        mPaddingBottom = getPaddingBottom() + (int) strokewidth;
        mViewWidth = getWidth() - mPaddingLeft - mPaddingRight;
        mViewHeight = getHeight() - mPaddingTop - mPaddingBottom;

        mCenterX = mPaddingLeft + mViewWidth / 2;
        mCenterY = mPaddingRight + mViewHeight / 2;
        mCircleRadius = mViewWidth < mViewHeight ? mViewWidth / 2 : mViewHeight / 2;//半径

        Log.d(TAG, "onLayout: mCenterX " + mCenterX + " mCenterY " + mCenterY + " mCircleRadius " + mCircleRadius);
        mFrameRect.set(mCenterX - mCircleRadius + strokewidth,
                mCenterY - mCircleRadius + strokewidth,
                mCenterX + mCircleRadius - strokewidth,
                mCenterY + mCircleRadius - strokewidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mCenterX, mCenterY, mCircleRadius - strokewidth, mBackgroundPaint);
        Paint mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokewidth);
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.bb_3ba1ff));
        Log.d(TAG, "onDraw: 重新绘制就会走 mFrameRect.left " + mFrameRect.left + " mFrameRect.top " + mFrameRect.top
                + " mFrameRect.bottom " + mFrameRect.bottom + " mFrameRect.right" + mFrameRect.right);
        canvas.drawArc(mFrameRect, -30, 60, false, mPaint);
        Paint mPaint1 = new Paint();
        mPaint1.setStyle(Paint.Style.STROKE);
        mPaint1.setStrokeWidth(strokewidth);
        mPaint1.setAntiAlias(true);
        mPaint1.setColor(getResources().getColor(R.color.bb_34dd8a));
        canvas.drawArc(mFrameRect, 30, 180, false, mPaint1);
    }
}
