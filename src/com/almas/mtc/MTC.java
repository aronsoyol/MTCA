package com.almas.mtc;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;

public class MTC extends EditText {
	static {
		System.loadLibrary("mtc");
	}
	/*
	 * mBmp - native函数绘制的位图
	 * */
	private Bitmap mBmp;
	private int mNativeLayout;
	private int mNativeFont;
	
	@SuppressLint("UseValueOf")
	private Boolean lock = new Boolean(true);
	
	// add by he
	private final PopupWindow mCursor;
	private CursorController cursorController = new CursorController();
	protected int mFontSpacing;
	protected int mDensity = (int) getResources().getDisplayMetrics().density;
	protected TextPaint mPaint;
	protected int fontsize=30;
	
	public MTC(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.addTextChangedListener(new MyTextWatcher() );
		// TODO Auto-generated constructor stub
		mNativeFont =  nativeFontCreate(fontsize*mDensity, Color.BLACK, Color.WHITE);
		mNativeLayout = nativeLayoutCreate(mNativeFont);
		String text = getText().toString();
		if(text.length() > 0)
		{
			nativeLayoutSetText(mNativeLayout, text);
		}
		//add by he
		mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		SharedPreferences mPrefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		mPaint.setTextSize(mPrefs.getInt("40",
				fontsize)*mDensity);
		mPaint.setTypeface(Typeface.createFromAsset(context.getAssets(),
				"MongolianWhite.ttf"));
		View view = new View(context);
		view.setBackgroundColor(Color.BLUE);
		mFontSpacing = (int) mPaint.getFontSpacing();
		mCursor = new PopupWindow(view);
	}
	public MTC(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.addTextChangedListener(new MyTextWatcher() );
		// TODO Auto-generated constructor stub
		mNativeFont =  nativeFontCreate(fontsize*mDensity, Color.BLACK, Color.WHITE);
		mNativeLayout = nativeLayoutCreate(mNativeFont);
		String text = getText().toString();
		if(text.length() > 0)
		{
			nativeLayoutSetText(mNativeLayout, text);
		}
		//add by he
		mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		SharedPreferences mPrefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		mPaint.setTextSize(mPrefs.getInt("40",
				fontsize)*mDensity);
		mPaint.setTypeface(Typeface.createFromAsset(context.getAssets(),
				"MongolianWhite.ttf"));
		View view = new View(context);
		view.setBackgroundColor(Color.BLUE);
		mCursor = new PopupWindow(view);
	}
	public MTC(Context context) {
		super(context);
		this.addTextChangedListener(new MyTextWatcher() );
		// TODO Auto-generated constructor stub
		mNativeFont =  nativeFontCreate(fontsize*mDensity, Color.BLACK, Color.WHITE);
		mNativeLayout = nativeLayoutCreate(mNativeFont);
		String text = getText().toString();
		if(text.length() > 0)
		{
			nativeLayoutSetText(mNativeLayout, text);
		}
		//add by he
		mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		SharedPreferences mPrefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		mPaint.setTextSize(mPrefs.getInt("40",
				fontsize)*mDensity);
		mPaint.setTypeface(Typeface.createFromAsset(context.getAssets(),
				"MongolianWhite.ttf"));
		View view = new View(context);
		view.setBackgroundColor(Color.BLUE);
		mCursor = new PopupWindow(view);
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		nativeLayoutDestroy(mNativeLayout);
		nativeFontDestroy(mNativeFont);
	}
	/*
	 * c语言的绘制文本的函数
	 * */
	public native int nativeFontCreate(int size, int forColor, int backColor);
	public native void nativeFontDestroy(int nativeFont);
	public native int nativeFontLineHeight(int nativeFont);
	public native int nativeLayoutCreate(int nativeFont);
	public native void nativeLayoutSetText(int nativeLayout, String text);
	public native int nativeLayoutBreakLine(int nativeLayout, int height);
	public native boolean nativeLayoutDrawToBitmap(int nativeLayout, int width, int height, int x,int y);
	
	
	/*
	 * 从坐标获取字符的索引
	 * */
	public native int nativeLayoutGetCharPosition(int nativeLayout, int x, int y);
	
	/*
	 *从字符的索引获取字符的坐标
	 * */
	public native long nativeLayoutGetCharLocation(int nativeLayout, int charPos, boolean trailing);
	
	
	/*
	 * 清楚native layout
	 * */
	public native void nativeLayoutDestroy(int nativeLayout);
	//public native boolean nativeDrawToBitmap(int textSize, int width, int heigth, String text, Boolean lock);

	/*
	 * java的绘制位图函数，被nativeDrawToBitmap调用
	 * */
	public void javaDrawToBitmap(int canvasBitmap[], int width, int height)
	{
		mBmp = Bitmap.createBitmap(canvasBitmap, width, height, Config.ARGB_8888);

	}
	
