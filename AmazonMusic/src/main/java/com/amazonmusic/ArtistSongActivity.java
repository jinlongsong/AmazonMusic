package com.amazonmusic;

import java.util.ArrayList;
import java.util.List;

import com.adapter.ArtistSongAdapter;
import com.adapter.ArtistsAdapter;
import com.adapter.MusicAdapter;
import com.amaon.getset.Music;
import com.service.PlayerService;
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

public class ArtistSongActivity extends Activity {
	//歌手名下的所有歌曲的列表
	
	private ListView artist_song_list;
	Context mContext;
	private int singerid; // 由前面传过来的歌手id
	private String singername;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.artistsong);
		mContext = this;

		Intent intent = getIntent();
		singerid = intent.getExtras().getInt("singer_id");// 获取歌手id
		singername = intent.getExtras().getString("artist_name");
		setTitle(singername);

		// 加载属于该歌手的歌曲
		artist_song_list = (ListView) findViewById(R.id.artist_song_ListView);
		ArtistSongAdapter adapter = new ArtistSongAdapter(this,
				ArtistList.getArtistSongData(mContext, singerid));
		artist_song_list.setAdapter(adapter);

		// 判断是否有数据
		
		  if (ArtistList.getArtistData(mContext).size()>0) {
		  Toast.makeText(ArtistSongActivity.this,
		  "数据长度"+ArtistList.getArtistSongData(mContext,singerid).size(),
		  Toast.LENGTH_LONG).show(); }else {
		  Toast.makeText(ArtistSongActivity.this, "无数据",
		  Toast.LENGTH_LONG).show(); }
		 

		artist_song_list.setOnItemClickListener(new OnItemClickListener() {  //点击进行播放

			/* (non-Javadoc)
			 * arg2 为所单击的歌曲在list中的位置，根据此参数确定调用list中的拿一首歌曲
			 * list.get(arg2)为歌曲对象
			 */
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				//List<Music> list=ArtistList.getArtistSongData(mContext, singerid); //获取该歌手的歌曲列表
				
				Intent artist_song_PlayIntent=new Intent(ArtistSongActivity.this,   //点击开始播放
						PlayActivity.class);
				startActivity(artist_song_PlayIntent);
				
				Intent from_artist = new Intent(ArtistSongActivity.this, // 点击开始播放
						PlayerService.class);
				from_artist.putExtra("control", "artistsong_listClick");
				from_artist.putExtra("musicId_2", arg2);//获取歌曲列表中歌曲位置
				from_artist.putExtra("singerid_1", singerid);//获取歌曲列表中歌曲id
				startService(from_artist);

			}
		});

	}

}
