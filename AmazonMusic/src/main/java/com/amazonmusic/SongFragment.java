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

import com.adapter.MusicAdapter;
import com.amaon.getset.Music;
import com.service.PlayerService;
import com.util.MusicList;

import java.util.List;

public class SongFragment extends BaseFragment {
    private ListView msongsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listmusic, container, false);

        msongsList = (ListView) view.findViewById(R.id.listAllMusic);
        List<Music> listMusic = MusicList.getMusicData(mContext);
        MusicAdapter adapter = new MusicAdapter(mContext, listMusic);
        msongsList.setAdapter(adapter);

        msongsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                //点击开始播放
                Intent startPlayIntent = new Intent(mContext,
                        PlayActivity.class);
                startActivity(startPlayIntent);
                //点击开始播放
                Intent playing = new Intent(mContext, PlayerService.class);
                playing.putExtra("control", "song_listClick");
                playing.putExtra("musicId_1", arg2);//点击歌曲所在位置
                mContext.startService(playing);
            }
        });
        return view;
    }
}
