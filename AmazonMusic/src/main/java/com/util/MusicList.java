package com.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.amaon.getset.Music;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;

public class MusicList { 
	// 返回的是Musicist列表
	//获取专辑封面的Uri 
	private static final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart"); 
	private static final HashMap<Long, Bitmap> sArtCache = new HashMap<Long, Bitmap>();
	private static final BitmapFactory.Options sBitmapOptionsCache = new BitmapFactory.Options();
	private static final BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();
	
	public static List<Music> getMusicData(Context context) {
		List<Music> musicList = new ArrayList<Music>();
		ContentResolver cr = context.getContentResolver();// 获取ContentResolver对象，该对象用于操作多媒体数据
		if (cr != null) {
			// 查询多媒体库				
			Cursor cursor = cr.query(  //查询所有歌曲
					MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, // EXTERNAL_CONTENT_URI为存储在外部存储器上的音频文件内容的contentprovider路径
					null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER); // INTERNAL_CONTENT_URI为存储在手机内部的音频文件内容的contentprovider路径
			if (null == cursor) {
				return null;
			}
			try {
				if (cursor.moveToFirst()) {
					do {
						Music m = new Music();
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
						// Log.e("--------------", sbr);
						if (sbr.equals("mp3")) { // 设置music类的值
							m.setTitle(title);
							m.setSinger(singer);
							m.setAlbum(album);
							m.setSize(size);
							m.setTime(time);
							m.setAlbumId(albumid);
							m.setUrl(url);
							m.setName(name);//最后修改处  setName(name)--->setName(title)

							musicList.add(m); // 循环添加歌曲信息
						}
					} while (cursor.moveToNext());
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return musicList;

	}
	
	public static Bitmap getCachedArtwork(Context context, long artIndex,
			Bitmap defaultArtwork) {
		Bitmap bitmap = null;
		synchronized (sArtCache) {
			bitmap = sArtCache.get(artIndex);
		}
		if(context == null) {
			return null;
		}
		if (bitmap == null) {
			bitmap = defaultArtwork;
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();
			Bitmap b = MusicList.getArtworkQuick(context, artIndex, w, h);
			if (b != null) {
				bitmap = b;
				synchronized (sArtCache) {
					// the cache may have changed since we checked
					Bitmap value = sArtCache.get(artIndex);
					if (value == null) {
						sArtCache.put(artIndex, bitmap);
					} else {
						bitmap = value;
					}
				}
			}
		}
		return bitmap;
	}
	
	public static Bitmap getArtworkQuick(Context context, long album_id, int w,
			int h) {		
		w -= 1;
		ContentResolver res = context.getContentResolver();
		Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
		if (uri != null) {
			ParcelFileDescriptor fd = null;
			try {
				fd = res.openFileDescriptor(uri, "r");
				int sampleSize = 1;

				// Compute the closest power-of-two scale factor
				// and pass that to sBitmapOptionsCache.inSampleSize, which will
				// result in faster decoding and better quality
				sBitmapOptionsCache.inJustDecodeBounds = true;
				BitmapFactory.decodeFileDescriptor(fd.getFileDescriptor(),
						null, sBitmapOptionsCache);
				int nextWidth = sBitmapOptionsCache.outWidth >> 1;
				int nextHeight = sBitmapOptionsCache.outHeight >> 1;
				while (nextWidth > w && nextHeight > h) {
					sampleSize <<= 1;
					nextWidth >>= 1;
					nextHeight >>= 1;
				}

				sBitmapOptionsCache.inSampleSize = sampleSize;
				sBitmapOptionsCache.inJustDecodeBounds = false;
				Bitmap b = BitmapFactory.decodeFileDescriptor(
						fd.getFileDescriptor(), null, sBitmapOptionsCache);

				if (b != null) {
					// finally rescale to exactly the size we need
					if (sBitmapOptionsCache.outWidth != w
							|| sBitmapOptionsCache.outHeight != h) {
						Bitmap tmp = Bitmap.createScaledBitmap(b, w, h, true);
						// Bitmap.createScaledBitmap() can return the same
						// bitmap
						if (tmp != b)
							b.recycle();
						b = tmp;
					}
				}
				return b;
			} catch (FileNotFoundException e) {
			} finally {
				try {
					if (fd != null)
						fd.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}
	
}
