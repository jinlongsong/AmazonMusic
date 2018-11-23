package com.amazonmusic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.util.MusicList;

public class PlayingListFragment extends BaseFragment {
    private View view1;
    private TextView song_num;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.playing_total, container, false);
        song_num = (TextView) view.findViewById(R.id.allmusic_text2);
        view1 = view.findViewById(R.id.all_music_view);

        view1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, SongActivity.class);
                startActivity(intent);
            }
        });
        song_num.setText("共" + MusicList.getMusicData(mContext).size() + "首歌曲");
        return view;
    }
}
