package com.amazonmusic;

import java.util.ArrayList;
import java.util.List;

import com.adapter.ArtistSongAdapter;
import com.adapter.ArtistsAdapter;
import com.adapter.MusicAdapter;
import com.amaon.getset.Music;
import com.service.PlayerService;
import com.util.AlbumList;
import com.util.ArtistList;
import com.util.MusicList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class AlbumSongActivity extends Activity {
	//指定专辑里的所有歌曲list
	
	private ListView album_song_list;
	Context mContext;
	private int albumid; // 由前面传过来的歌手id
	private String albumname;
	private String albumsingername;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.artistsong);
		mContext = this;

		Intent intent = getIntent();
		albumid = intent.getExtras().getInt("album_id");// 获取专辑id
		albumname = intent.getExtras().getString("album_name"); //专辑名
		albumsingername= intent.getExtras().getString("album_singer_name");//专辑歌手名
		setTitle(albumname+"-"+albumsingername);

		// 加载属于该歌手的歌曲
		album_song_list = (ListView) findViewById(R.id.artist_song_ListView);
		ArtistSongAdapter adapter = new ArtistSongAdapter(this,
				AlbumList.getAlbumSongData(mContext, albumid));
		album_song_list.setAdapter(adapter);

		// 判断是否有数据
		/*
		 * if (ArtistList.getArtistData(mContext).size()>0) {
		 * Toast.makeText(ArtistSongActivity.this,
		 * "数据长度"+ArtistList.getArtistSongData(mContext,singerid).size(),
		 * Toast.LENGTH_LONG).show(); }else {
		 * Toast.makeText(ArtistSongActivity.this, "无数据",
		 * Toast.LENGTH_LONG).show(); }
		 */

		album_song_list.setOnItemClickListener(new OnItemClickListener() { //点击歌曲进行播放

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent album_song_PlayIntent=new Intent(AlbumSongActivity.this,  
						PlayActivity.class);
				startActivity(album_song_PlayIntent);
				
				Intent from_album = new Intent(AlbumSongActivity.this, // 点击开始播放
						PlayerService.class);
				from_album.putExtra("control", "albumsong_listClick");  //来自专辑列表
				from_album.putExtra("musicId_3", arg2);//歌曲在列表中的位置
				from_album.putExtra("albumid_1", albumid);////专辑号
				startService(from_album);

			}
		});

	}

}
