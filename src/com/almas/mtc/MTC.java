package com.almas.mtc;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

public class MTC extends EditText {
	static {
		System.loadLibrary("mtc");
	}
	/*
	 * mBmp - native函数绘制的位图
	 * */
	private Bitmap mBmp;
	
	
	@SuppressLint("UseValueOf")
	private Boolean lock = new Boolean(true);
	
	public MTC(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.addTextChangedListener(new MyTextWatcher() );
		// TODO Auto-generated constructor stub
	}
	public MTC(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.addTextChangedListener(new MyTextWatcher() );
		// TODO Auto-generated constructor stub
	}
	public MTC(Context context) {
		super(context);
		this.addTextChangedListener(new MyTextWatcher() );
		// TODO Auto-generated constructor stub
	}
	/*
	 * c语言的绘制文本的函数
	 * */
	public native boolean nativeDrawToBitmap(int textSize, int width, int heigth, String text, Boolean lock);
	
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
	}
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    	// TODO Auto-generated method stub
    	super.onSizeChanged(w, h, oldw, oldh);
		int width = w - getPaddingLeft() - getPaddingRight();
		int height = h - getPaddingTop() - getPaddingBottom();
		
		if(width > 0 && height > 0)
		{
			int textSize = (int)getTextSize();
			nativeDrawToBitmap(
					textSize, width, height,
					getText().toString(), lock
				);
		}
    	//checkLineEnd();
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
			
			if(width > 0 && height > 0)
			{
				nativeDrawToBitmap(
						(int)getTextSize(), width, height,
						getText().toString(), lock
					);
			}
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}
		
	}
}
