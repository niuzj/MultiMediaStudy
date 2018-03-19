package niu.multimediastudy.chapter5;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import niu.multimediastudy.base.SectionBaseActivity;

/**
 * Created by Qinlian_niu on 2018/3/15.
 */

public class Section2Activity extends SectionBaseActivity {

    private Intent audioService;
    private Service bindService;
    private ServiceConnection mServiceConnection;

    @Override
    public void addItems() {
        listItems = new String[]{"开启服务", "结束服务", "绑定服务", "解绑服务", "暂停播放", "重新播放"};
    }

    @Override
    public void onClick(String tag) {
        switch (tag) {
            case "开启服务":
                if (audioService == null) {
                    audioService = new Intent(this, BgAudioService.class);
                    startService(audioService);
                }
                break;

            case "结束服务":
                if (audioService != null) {
                    stopService(audioService);
                    audioService = null;
                }
                break;

            case "绑定服务":
                if (audioService == null) {
                    audioService = new Intent(this, BgAudioBindService.class);
                    mServiceConnection = new MyServiceConn();
                    bindService(audioService, mServiceConnection, BIND_AUTO_CREATE);
                }
                break;

            case "解绑服务":
                if (audioService != null && mServiceConnection != null) {
                    unbindService(mServiceConnection);
                    mServiceConnection = null;
                    audioService = null;
                    bindService = null;
                }
                break;

            case "暂停播放":
                if (bindService != null) {
                    ((BgAudioBindService) bindService).pause();
                }
                break;

            case "重新播放":
                if (bindService != null) {
                    ((BgAudioBindService) bindService).start();
                }
                break;
        }
    }

    public class MyServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bindService = ((BgAudioBindService.MyBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

}
