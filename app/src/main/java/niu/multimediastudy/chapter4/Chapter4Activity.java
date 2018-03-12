package niu.multimediastudy.chapter4;

import android.content.Intent;

import niu.multimediastudy.base.ChapterBaseActivity;

/**
 * Created by Qinlian_niu on 2018/3/9.
 */

public class Chapter4Activity extends ChapterBaseActivity {
    @Override
    public void addItems() {
        listItems = new String[]{"手势轨迹", "在现有图片上绘制"};
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(Chapter4Activity.this, Section1Activity.class);
                break;

            case 1:
                intent = new Intent(Chapter4Activity.this, Section2Activity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
