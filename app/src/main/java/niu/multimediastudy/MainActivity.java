package niu.multimediastudy;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import niu.multimediastudy.chapter1.Chapter1Activity;
import niu.multimediastudy.chapter2.Chapter2Activity;
import niu.multimediastudy.chapter3.Chapter3Activity;
import niu.multimediastudy.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;
    String[] listItems = new String[]{"Android图像概述", "构建定制的Camera应用程序", "图像编辑与处理"};

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
                }
                if (intent != null) {
                    startActivity(intent);
                }
            }
        });
    }
}
