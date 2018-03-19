package niu.multimediastudy.chapter6;

import android.content.Intent;

import niu.multimediastudy.base.ChapterBaseActivity;

/**
 * Created by Qinlian_niu on 2018/3/15.
 */

public class Chapter6Activity extends ChapterBaseActivity {
    @Override
    public void addItems() {
        listItems = new String[]{"通过意图捕获音频", "MediaRecorder捕获音频", "AudioRecord录制原始音频"};
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(this, Section1Activity.class);
                break;

            case 1:
                intent = new Intent(this, Section2Activity.class);
                break;

            case 2:
                intent = new Intent(this, Section3Activity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
