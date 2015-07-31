package com.almas.mtc;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.PaintDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

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
	
	public MTC(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.addTextChangedListener(new MyTextWatcher() );
		// TODO Auto-generated constructor stub
		mNativeFont =  nativeFontCreate(80, Color.BLACK, Color.WHITE);
		mNativeLayout = nativeLayoutCreate(mNativeFont);
		String text = getText().toString();
		if(text.length() > 0)
		{
			nativeLayoutSetText(mNativeLayout, text);
		}
	}
	public MTC(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.addTextChangedListener(new MyTextWatcher() );
		// TODO Auto-generated constructor stub
		mNativeFont =  nativeFontCreate(80, Color.BLACK, Color.WHITE);
		mNativeLayout = nativeLayoutCreate(mNativeFont);
		String text = getText().toString();
		if(text.length() > 0)
		{
			nativeLayoutSetText(mNativeLayout, text);
		}
	}
	public MTC(Context context) {
		super(context);
		this.addTextChangedListener(new MyTextWatcher() );
		// TODO Auto-generated constructor stub
		mNativeFont =  nativeFontCreate(80, Color.BLACK, Color.WHITE);
		mNativeLayout = nativeLayoutCreate(mNativeFont);
		String text = getText().toString();
		if(text.length() > 0)
		{
			nativeLayoutSetText(mNativeLayout, text);
		}
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
		
		if(mCursorPos != null)
		{
			Paint p = new Paint();
			p.setColor(Color.BLUE);
			
			canvas.drawLine(mCursorPos.x, mCursorPos.y, mCursorPos.x + 50, mCursorPos.y, p);
			canvas.drawLine(mCursorPos.x, mCursorPos.y+1, mCursorPos.x + 50, mCursorPos.y+1, p);
		}
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
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		mCursorPos = new Point();
		mCursorPos.x = (int) event.getX();
		mCursorPos.y = (int) event.getY();
		
		
		return super.onTouchEvent(event);
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
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}
		
	}
}
