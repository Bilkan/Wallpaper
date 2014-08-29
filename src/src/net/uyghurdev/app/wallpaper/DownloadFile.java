package net.uyghurdev.app.wallpaper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Environment;

public class DownloadFile{
	private static final int BUFFER_SIZE=1024;
	private URL url;
	private byte[] buffer;
	private String urlStr;
	private int downloadedSize;
	private int fileSize;
	private HttpURLConnection con = null;
	private Boolean finished=false;
	private String SDPATH;
	private String dirName;
	public DownloadFile(String urlStr,String dirName){
		this.urlStr=urlStr;
		this.SDPATH=  Environment.getExternalStorageDirectory()+ "/";
		this.dirName=dirName;
	}
	public boolean start() {

        
        try {
		 		
		 		File file = new File(SDPATH+dirName,getFileName(urlStr));       		   		 		
		 		url=new URL(urlStr);
				con = (HttpURLConnection) url.openConnection(); 
		        con.setRequestMethod("GET");
		        con.setDoOutput(true);
		        con.connect();
                FileOutputStream fileOutput = new FileOutputStream(file); 
                InputStream inputStream = con.getInputStream();
                fileSize = con.getContentLength();
                downloadedSize = 0;
                buffer = new byte[BUFFER_SIZE];
                int bufferLength = 0;
                do{
             
                	bufferLength = inputStream.read(buffer);
                	if(bufferLength==-1){
                		break;
                	}
                	fileOutput.write(buffer, 0, bufferLength);
                       downloadedSize += bufferLength;
                       finished=false;
                	}while(true);        //close the output stream when done
               
                fileOutput.close();
                inputStream.close();
                finished=true;
                }
         catch (MalformedURLException e) {
                e.printStackTrace();
        }
         catch (IOException e)
         {        e.printStackTrace();}
         return finished;
        
	}
	public Boolean isFinish() {
		return finished;
	}
	public int getCur() {
		// TODO Auto-generated method stub
		return downloadedSize;
	}
	public int getFileLength()
	{
		return fileSize;
	}	
	public String getFileName(String Url)
	{
		int fileNameStart = Url.lastIndexOf("/");
		return  Url.substring(fileNameStart);			

	}
	public boolean isFileExist(String fileName) {  
		File file = new File(SDPATH +dirName+ fileName);  
		return file.exists();  
	}  

}
