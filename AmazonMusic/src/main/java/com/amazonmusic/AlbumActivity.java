package com.amazonmusic;

import com.adapter.AlbumGvAdapter;
import com.adapter.ArtistsAdapter;
import com.util.AlbumList;
import com.util.ArtistList;
import com.util.MusicList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class AlbumActivity extends Activity {

    private GridView albumGridView;
    public static Context context;
    public AlbumActivity albumActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album);
        context = this;
        albumGridView = (GridView) findViewById(R.id.albumGridView);
        AlbumGvAdapter adapter = new AlbumGvAdapter(this, AlbumList.getAlbumData(context));
        albumGridView.setAdapter(adapter);

        //测试是否有数据
        if (AlbumList.getAlbumData(this).size() > 0) {
            Toast.makeText(AlbumActivity.this, "数据长度" + ArtistList.getArtistData(this).size(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(AlbumActivity.this, "无数据", Toast.LENGTH_LONG).show();
        }

        // gridview点击事件
        albumGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                int albumid = AlbumList.getAlbumData(context).get(arg2).getAlbumId();
                String albumname = AlbumList.getAlbumData(context).get(arg2).getAlbumName();
                String albumsingername = AlbumList.getAlbumData(context).get(arg2).getSingerName();

                Intent album_song_intent = new Intent(AlbumActivity.this, AlbumSongActivity.class);
                album_song_intent.putExtra("album_id", albumid);  //将歌手id传递过去
                album_song_intent.putExtra("album_name", albumname);
                album_song_intent.putExtra("album_singer_name", albumsingername);
                startActivity(album_song_intent);
            }
        });
    }
}
