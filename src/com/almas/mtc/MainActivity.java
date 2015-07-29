package com.almas.mtc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Context context = getApplicationContext();
//		copyFontToPackage(context, )
		String[] fontNames = new String[2];// = "MongolianWhite.ttf";
		fontNames[0] = new String("MongolianWhite.ttf");
		fontNames[1] = new String("msyh.ttf");
		int num_of_copied_file = 0;
		int num_of_exists_file = 0;
		for(String fontName : fontNames)
		{
			File fontFile = new File(getApplicationContext().getFilesDir().getAbsoluteFile() + "/" + fontName);
			if(!fontFile.exists())
			{
				if(copyFontToPackage(context, fontName))
					num_of_copied_file++;
			}
			else
			{
				num_of_exists_file++;
			}
		}
		Log.i("onCreate", String.format("%d copied, %d exits", num_of_copied_file, num_of_exists_file));
	}
	private boolean copyFontToPackage(Context context, String fontName)
	{
		AssetManager assetManager = context.getAssets();
		
		//从Asset读取字体文件，并存储到工程包下面
		String fontFileName = fontName;
	    InputStream inputStream = null;
	    
	    FileOutputStream outputStream = null;
	    File outputFile = null;
	    long fileSize0 = 0;
		try {
			inputStream = assetManager.open(fontFileName);
			outputStream = openFileOutput(fontFileName, MODE_PRIVATE);
			fileSize0 = copyFile(inputStream, outputStream);
			inputStream.close();
			outputStream.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		//验证是否正确存储到工程包下面

		File file = new File(getApplicationContext().getFilesDir().getAbsoluteFile() + "/" + fontName);
		
		if(file.exists())
		{
			long fileSize1 = file.length();
			if(fileSize0 == fileSize1)
			{
				Log.i("copy font file", "successed");
				return true;
			}
			else
			{
				Log.i("copy font file", "failed");
				return false;
			}
		}
		else
		{
			Log.i("copy font file", "failed");
			return false;
		}
	}
	public  long copyFile(InputStream in, OutputStream out) throws IOException {
	    byte[] buffer = new byte[1024];
	    long size = 0;
	    int read;
	    while((read = in.read(buffer)) != -1){
	      out.write(buffer, 0, read);
	      size += read;
	    }
	    return size;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
