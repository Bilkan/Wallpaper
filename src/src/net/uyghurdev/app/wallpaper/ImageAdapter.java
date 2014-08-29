package net.uyghurdev.app.wallpaper;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.uyghurdev.app.wallpaper.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context myContext;
	private int imgWidth,imgHeight;
	 private int mGalleryItemBackground;
	// LayoutInflater inflater;
	//public String[] myImageIds;
	public List<String> myImageIds=new ArrayList<String>();
	private String urlStr;
	
	List<View> lstView=new ArrayList<View>();
	List<Integer> lstPosition=new ArrayList<Integer>();  
	public ImageAdapter(Context c, List<String> myImage,String urlStr,int imageWidth,int imageHeight)
	{
		this.myContext=c;
		this.myImageIds=myImage;
		this.urlStr=urlStr;
		this.imgWidth=imageWidth;
		this.imgHeight=imageHeight;
		TypedArray a = myContext 
        .obtainStyledAttributes(R.styleable.Gallery);
   
 
		mGalleryItemBackground = a.getResourceId(
        R.styleable.Gallery_android_galleryItemBackground, 1);


    a.recycle();

    //inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return this.myImageIds.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}



	List<Integer> lstTimes= new ArrayList<Integer>();  
	long startTime=0;

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView myImage;
		if (lstPosition.contains(position) == false) {
			//if(convertView==null){  
            if(lstPosition.size()>75)  
            {  
                lstPosition.remove(0);
                lstView.remove(0);
            } 
            myImage=new ImageView(myContext);
            myImage.setAdjustViewBounds(false);
		//
            //convertView = inflater.inflate(R.layout.imgitem, null); 
            //myImage = new imgView();
           // myImage = (ImageView) convertView.findViewById(R.id.imgView);
            myImage.setLayoutParams(new GridView.LayoutParams(imgWidth, imgHeight));
            myImage.setScaleType(ImageView.ScaleType.FIT_XY);

            myImage.setBackgroundResource(mGalleryItemBackground);
            //****************
            //DownloadFile mFile=new DownloadFile(urlStr+myImageIds.get(position), "wallpaper/");
            //String fileNameStr=new String(mFile.getFileName(urlStr+myImageIds.get(position))); 
            //****************
            new AsyncLoadImage().execute(new Object[] { myImage,urlStr+myImageIds.get(position)}); 
            
            lstPosition.add(position);
            lstView.add(myImage);
        } else  
        {  
        	myImage = (ImageView) lstView.get(lstPosition.indexOf(position)); 
        	//myImage=(imgView)convertView.getTag();
       }   
			
		return myImage;
	}

	public static Bitmap returnBitmap(String url) {
        URL bmurl = null;
        Bitmap bitmap = null;
        try {
                bmurl = new URL(url);
                HttpURLConnection  huconn = (HttpURLConnection ) bmurl
                                .openConnection();
                huconn.setDoInput(true);
                huconn.connect();
                InputStream is = huconn.getInputStream();
               int length = huconn.getContentLength();
               
                if (length != -1) {
                        byte[] imgData = new byte[length];
                        byte[] temp = new byte[1024];
                        int readLen = 0;
                        int destPos = 0;
                        while ((readLen = is.read(temp)) > 0) {
                                System.arraycopy(temp, 0, imgData, destPos, readLen);
                                destPos += readLen;
                        }
                        bitmap = BitmapFactory.decodeByteArray(imgData, 0,
                                        imgData.length);
                }
               
               is.close();
        } catch (Exception ex) {
//                Log.i("error", ex.getMessage());
        }
        return bitmap;
}

	
	class AsyncLoadImage extends AsyncTask<Object, Object, Void> {  
        @Override  
        protected Void doInBackground(Object... params) {  
  
             
                ImageView imageView=(ImageView) params[0];  
                String url=(String) params[1];

                Bitmap bitmap = returnBitmap(url); 
                publishProgress(new Object[] {imageView, bitmap});  
                


           
            return null;  
       }  
  
        protected void onProgressUpdate(Object... progress) {  
            ImageView imageView = (ImageView) progress[0];  
            imageView.setImageBitmap((Bitmap) progress[1]);           
        }  
    }  

}
