package niu.multimediastudy.chapter2;

import android.content.Intent;
import android.widget.Toast;

import niu.multimediastudy.base.ChapterBaseActivity;

/**
 * Created by Qinlian_niu on 2018/3/8.
 */

public class Chapter2Activity extends ChapterBaseActivity {
    @Override
    public void addItems() {
        listItems = new String[]{"使用Camera类", "定制延时拍摄的Camera应用程序"};
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(Chapter2Activity.this, Section1Activity.class);
                break;

            case 1:
                Toast.makeText(this, "只是加个Timer", Toast.LENGTH_SHORT).show();
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
