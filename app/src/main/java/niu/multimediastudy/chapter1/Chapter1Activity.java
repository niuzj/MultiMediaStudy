package niu.multimediastudy.chapter1;

import android.content.Intent;

import niu.multimediastudy.base.ChapterBaseActivity;

public class Chapter1Activity extends ChapterBaseActivity {

    @Override
    public void addItems() {
        listItems = new String[]{"使用内置的Camera应用程序捕获图像",
                "初识ContentResolver",
                "图像存储和元数据"};
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(Chapter1Activity.this, Section1Activity.class);
                break;

            case 1:
                intent = new Intent(Chapter1Activity.this, Section2Activity.class);
                break;

            case 2:
                intent = new Intent(Chapter1Activity.this, Section3Activity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
