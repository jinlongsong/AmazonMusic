package com.amaon.getset;

public class Music  {
  
	private String title;
	private String singer;
	private String album;
	private int albumid;
	private String url;
	private long size;
	private int time;
	private String name;//歌曲名
	private int numofsinger;//歌手的歌曲数
	private int numOfAlbum;//歌手的专辑数
	private String singername;//歌手姓名
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	
	public String getSingerName() {
		return singername;
	}
	public void setSingerName(String singername) { //设置歌手名
		this.singername = singername;
	}
	
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public int getAlbumId() {
		return albumid;
	}
	public void setAlbumId(int albumid) {
		this.albumid = albumid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
}
