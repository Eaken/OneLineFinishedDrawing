package com.example.onelinetoend.Util.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.onelinetoend.R;
import com.example.onelinetoend.Util.ValueUtil;

import androidx.annotation.Nullable;

public class MusicService extends Service {

    private MediaPlayer mediaPlayer;
    private boolean isStop = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final int op;
        if(intent==null) op = 2;
        else {
            op = intent.getIntExtra(ValueUtil.key_toPlayMusic,2);
        }
        switch (op){
            case 0:
                if(mediaPlayer==null || isStop){
                    if(mediaPlayer!=null) mediaPlayer.release();
                    mediaPlayer = MediaPlayer.create(this, R.raw.bgm);
                    mediaPlayer.setLooping(true);
                }
                mediaPlayer.start();
                isStop = false;
                break;
            case 1:
                if(mediaPlayer!=null){
                    mediaPlayer.pause();
                    isStop = false;
                }
                break;
             default:
                 if(mediaPlayer!=null){
                     mediaPlayer.stop();
                     isStop = true;
                 }
                 break;
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}
