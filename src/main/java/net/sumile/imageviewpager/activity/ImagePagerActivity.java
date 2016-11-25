package net.sumile.imageviewpager.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.sumile.imageviewpager.R;
import net.sumile.imageviewpager.fragments.ImageDetailFragment;
import net.sumile.imageviewpager.views.HackyViewPager;

import java.util.ArrayList;

/**
 * 图片查看器
 */
public class ImagePagerActivity extends FragmentActivity {

    private static OnSelectedListener mCallBack;
    private HackyViewPager mPager;
    private int pagerPosition;
    private TextView indicator;
    private int type = 0;
    private static final String TYPE = "TYPE";
    private RelativeLayout header;
    private RelativeLayout footer;
    private ImageView back;
    private TextView count;
    private TextView yes;
    private CheckBox choose;
    private int currentPage;

    public static void startActivity(Context context, int index, ArrayList<String> urls) {
        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putExtra(STATE_POSITION, index);
        intent.putExtra(EXTRA_IMAGE_URLS, urls);
        intent.putExtra(TYPE, 0);
        context.startActivity(intent);
    }

    /**
     * 显示一个包含头部和尾部的选择器，初始默认全选中（预览）
     */
    public static void startActivityWithHF(Context context, int index, ArrayList<String> urls, OnSelectedListener callBack) {
        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putExtra(STATE_POSITION, index);
        intent.putExtra(EXTRA_IMAGE_URLS, urls);
        intent.putExtra(TYPE, 1);
        mCallBack = callBack;
        context.startActivity(intent);
    }

    /**
     * 显示一个包含头部和尾部的选择器，初始默认全选中（浏览）
     */
    public static void startActivityWithHF_NotSelected(Context context, int index, ArrayList<String> urls, OnSelectedListener callBack) {
        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putExtra(STATE_POSITION, index);
        intent.putExtra(EXTRA_IMAGE_URLS, urls);
        intent.putExtra(TYPE, 2);
        mCallBack = callBack;
        context.startActivity(intent);
    }

    public static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    ArrayList<Boolean> statusList = new ArrayList<>();

    private void createStatusList(ArrayList<String> dataList) {
        for (int i = 0; i < dataList.size(); i++) {
            statusList.add(true);
        }
    }

    private void createStatusListFalse(ArrayList<String> dataList) {
        for (int i = 0; i < dataList.size(); i++) {
            statusList.add(false);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_detail_pager);
        initView();
        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        currentPage = pagerPosition;
        final ArrayList<String> urls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);
        mPager = (HackyViewPager) findViewById(R.id.pager);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urls);
        mPager.setAdapter(mAdapter);
        indicator = (TextView) findViewById(R.id.indicator);
        type = getIntent().getIntExtra(TYPE, 0);
        if (type == 1) {
            createStatusList(urls);
            footer.setVisibility(View.VISIBLE);
            header.setVisibility(View.VISIBLE);
            indicator.setVisibility(View.GONE);
            choose.setChecked(true);
            choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = choose.isChecked();
                    if (mCallBack != null) {
                        statusList.set(currentPage, isChecked);
                        yes.setText("确定(" + getSelectedCount() + "/" + urls.size() + ")");
                        mCallBack.onSelected(isChecked, urls.get(currentPage));
                    }
                }
            });
            count.setText((currentPage + 1) + "/" + urls.size());
            yes.setText("确定(" + urls.size() + "/" + urls.size() + ")");
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else if (type == 2) {
            createStatusListFalse(urls);
            footer.setVisibility(View.VISIBLE);
            header.setVisibility(View.VISIBLE);
            indicator.setVisibility(View.GONE);
            choose.setChecked(false);
            choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = choose.isChecked();
                    if (mCallBack != null) {
                        statusList.set(currentPage, isChecked);
                        yes.setText("确定(" + getSelectedCount() + "/" + urls.size() + ")");
                        mCallBack.onSelected(isChecked, urls.get(currentPage));
                    }
                }
            });
            count.setText((currentPage + 1) + "/" + urls.size());
            yes.setText("确定(" + urls.size() + "/" + urls.size() + ")");
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            footer.setVisibility(View.GONE);
            header.setVisibility(View.GONE);
            indicator.setVisibility(View.VISIBLE);
        }


        final CharSequence text = getString(R.string.viewpager_indicator, 1, mPager.getAdapter().getCount());
        indicator.setText(text);
        // 更新下标
        mPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                if (type == 1 || type == 2) {
                    currentPage = arg0;
                    if (statusList.get(currentPage)) {
                        choose.setChecked(true);
                    } else {
                        choose.setChecked(false);
                    }
                    count.setText((currentPage + 1) + "/" + urls.size());
                    yes.setText("确定(" + getSelectedCount() + "/" + urls.size() + ")");
                } else {
                    CharSequence text = getString(R.string.viewpager_indicator, arg0 + 1, mPager.getAdapter().getCount());
                    indicator.setText(text);
                }
            }

        });
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        mPager.setCurrentItem(pagerPosition);
    }

    private int getSelectedCount() {
        int count = 0;
        for (boolean is : statusList) {
            if (is) {
                count++;
            }
        }
        return count;
    }

    private void initView() {
        header = (RelativeLayout) findViewById(R.id.header);
        footer = (RelativeLayout) findViewById(R.id.footer);
        back = (ImageView) findViewById(R.id.back);
        count = (TextView) findViewById(R.id.count);
        yes = (TextView) findViewById(R.id.yes);
        choose = (CheckBox) findViewById(R.id.choose);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, mPager.getCurrentItem());
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public ArrayList<String> fileList;

        public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList.get(position);
            return ImageDetailFragment.newInstance(url);
        }

    }

    public interface OnSelectedListener {
        void onSelected(boolean ifSelected, String path);
    }
}