	@Override
	protected void onDraw(Canvas canvas) {

		if(mBmp != null)
			canvas.drawBitmap(mBmp, this.getPaddingLeft(), getPaddingTop(), null);
		// add by Notes
	/*	if(mCursorPos != null)
		{
			Paint p = new Paint();
			p.setColor(Color.BLUE);
			
			canvas.drawLine(mCursorPos.x, mCursorPos.y, mCursorPos.x + 50, mCursorPos.y, p);
			canvas.drawLine(mCursorPos.x, mCursorPos.y+1, mCursorPos.x + 50, mCursorPos.y+1, p);
		}*/
	}
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    	// TODO Auto-generated method stub
    	super.onSizeChanged(w, h, oldw, oldh);
		int width = w - getPaddingLeft() - getPaddingRight();
		int height = h - getPaddingTop() - getPaddingBottom();
		
//		if(width > 0 && height > 0)
//		{
//			int textSize = (int)getTextSize();
//			nativeDrawToBitmap(
//					textSize, width, height,
//					getText().toString(), lock
//				);
//		}
		nativeLayoutBreakLine(mNativeLayout, height);
		nativeLayoutDrawToBitmap(mNativeLayout, width, height, 0, 0);
    	//checkLineEnd();
    }
    private Point mCursorPos;
    private Canvas canvas;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		int x = (int) event.getX() - getPaddingLeft();
		int y = (int) event.getY() - getPaddingTop();
		
		// 从坐标获取 字符位置， charPos 的 最高位 表示 trailing 
		// 什么是trailing？
		//，当前坐标位置是 字符的上半部分的话 trailing为false
		//下半部分的话trailing 为true
		int charPos = nativeLayoutGetCharPosition(mNativeLayout, x, y);
		
		Log.v("MTC", String.format("x=%d,y=%d,pos=%d",x, y,charPos));
		boolean trailing = (charPos & 0x80000000) != 0;
		
		// 经过这个操作以后才是真正的字符位置
		charPos &= 0X7FFFFFFF;
		
		
		// 64位的long 类型返回值的 高32位表示x坐标
		//低32表示 y坐标
		
		long charLoc = nativeLayoutGetCharLocation(mNativeLayout, charPos, trailing);
		
		mCursorPos = new Point();
		mCursorPos.y = (int)(charLoc & 0x0FFFFFFFFL) + getPaddingTop();
		mCursorPos.x = (int)((charLoc & 0xFFFFFFFF00000000L) >>> 32) + getPaddingLeft();
		cursorController.show(mCursorPos.x, mCursorPos.y);
		Log.v("MTC", String.format("p=%d,t=%d,touchX=%d,touchY=%d,charX=%d,charY=%d", charPos, trailing?1:0, x, y, mCursorPos.x, mCursorPos.y));
		boolean ret = super.onTouchEvent(event);
		setSelection(charPos + (trailing ? 1 : 0));
		// add by he
		moveCursor(mCursorPos.x, mCursorPos.y);
		// end by
		return ret;
	}
	private void moveCursor(int x,int y) {
		int[] viewCoords = new int[2];
		this.getLocationInWindow(viewCoords);
		mFontSpacing = (int) mPaint.getFontSpacing();
		mCursor.setWidth(mFontSpacing);
		mCursor.setHeight(1 * mDensity);
		cursorController.hide();

		cursorController.show(x + viewCoords[0], y
				+ viewCoords[1]);

	}
	class MyTextWatcher implements TextWatcher
	{

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			int width = getWidth() - getPaddingLeft() - getPaddingRight();
			int height = getHeight() - getPaddingTop() - getPaddingBottom();
			
			nativeLayoutSetText(mNativeLayout, getText().toString());
			nativeLayoutBreakLine(mNativeLayout, height);
			nativeLayoutDrawToBitmap(mNativeLayout, width, height, 0, 0);
//			if(width > 0 && height > 0)
//			{
//				
//				nativeDrawToBitmap(
//						(int)getTextSize(), width, height,
//						getText().toString(), lock
//					);
//			}
			//获取新的坐标
			int charPos = getSelectionStart();
			long charLoc = nativeLayoutGetCharLocation(mNativeLayout, charPos, false);
			mCursorPos = new Point();
			mCursorPos.y = (int)(charLoc & 0x0FFFFFFFFL) + getPaddingTop();
			mCursorPos.x = (int)((charLoc & 0xFFFFFFFF00000000L) >>> 32) + getPaddingLeft();
			// add by he
			moveCursor(mCursorPos.x, mCursorPos.y);
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}
		
	}
	// add by he 
	private class CursorController {
		private static final int DELAY_BEFORE_FADE_OUT = 600;
		private boolean showing = false;
		int x;
		int y;
		int[] viewCoords = new int[2];
		private final Runnable mHider = new Runnable() {
			@Override
			public void run() {
				if (showing) {
					hide();
				} else {
					show(x, y);
				}
			}
		};

		CursorController() {
			getLocationInWindow(viewCoords);
		}

		public void show(int x, int y) {
			this.x = x;
			this.y = y;
			showing = true;
			mCursor.showAtLocation(getRootView(), Gravity.NO_GRAVITY, x+ viewCoords[0], y+viewCoords[1]);
			hideDelayed(DELAY_BEFORE_FADE_OUT);
		}
		public void hide() {
			showing = false;
			mCursor.dismiss();
			removeCallbacks(mHider);
			hideDelayed(DELAY_BEFORE_FADE_OUT);
		}

		private void hideDelayed(int msec) {
			removeCallbacks(mHider);
			postDelayed(mHider, msec);
		}
		public void stop() {
			showing = false;
			mCursor.dismiss();
			removeCallbacks(mHider);
		}

	}
}
