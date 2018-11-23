package com.util;

import java.util.ArrayList;
import java.util.List;

import com.amaon.getset.Artist;
import com.amaon.getset.Music;
import com.amazonmusic.ArtistActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

public class ArtistList { // 返回的是歌手列表

	/**
	 * @param context 上下文对象
	 * @return 返回歌手列表
	 */
	public static List<Artist> getArtistData(Context context) {
		List<Artist> artistList = new ArrayList<Artist>();

		ContentResolver cr1 = context.getContentResolver();// 获取ContentResolver对象，该对象用于操作多媒体数据
		if (cr1 != null) {
			// 查询多媒体库
			Cursor cursor = cr1.query(
					MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, null, null,
					null, MediaStore.Audio.Artists.DEFAULT_SORT_ORDER); // 查询歌手信息
			if (null == cursor) {
				return null;
			}
			try {
				if (cursor.moveToFirst()) {
					do {
						Artist artist = new Artist();

						/*
						 * String title = cursor.getString(cursor
						 * .getColumnIndex(MediaStore.Audio.Media.TITLE));// 歌曲名
						 */
						int singerid = cursor.getInt(cursor
							.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID));// 歌手id
						String singername = cursor.getString(cursor
							.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST)); // 歌手姓名
						int numOfAlbum = cursor.getInt(cursor
							.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS));// 共有多少该歌手的专辑
						int numOfSong = cursor.getInt(cursor
							.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_TRACKS)); // 共有多少该歌手的歌曲

						
						artist.setSingerId(singerid); // 歌手id
						artist.setSingerName(singername);// 歌手名
						artist.setNumOfSinger(numOfSong);// 歌曲数
						artist.setNumOfAlbum(numOfAlbum);// 专辑数
						artistList.add(artist); // 循环添加歌曲信息

					} while (cursor.moveToNext());
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return artistList;

	}
	
	/**
	 * @param context //上下文对象
	 * @param singerid //歌手id
	 * @return 返回的是属于指定歌手的歌曲列表
	 */
	public static List<Music> getArtistSongData(Context context,int singerid) {		
		List<Music> artistSongList = new ArrayList<Music>();
		ContentResolver cr2 = context.getContentResolver();// 获取ContentResolver对象，该对象用于操作多媒体数据
		if (cr2!=null) {
			Cursor cursor=cr2.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,  null ,  
		             MediaStore.Audio.Media.ARTIST_ID + "="  + singerid,  null ,  
		             MediaStore.Audio.Media.TITLE);  //查询属于指定歌手（歌手id 为 aid）的歌曲：
			if (null == cursor) {
				return null;
			}
			try {
				if (cursor.moveToFirst()) {  //为true表示有数据
					do {
						Music m=new Music();//获取音乐对象
						
						String title = cursor.getString(cursor
								.getColumnIndex(MediaStore.Audio.Media.TITLE));// 歌曲名
						String singer = cursor.getString(cursor
								.getColumnIndex(MediaStore.Audio.Media.ARTIST));// 歌手名
						if ("<unknown>".equals(singer)) {
							singer = "<unknown>";
						}
						String album = cursor.getString(cursor
								.getColumnIndex(MediaStore.Audio.Media.ALBUM));// 专辑名
						long size = cursor.getLong(cursor
								.getColumnIndex(MediaStore.Audio.Media.SIZE));// 歌曲大小
						int time = cursor.getInt(cursor
								.getColumnIndex(MediaStore.Audio.Media.DURATION));// 歌曲时长
						String url = cursor.getString(cursor
								.getColumnIndex(MediaStore.Audio.Media.DATA));// 路径
						String name = cursor.getString(cursor
								.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));// //(歌手--歌曲名)
				
						int albumid=cursor.getInt(cursor
								.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));//专辑号
						String sbr = name.substring(name.length() - 3, // 后缀类型
								name.length());
						
						if (sbr.equals("mp3")) { // 设置music类的值
							m.setTitle(title);
							m.setSinger(singer);
							m.setAlbum(album);
							m.setSize(size);
							m.setTime(time);
							m.setUrl(url);
							m.setName(name);  
							m.setAlbumId(albumid);
							artistSongList.add(m);
						}
						
					} while (cursor.moveToNext()); //cursor.moveToNext()是用来做循环的										
				}
			} catch (Exception e) {
				// TODO: handle exception
			}			
		} 		 
		 return artistSongList;
	}	

}
