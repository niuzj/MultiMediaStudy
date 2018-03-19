package niu.multimediastudy.chapter5;

import android.content.Intent;

import java.io.File;

import niu.multimediastudy.R;
import niu.multimediastudy.base.ChapterBaseActivity;
import niu.multimediastudy.util.FileUtil;

/**
 * Created by Qinlian_niu on 2018/3/12.
 */

public class Chapter5Activity extends ChapterBaseActivity {
    @Override
    public void addItems() {
        listItems = new String[]{"通过意图使用内置的音频播放器", "用于音频的MediaStore", "后台音频播放"};
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                //注意这里应该不能播放raw目录下的音频
                FileUtil fileUtil = new FileUtil();
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(fileUtil.getUri("kiss.mp3", Chapter5Activity.this), "audio/mp3");
                break;

            case 1:
                intent = new Intent(Chapter5Activity.this, Section1Activity.class);
                break;

            case 2:
                intent = new Intent(Chapter5Activity.this, Section2Activity.class);
                break;

        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
