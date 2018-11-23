package com.amazonmusic;

import java.io.IOException;
import java.util.List;

import com.adapter.MusicAdapter;
import com.amaon.getset.Music;
import com.service.PlayerService;
import com.util.ArtistList;
import com.util.MusicList;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SongActivity extends Activity {

	private ListView msongsList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listmusic);
		
		msongsList= (ListView) this.findViewById(R.id.listAllMusic);
		List<Music> listMusic=MusicList.getMusicData(getApplicationContext());
		MusicAdapter adapter=new MusicAdapter(this, listMusic);
		msongsList.setAdapter(adapter);
		
				
		msongsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				Intent startPlayIntent=new Intent(SongActivity.this,   //点击开始播放
						PlayActivity.class);
				startActivity(startPlayIntent);
				
				Intent playing = new Intent(SongActivity.this,   //点击开始播放
				PlayerService.class);
				playing.putExtra("control", "song_listClick");
				playing.putExtra("musicId_1", arg2);//点击歌曲所在位置
				startService(playing);
				
			}
		});
		
		
	}
		
}
