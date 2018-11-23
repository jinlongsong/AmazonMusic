package com.amazonmusic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.adapter.ArtistsAdapter;
import com.util.ArtistList;

public class ArtistFragment extends BaseFragment {
    //歌手列表  显示全部歌手
    private ListView artistListView;
    private ArtistsAdapter adapter;
    private int singerid;
    private String singername;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.artist, container, false);

        artistListView = (ListView) view.findViewById(R.id.artistListView);
        adapter = new ArtistsAdapter(mContext, ArtistList.getArtistData(mContext));
        artistListView.setAdapter(adapter);

        //测试是否有数据
        if (ArtistList.getArtistData(mContext).size() > 0) {
            Toast.makeText(mContext, "数据长度" + ArtistList.getArtistData(mContext).size(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, "无数据", Toast.LENGTH_LONG).show();
        }


        artistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  //点击进入歌手的歌曲列表

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                singerid = ArtistList.getArtistData(mContext).get(arg2).getSingerId();
                singername = ArtistList.getArtistData(mContext).get(arg2).getSingerName();

                Intent artist_song_intent = new Intent(mContext, ArtistSongActivity.class);
                artist_song_intent.putExtra("singer_id", singerid);  //将歌手id传递过去
                artist_song_intent.putExtra("artist_name", singername);
                startActivity(artist_song_intent);
                adapter.notifyDataSetChanged();

            }
        });
        return view;
    }
}
