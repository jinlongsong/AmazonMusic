package com.util;

import java.util.ArrayList;
import java.util.List;

import com.amaon.getset.Album;
import com.amaon.getset.Music;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

public class AlbumList { // 返回的是Musicist列表

	/**
	 * @param context 上下文对象
	 * @return 返回专辑列表
	 */
	public static List<Album> getAlbumData(Context context) {
		List<Album> albumList = new ArrayList<Album>();

		ContentResolver cr = context.getContentResolver();// 获取ContentResolver对象，该对象用于操作多媒体数据
		if (cr != null) {
			// 查询多媒体库
			 Cursor cursor = cr.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,  null ,  null , null ,  
					 MediaStore.Audio.Albums.DEFAULT_SORT_ORDER);// 查询专辑信息

			if (null == cursor) {
				return null;
			}
			try {
				if (cursor.moveToFirst()) {
					do {
						Album album = new Album();

						int albumid = cursor.getInt(cursor
							.getColumnIndexOrThrow(MediaStore.Audio.Albums._ID)); // 专辑id
						String albumname = cursor.getString(cursor
							.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM)); // 专辑名称
						int numOfSong = cursor.getInt(cursor
							.getColumnIndexOrThrow(MediaStore.Audio.Albums.NUMBER_OF_SONGS));// 共用多少歌曲属于该专辑
						String singername=cursor.getString(cursor
								.getColumnIndexOrThrow(MediaStore.Audio.Albums.ARTIST)); // 歌手名
						String albumart=cursor.getString(cursor
								.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART)); // 专辑图片
						
						
						album.setAlbumId(albumid);
						album.setAlbumName(albumname);
						album.setAlbumArt(albumart);
						album.setSongNumOfAlbum(numOfSong);
						album.setSingerName(singername);
						
						albumList.add(album); // 循环添加歌曲信息

					} while (cursor.moveToNext());
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return albumList;

	}
	
	/**
	 * @param context //上下文对象
	 * @param albumid //专辑id
	 * @return 返回的是属于指定专辑的歌曲列表
	 */
	public static List<Music> getAlbumSongData(Context context,int albumid) {		
		List<Music> albumSongList = new ArrayList<Music>();
		ContentResolver cr2 = context.getContentResolver();// 获取ContentResolver对象，该对象用于操作多媒体数据
		if (cr2!=null) {
			Cursor cursor=cr2.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,  null ,  
	                MediaStore.Audio.Media.ALBUM_ID + "="  + albumid,  null ,  
	                MediaStore.Audio.Media.TITLE);  //查询属于指定专辑（专辑id 为 aid）的歌曲：
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
								.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));//(歌手--歌曲名)
						int albumid2=cursor.getInt(cursor
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
							m.setAlbumId(albumid2);
							albumSongList.add(m);
						}
						
					} while (cursor.moveToNext()); //cursor.moveToNext()是用来做循环的										
				}
			} catch (Exception e) {
				// TODO: handle exception
			}			
		} 		 
		 return albumSongList;
	}	

}
