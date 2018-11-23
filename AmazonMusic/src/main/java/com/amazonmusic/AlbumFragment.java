package com.amazonmusic;

import com.adapter.AlbumGvAdapter;
import com.adapter.RecycleViewAdapter;
import com.util.AlbumList;
import com.util.ArtistList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AlbumFragment extends BaseFragment {
    private GridView albumGridView;
    public AlbumActivity albumActivity;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.album, container, false);
        //albumGridView = (GridView) view.findViewById(R.id.albumGridView);
        //AlbumGvAdapter adapter = new AlbumGvAdapter(mContext, AlbumList.getAlbumData(mContext));
        //albumGridView.setAdapter(adapter);
        recyclerView = (RecyclerView)view.findViewById(R.id.albumRecycleView);
        RecycleViewAdapter adapter = new RecycleViewAdapter(mContext, AlbumList.getAlbumData(mContext));
        recyclerView.setAdapter(adapter);

        //测试是否有数据
        if (AlbumList.getAlbumData(mContext).size() > 0) {
            Toast.makeText(mContext, "数据长度" + ArtistList.getArtistData(mContext).size(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, "无数据", Toast.LENGTH_LONG).show();
        }

//        // gridview点击事件
//        albumGridView.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                    long arg3) {
//
//                int albumid = AlbumList.getAlbumData(mContext).get(arg2).getAlbumId();
//                String albumname = AlbumList.getAlbumData(mContext).get(arg2).getAlbumName();
//                String albumsingername = AlbumList.getAlbumData(mContext).get(arg2).getSingerName();
//
//                Intent album_song_intent = new Intent(mContext, AlbumSongActivity.class);
//                album_song_intent.putExtra("album_id", albumid);  //将歌手id传递过去
//                album_song_intent.putExtra("album_name", albumname);
//                album_song_intent.putExtra("album_singer_name", albumsingername);
//                startActivity(album_song_intent);
//            }
//        });

        return view;
    }
}
