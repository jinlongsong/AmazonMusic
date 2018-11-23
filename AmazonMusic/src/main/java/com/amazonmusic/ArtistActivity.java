package com.amazonmusic;

import java.util.ArrayList;
import java.util.List;

import com.adapter.ArtistsAdapter;
import com.amaon.getset.Artist;
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

public class ArtistActivity extends Activity {
	//歌手列表  显示全部歌手
	
	private ListView artistListView;
	Context mContext;
	private ArtistsAdapter adapter;
	private int singerid;
	private String singername;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.artist);		
		mContext=this;
		
		
		artistListView=(ListView)findViewById(R.id.artistListView);				
		adapter=new ArtistsAdapter(this, ArtistList.getArtistData(mContext));
		artistListView.setAdapter(adapter);		
		
		//测试是否有数据
		if (ArtistList.getArtistData(mContext).size()>0) {
			Toast.makeText(ArtistActivity.this, "数据长度"+ArtistList.getArtistData(this).size(), Toast.LENGTH_LONG).show();
		}else {
			Toast.makeText(ArtistActivity.this, "无数据", Toast.LENGTH_LONG).show();
		}
		
		
		artistListView.setOnItemClickListener(new OnItemClickListener() {  //点击进入歌手的歌曲列表

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			
				singerid=ArtistList.getArtistData(mContext).get(arg2).getSingerId();
				singername=ArtistList.getArtistData(mContext).get(arg2).getSingerName();
				
				Intent artist_song_intent=new Intent(ArtistActivity.this,ArtistSongActivity.class);
				artist_song_intent.putExtra("singer_id", singerid);  //将歌手id传递过去
				artist_song_intent.putExtra("artist_name", singername);
				startActivity(artist_song_intent);
				adapter.notifyDataSetChanged();
				
			}
		});
		
	
	}

	
}
