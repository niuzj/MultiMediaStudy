package niu.multimediastudy.base;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import niu.multimediastudy.R;
import niu.multimediastudy.databinding.ActivitySectionBinding;

/**
 * Created by Qinlian_niu on 2018/3/6.
 */

public abstract class SectionBaseActivity extends BaseActivity {

    public ActivitySectionBinding mBinding;
    public String[] listItems = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_section);
        addItems();
        addButton();
    }

    public abstract void addItems();

    private void addButton() {
        if (listItems != null && listItems.length > 0) {
            for (int i = 0; i < listItems.length; i++) {
                AppCompatButton button = new AppCompatButton(this);
                button.setSupportAllCaps(false);
                button.setText(listItems[i]);
                button.setTag(listItems[i]);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SectionBaseActivity.this.onClick((String) v.getTag());
                    }
                });
                mBinding.layout.addView(button);
            }
        }
    }

    public abstract void onClick(String tag);

}
