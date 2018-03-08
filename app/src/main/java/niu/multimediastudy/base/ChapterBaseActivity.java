package niu.multimediastudy.base;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import niu.multimediastudy.R;
import niu.multimediastudy.databinding.ActivityChapterBinding;

public abstract class ChapterBaseActivity extends BaseActivity {

    public ActivityChapterBinding mBinding;
    public String[] listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chapter);
        addItems();
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item, listItems);
        mBinding.listview.setAdapter(adapter);
        mBinding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChapterBaseActivity.this.onItemClick(position);
            }
        });
    }

    public abstract void addItems();

    public abstract void onItemClick(int position);

}
