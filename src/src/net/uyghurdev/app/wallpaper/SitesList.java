package net.uyghurdev.app.wallpaper;

import java.util.ArrayList;

public class SitesList {
	 
	/** Variables */

	 private ArrayList<String> imgName = new ArrayList<String>();
	 private ArrayList<String> imgAddr = new ArrayList<String>();
	 private ArrayList<String> imgAddrSmall = new ArrayList<String>();

	 
	/** In Setter method default it will return arraylist
	 * change that to add */

	public ArrayList<String> getIMGName() {
	 return imgName;
	 }
	 
	public void setIMGName(String imgName) {
	 this.imgName.add(imgName);
	 }
	 
	public ArrayList<String> getImgAddr() {
	 return imgAddr;
	 }
	 
	public void setImgAddr(String imgAddr) {
	 this.imgAddr.add(imgAddr);
	 }
	 
	public ArrayList<String> getImgSmall() {
	 return imgAddrSmall;
	 }
	 
	public void setImgSmall(String imgAddrSmall) {
	 this.imgAddrSmall.add(imgAddrSmall);
	 }

}
