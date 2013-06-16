package com.luzi82.fontmetricsviewer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class FontDisplaySurfaceView extends SurfaceView {

	public FontDisplaySurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		update();
		setWillNotDraw(false);
	}

	public FontDisplaySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		update();
		setWillNotDraw(false);
	}

	public FontDisplaySurfaceView(Context context) {
		super(context);
		update();
		setWillNotDraw(false);
	}

	String mText = "";

	public void setText(String aText) {
		mText = aText;
		update();
	}

	int mDrawX = 100;
	int mDrawY = 100;

	public void setPos(int aX, int aY) {
		mDrawX = aX;
		mDrawY = aY;
		update();
	}

	Typeface mTextTypeface = Typeface.DEFAULT;
	float mTextSize = 64;

	public void setTextTypeface(Typeface aTypeface) {
		mTextTypeface = aTypeface;
		update();
	}

	public void setTextSize(float aSize) {
		mTextSize = aSize;
		update();
	}

	public int mHinting = Paint.HINTING_OFF;

	public void setHinting(int hinting) {
		mHinting = hinting;
		update();
	}

	public Align mAlign = Align.LEFT;

	public void setAlign(Align align) {
		mAlign = align;
		update();
	}

	public static final float DATA_TEXT_SIZE = 15F;
	Paint mTextPaint = new Paint();
	Rect mTextRect = new Rect();
	Rect mTextBounds =  new Rect();
	float mTextMeasure;
	FontMetrics mFontMetrics;
	Paint mBoundPaint = new Paint();
	Paint mBoundPaint2 = new Paint();
	Paint mPosPaint = new Paint();
	Paint mFontMetricsPaint = new Paint();
	Paint mTextMeasurePaint = new Paint();
	Paint mTextMeasurePaint2 = new Paint();

	public void update() {
		mTextPaint.reset();
		mTextPaint.setTypeface(mTextTypeface);
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setTextAlign(mAlign);
		mTextPaint.setHinting(mHinting);
		mTextPaint.setColor(Color.BLACK);
		mTextPaint.setStyle(Paint.Style.FILL);
		mFontMetrics = mTextPaint.getFontMetrics();

		mBoundPaint.setColor(0xffbfdfff);
		mBoundPaint.setStyle(Paint.Style.FILL);
		
		mBoundPaint2.setColor(0xff003f7f);
		mBoundPaint2.setTextSize(DATA_TEXT_SIZE);

		mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBounds);
		mTextPaint.getTextBounds(mText, 0, mText.length(), mTextRect);
		mTextRect.offset(mDrawX, mDrawY);
		mTextMeasure = mTextPaint.measureText(mText);

		mPosPaint.setColor(0xff007f00);
		mPosPaint.setTextAlign(Align.LEFT);
		mPosPaint.setTextSize(DATA_TEXT_SIZE);
		mFontMetricsPaint.setColor(0xff7f0000);
		mFontMetricsPaint.setTextAlign(Align.RIGHT);
		mFontMetricsPaint.setTextSize(DATA_TEXT_SIZE);

		mTextMeasurePaint.setColor(0xff7fff7f);
		mTextMeasurePaint.setTextAlign(Align.LEFT);
		mTextMeasurePaint.setTextSize(DATA_TEXT_SIZE);

		mTextMeasurePaint2.setColor(0xffbfffbf);
		mTextMeasurePaint2.setTextAlign(Align.LEFT);
		mTextMeasurePaint2.setTextSize(DATA_TEXT_SIZE);

		invalidate();
	}

	float touchDownX;
	float touchDownY;
	int moveOriX;
	int moveOriY;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			touchDownX = event.getX();
			touchDownY = event.getY();
			moveOriX = mDrawX;
			moveOriY = mDrawY;
			return true;
		}
		case MotionEvent.ACTION_MOVE: {
			mDrawX = Math.round(moveOriX + (event.getX() - touchDownX));
			mDrawY = Math.round(moveOriY + (event.getY() - touchDownY));
			update();
			return true;
		}
		case MotionEvent.ACTION_UP: {
			mDrawX = Math.round(moveOriX + (event.getX() - touchDownX));
			mDrawY = Math.round(moveOriY + (event.getY() - touchDownY));
			update();
			return true;
		}
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC);

		canvas.drawRect(mTextRect, mBoundPaint);
		canvas.drawText(mText, mDrawX, mDrawY, mTextPaint);

		int cw, ch;
		cw = canvas.getWidth();
		ch = canvas.getHeight();
		float x, y;
		Paint p;

		x = mDrawX;
		y = mDrawY;
		p = mPosPaint;
		canvas.drawLine(x, 0, x, ch, p);
		canvas.drawText(" x: " + x, x, (float) (0 - Math.floor(p.getFontMetrics().top)), p);
		canvas.drawLine(0, y, cw, y, p);
		canvas.drawText(" y: " + y, 0, y, p);

		p = mFontMetricsPaint;
		x = cw;

		y = mDrawY + mFontMetrics.ascent;
		canvas.drawLine(0, y, cw, y, p);
		canvas.drawText("ascent: " + mFontMetrics.ascent + " ", x, y, p);

		y = mDrawY + mFontMetrics.bottom;
		canvas.drawLine(0, y, cw, y, p);
		canvas.drawText("bottom: " + mFontMetrics.bottom + " ", x, y, p);

		y = mDrawY + mFontMetrics.descent;
		canvas.drawLine(0, y, cw, y, p);
		canvas.drawText("descent: " + mFontMetrics.descent + " ", x, y, p);

		y = mDrawY + mFontMetrics.leading;
		canvas.drawLine(0, y, cw, y, p);
		canvas.drawText("leading: " + mFontMetrics.leading + " ", x, y, p);

		y = mDrawY + mFontMetrics.top;
		canvas.drawLine(0, y, cw, y, p);
		canvas.drawText("top: " + mFontMetrics.top + " ", x, y, p);

		p = mTextMeasurePaint;
		x = mDrawX + mTextMeasure;
		canvas.drawLine(x, 0, x, ch, p);
		canvas.drawText(" measureText: " + mTextMeasure, x, (float) (0 - Math.floor(p.getFontMetrics().top)), p);
		x = mDrawX - mTextMeasure;
		canvas.drawLine(x, 0, x, ch, p);
		canvas.drawText(" measureText: " + mTextMeasure, x, (float) (0 - Math.floor(p.getFontMetrics().top)), p);
		p = mTextMeasurePaint2;
		x = mDrawX + mTextMeasure / 2;
		canvas.drawLine(x, 0, x, ch, p);
		x = mDrawX - mTextMeasure / 2;
		canvas.drawLine(x, 0, x, ch, p);

		canvas.drawText("getTextBounds: "+mTextBounds.toShortString(), mTextRect.left, mTextRect.top, mBoundPaint2);
	}

}
