package com.amazonmusic;

import java.util.List;

import com.adapter.MusicAdapter;
import com.amaon.getset.Music;
import com.util.MusicList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PlayingListActivity extends Activity {

	View view1;
	public static TextView song_num;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playing_total);
		song_num = (TextView) findViewById(R.id.allmusic_text2);
		view1 = findViewById(R.id.all_music_view);
		
		view1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(PlayingListActivity.this,
						SongActivity.class);
				startActivity(intent);
			}
		});		
		song_num.setText("共" + MusicList.getMusicData(this).size() + "首歌曲");
		
	}

	
}
