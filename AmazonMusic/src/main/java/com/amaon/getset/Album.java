package com.amaon.getset;

public class Album  {
  
	private int albumid;   //专辑 id	
	private int numOfSong;//专辑里的歌曲数
	private String albumname;//专辑名
	private String singername;//歌手名
	private String albumart;//专辑图片
	
	public int getAlbumId() { //Album id
		return albumid;
	}
	
	public void setAlbumId(int albumid) { //id
		this.albumid=albumid;
	}
	
	public String getAlbumArt() { //Album pic
		return albumart;
	}
	
	public void setAlbumArt(String albumart) { //id
		this.albumart=albumart;
	}
	
	public int getSongNumOfAlbum() { //返回歌曲数
		return numOfSong;
	}
	
	public void setSongNumOfAlbum(int numOfSong) { //专辑里的歌曲数
		this.numOfSong=numOfSong;
	}
	
	public String getAlbumName() {  //返回专辑名
		return albumname;
	}
	public void setAlbumName(String albumname) { //设置专辑名
		this.albumname = albumname;
	}
	
	public String getSingerName() {  //返回歌手名
		return singername;
	}
	public void setSingerName(String singername) { //设置歌手名
		this.singername = singername;
	}
}
