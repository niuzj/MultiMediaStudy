package niu.multimediastudy.chapter3;

import android.content.Intent;

import niu.multimediastudy.base.ChapterBaseActivity;

/**
 * Created by Qinlian_niu on 2018/3/8.
 */

public class Chapter3Activity extends ChapterBaseActivity {
    @Override
    public void addItems() {
        listItems = new String[]{"使用内置Gallery应用程序选择图像", "在位图上绘制位图",
                "Matrix", "ColorMatrix", "二图合一"};
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(Chapter3Activity.this, Section1Activity.class);
                break;

            case 1:
                intent = new Intent(Chapter3Activity.this, Section2Activity.class);
                break;

            case 2:
                intent = new Intent(Chapter3Activity.this, Section3Activity.class);
                break;

            case 3:
                intent = new Intent(Chapter3Activity.this, Section4Activity.class);
                break;

            case 4:
                intent = new Intent(Chapter3Activity.this, Section5Activity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
