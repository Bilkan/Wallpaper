package net.uyghurdev.app.wallpaper;

import java.io.IOException;

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

public class Wallpaper extends Activity {

	private ImageAdapter mImgAdapter;

	private GridView myGridImage;
	@SuppressWarnings("unused")
	private int imgIndex;
	private Dialog mView;
	private OnClickListener ocl;
	private Bitmap myBp = null;
	private DownloadFile myDownImage;
	private String imageUrl;
	private String sdcardPath = "wallpaper/";
	private boolean isFinished;
	private ProgressDialog dialog = null;
	private String urlStr;
	private SitesList sitesList = null;
	public int imgWidth, imgHeight;
	private boolean notConnected = false;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
		ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE); // mobile
		NetworkInfo networkInfo = conMan.getActiveNetworkInfo();

		// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

		// notConnectedAlert();

		init();
		if (networkInfo != null
				&& networkInfo.getState() == NetworkInfo.State.CONNECTED) {
			// if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			//
			// mobileNotSelectedAlert();
			// }
		} else {
			//
			notConnected = true;

		}
		if (notConnected) {
			AlertDialog dialog = new AlertDialog.Builder(this).create();
			dialog.setTitle(getString(R.string.notNet_dlg_title));
			dialog.setMessage(getString(R.string.notNet_dlg_msg));
			dialog.setButton(getString(R.string.closeBtn),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							finish();
						}

					});
			dialog.show();

		} else {
			this.getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			imgWidth = dm.widthPixels / 3;
			imgHeight = imgWidth * 250 / 256;

			sitesList = mySiteList(urlStr + getString(R.string.xmlFileName));

			mImgAdapter = new ImageAdapter(this, sitesList.getImgSmall(),
					urlStr, imgWidth, imgHeight);
			myGridImage.setAdapter(mImgAdapter);
			imgIndex = 0;

			ocl = new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					switch (arg1) {
					case Dialog.BUTTON_NEGATIVE:
						// imgDownAndExist(imageUrl, sdcardPath);
						break;
					case Dialog.BUTTON_POSITIVE:
						imgDownAndExist(imageUrl, sdcardPath);
						mySetWallpaper();
						break;
					case Dialog.BUTTON_NEUTRAL:

						break;
					}
				}
			};

			// onClicks = new OnClickListener() {
			// @Override
			// public void onClick(DialogInterface arg0, int arg1) {
			// // TODO Auto-generated method stub
			// switch (arg1) {
			// case Dialog.BUTTON_NEGATIVE:
			// // imgDownAndExist(imageUrl, sdcardPath);
			// break;
			// case Dialog.BUTTON_POSITIVE:
			// notConnected=true;
			// break;
			// case Dialog.BUTTON_NEUTRAL:
			//
			// break;
			// }
			// }
			// };
			myGridImage
					.setOnItemClickListener(new GridView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							imgIndex = arg2;
							imageUrl = urlStr
									+ sitesList.getImgAddr().get(arg2);

							downloadImage(imageUrl);

//							myBp = ImageAdapter.returnBitmap(urlStr
//									+ sitesList.getImgAddr().get(arg2));
//							mView = myDlg(myBp, ocl);
//
//							mView.show();

						}

					});
		}
	}

	private void downloadImage(final String imageUrl) {
		// TODO Auto-generated method stub
		
		final ProgressDialog progressDialog = ProgressDialog.show(this, "",
				getString(R.string.loading));
		
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				progressDialog.dismiss();
				mView = myDlg(myBp, ocl);
				mView.show();
			}
		};

		Thread checkUpdate = new Thread() {
			public void run() {
				

				try {
					myBp = ImageAdapter.returnBitmap(imageUrl);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					
				}
			
				handler.sendEmptyMessage(0);

			}

		};

		checkUpdate.start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Log.d("String3", getResources().getString(R.string.webid));
		super.onCreateOptionsMenu(menu);
		menu.add(0, 0, 0, getString(R.string.remarks));
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			appInfo();
			break;
		}

		return false;

	}

	private void appInfo() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
    	intent.setClass(this, About.class);
		startActivity(intent);
	}

	private void mySetWallpaper() {
		// TODO Auto-generated method stub

		try {
			Wallpaper.this.setWallpaper(myBp);
			myBp = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// / deslapki kimmat elix fonkissiyis
	private void init() {
		// TODO Auto-generated method stub
		urlStr = getString(R.string.xmlFileUrl);
		myGridImage = (GridView) findViewById(R.id.grid_view);

	}

	public SitesList mySiteList(String xmlFileName) {

		try {

			/** Handling XML */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			/** Send URL to parse XML Tags */

			URL sourceUrl = new URL(xmlFileName);
			MyXMLHandler myXMLHandler = new MyXMLHandler();
			xr.setContentHandler(myXMLHandler);
			xr.parse(new InputSource(sourceUrl.openStream()));
		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}
		return MyXMLHandler.sitesList;

	}

	private Dialog myDlg(Bitmap bp, OnClickListener ocl) {
		ImageView img = new ImageView(this);

		img.setImageBitmap(bp);
		img.setMinimumWidth(640);
		img.setMinimumHeight(428);

		Dialog mDlg = new AlertDialog.Builder(this).setView(img)
				.setPositiveButton(R.string.setBtn, ocl)
				.setNegativeButton(R.string.ReturnStr, null).create();

		return mDlg;

	}

	private void imgDownAndExist(String urlStr, String sdPath) {
		// TODO Auto-generated method stub
		isFinished = false;
		myDownImage = new DownloadFile(urlStr, sdPath);
		if (myDownImage.isFileExist(myDownImage.getFileName(urlStr))) {
			// myAlterDlg(oclClick);
		} else {
			imgDownToPad(urlStr, sdPath);
		}
	}

	private void imgDownToPad(String urlStr, String sdPath) {

		CharSequence msgStr = getString(R.string.downMsg);
		CharSequence downTitle = getString(R.string.downTitle);
		isFinished = myDownImage.start();
		dialog = new ProgressDialog(this);
		dialog.setTitle(downTitle);
		dialog.setMessage(msgStr);
		dialog.setMax(myDownImage.getFileLength());
		dialog.show();
		new Thread() {
			public void run() {
				try {

					if (isFinished) {
						dialog.dismiss();
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					dialog.dismiss();
				}
			}
		}.start();
	}
	// /============ Message Dialog === tor ulanmighan wakittiki====
	// private Dialog myDialogBox(String title,String msg,String btnCaption,
	// OnClickListener onCliks) {
	//
	// AlertDialog.Builder builder = new AlertDialog.Builder(this);
	// builder.setMessage("Are you sure you want to exit?")
	// .setCancelable(false)
	// .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	//
	// public void onClick(DialogInterface dialog, int id) {
	// Wallpaper.this.finish();
	// }
	// })
	// .setNegativeButton("No", new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int id) {
	// dialog.cancel();
	// }
	// });
	// AlertDialog alert = builder.create();
	// return alert;
	//
	// }
	// private void notConnectedAlert() {
	// // TODO Auto-generated method stub
	// new AlertDialog.Builder(this)
	// .setTitle(R.string.notNet_dlg_title)
	// .setMessage(R.string.notNet_dlg_msg)
	//
	// .setPositiveButton(R.string.notNet_dlg_btnCaption,
	// new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog,
	// int which) {
	// // TODO Auto-generated method stub
	// //Wallpaper.this.finish();
	// // which=1;
	//
	// Log.d("notConnection1", ""+notConnected);
	// }
	// }).show();
	// }
	//
	// // /============ Message Dialog === 3G tor ulanmighan wakittiki====
	// private void mobileNotSelectedAlert() {
	// // TODO Auto-generated method stub
	// new AlertDialog.Builder(this)
	// .setTitle(R.string.mobile_dlg_title)
	// .setMessage(R.string.mobile_dlg_msg)
	// .setNegativeButton(R.string.mobile_dlg_btnCaption,null)
	// .setPositiveButton(R.string.mobile_dlg_btnCaptionNo,
	// new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog,
	// int which) {
	// // TODO Auto-generated method stub
	// notConnected=true;
	// }
	// }).show();
	// }
}