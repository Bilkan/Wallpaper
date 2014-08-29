package net.uyghurdev.app.wallpaper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class About extends Activity {

	    /** Called when the activity is first created. */

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	    	requestWindowFeature(Window.FEATURE_NO_TITLE);
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.about);

	        Button close = (Button)findViewById(R.id.btnclose);
	        close.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
	        	
	        });
	        
	   }
}
