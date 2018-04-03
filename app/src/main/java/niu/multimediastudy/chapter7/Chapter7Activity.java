package niu.multimediastudy.chapter7;

import android.content.Intent;

import niu.multimediastudy.base.ChapterBaseActivity;

/**
 * Created by Qinlian_niu on 2018/3/19.
 */

public class Chapter7Activity extends ChapterBaseActivity {
    @Override
    public void addItems() {
        listItems = new String[]{"播放合成声音", "音频分析"};
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
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
