package com.example.asynctask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
    static int itemCount=0;
    ProgressDialog pd;
    private Handler handler;
    ExecutorService threadpool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  /*      threadpool = Executors.newFixedThreadPool(1);
        handler = new Handler(new Handler.Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch(msg.what)
				{
				case 0:
					pd = new ProgressDialog(MainActivity.this);
					pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					pd.setCancelable(false);
					pd.setMessage("Loading Image...");
					pd.show();
					break;
				case 1:

						pd.dismiss();
						ImageView im = (ImageView) findViewById(R.id.imageView1);
						if((Bitmap)msg.obj != null)
						{
						im.setImageBitmap((Bitmap)msg.obj);
						}
						Log.d("demo",Integer.toString(MainActivity.itemCount));
					
					break;
				default:
						break;
						
				}
				return false;
			}
		});
*/
        new getImage().execute("http://dev.theappsdr.com/lectures/inclass_http/index.php?pid="+ itemCount);
        findViewById(R.id.imageButton1).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				itemCount++;
				
				// TODO Auto-generated method stub
				if(itemCount>19)
				{
					itemCount =0;

				}
				if(checkNetworkConnection())

				 new getImage().execute("http://dev.theappsdr.com/lectures/inclass_http/index.php?pid="+ itemCount);
				else
					Toast.makeText(MainActivity.this, "No Network Connection",Toast.LENGTH_SHORT).show();
				
			}
		});
        findViewById(R.id.imageButton2).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				itemCount--;
				// TODO Auto-generated method stub
				if(itemCount<0)
				{
					itemCount=19;
					
				}
				if(checkNetworkConnection())

					 new getImage().execute("http://dev.theappsdr.com/lectures/inclass_http/index.php?pid="+ itemCount);
					else
						Toast.makeText(MainActivity.this, "No Network Connection",Toast.LENGTH_SHORT).show();
			}
		});
      
    }
    
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

    class getImage extends AsyncTask<String, Void, Bitmap>{

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			try{
				URL url = new URL(params[0]);
				URLConnection con = url.openConnection();
				Bitmap bitmap = BitmapFactory.decodeStream(con.getInputStream());
				return bitmap;
			}
			catch(Exception e){
				e.printStackTrace();
				}
			
			return null;
		}
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result != null)
			{
				ImageView im = (ImageView) findViewById(R.id.imageView1);
				im.setImageBitmap(result);
			}
			pd.dismiss();
		
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(MainActivity.this);
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setCancelable(false);
			pd.setMessage("Loading Image...");
			pd.show();
		}

    	
    }
    
    public boolean checkNetworkConnection()
    {
    	ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo info = cm.getActiveNetworkInfo();
    	if(info !=null && info.isConnected())
    		return true;
		return false;
    	
    }
    
  /*  class imageDownload implements Runnable{

		@Override
		public void run() {
			Message msg = new Message();
			msg.what = 0;
			handler.sendMessage(msg);
			URL url;
			try {
				url = new URL("http://dev.theappsdr.com/lectures/inclass_http/index.php?pid="+ MainActivity.itemCount);
				URLConnection con = url.openConnection();
				Bitmap bitmap = BitmapFactory.decodeStream(con.getInputStream());
				msg.obj = bitmap;
				msg.what = 1;
				handler.sendMessage(msg);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			
		}
    	
    }*/
}
