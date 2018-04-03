package niu.multimediastudy;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.io.File;

import niu.multimediastudy.chapter1.Chapter1Activity;
import niu.multimediastudy.chapter2.Chapter2Activity;
import niu.multimediastudy.chapter3.Chapter3Activity;
import niu.multimediastudy.chapter4.Chapter4Activity;
import niu.multimediastudy.chapter5.Chapter5Activity;
import niu.multimediastudy.chapter6.Chapter6Activity;
import niu.multimediastudy.chapter7.Chapter7Activity;
import niu.multimediastudy.databinding.ActivityMainBinding;
import niu.multimediastudy.util.FileUtil;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;
    String[] listItems = new String[]{"1、Android图像概述", "2、构建定制的Camera应用程序", "3、图像编辑与处理", "4、图形和触摸事件",
            "5、音频概述", "6、音频捕获", "7、音频合成与分析", "临时功能"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item, listItems);
        mBinding.listview.setAdapter(adapter);
        mBinding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(MainActivity.this, Chapter1Activity.class);
                        break;

                    case 1:
                        intent = new Intent(MainActivity.this, Chapter2Activity.class);
                        break;

                    case 2:
                        intent = new Intent(MainActivity.this, Chapter3Activity.class);
                        break;

                    case 3:
                        intent = new Intent(MainActivity.this, Chapter4Activity.class);
                        break;

                    case 4:
                        intent = new Intent(MainActivity.this, Chapter5Activity.class);
                        break;

                    case 5:
                        intent = new Intent(MainActivity.this, Chapter6Activity.class);
                        break;

                    case 6:
                        intent = new Intent(MainActivity.this, Chapter7Activity.class);
                        break;

                    case 7:
                        FileUtil fileUtil = new FileUtil();
                        fileUtil.printFile();
                        break;
                }
                if (intent != null) {
                    startActivity(intent);
                }
            }
        });
    }
}
