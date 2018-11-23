package com.amaon.getset;

public class Artist  {
  
	private int singerid;   //歌手id	
	private int numofsinger;//歌手的歌曲数
	private int numOfAlbum;//歌手的专辑数
	private String singername;//歌手姓名
	
	public int getSingerId() { //返回歌手id
		return singerid;
	}
	
	public void setSingerId(int singerid) { //设置歌手id
		this.singerid=singerid;
	}
	
	public int getNumOfSinger() { //返回歌曲数
		return numofsinger;
	}
	
	public void setNumOfSinger(int numofsinger) { //歌手歌曲数
		this.numofsinger=numofsinger;
	}
	
	public int getNumOfAlbum() { //返回专辑数
		return numOfAlbum;
	}
	
	public void setNumOfAlbum(int numOfAlbum) { //歌手专辑数
		this.numOfAlbum=numOfAlbum;
	}
	
	public String getSingerName() {  //返回歌手名
		return singername;
	}
	public void setSingerName(String singername) { //设置歌手名
		this.singername = singername;
	}
	
	
}
