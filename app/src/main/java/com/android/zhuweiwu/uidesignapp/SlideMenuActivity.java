package com.android.zhuweiwu.uidesignapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.zhuweiwu.myuilibrary.SlidingLayout;

/**
 * Created by zhuweiwu on 4/30/2016.
 */
public class SlideMenuActivity extends Activity {

    /**
     * 侧滑布局对象，用于通过手指滑动将左侧的菜单布局进行显示或隐藏。
     */
    private SlidingLayout slidingLayout;

    /**
     * menu按钮，点击按钮展示左侧布局，再点击一次隐藏左侧布局。
     */
    private Button menuButton;

    /**
     * 放在content布局中的ListView。
     */
    private ListView contentListView;

    /**
     * 作用于contentListView的适配器。
     */
    private ArrayAdapter<String> contentListAdapter;

    /**
     * 用于填充contentListAdapter的数据源。
     */
    private String[] contentItems = { "Content Item 1", "Content Item 2", "Content Item 3",
            "Content Item 4", "Content Item 5", "Content Item 6", "Content Item 7",
            "Content Item 8", "Content Item 9", "Content Item 10", "Content Item 11",
            "Content Item 12", "Content Item 13", "Content Item 14", "Content Item 15",
            "Content Item 16" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.activity_slide_menu);

        slidingLayout = (SlidingLayout) findViewById(R.id.id_sliding_layout);
        menuButton = (Button) findViewById(R.id.id_menu_btn);
        contentListView = (ListView) findViewById(R.id.id_content_list);
        contentListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,contentItems);
        contentListView.setAdapter(contentListAdapter);
        // 将监听滑动事件绑定在contentListView上
        slidingLayout.setScrollEvent(contentListView);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 实现点击一下menu展示左侧布局，再点击一下隐藏左侧布局的功能
                if (slidingLayout.isLeftLayoutVisible()) {
                    slidingLayout.scrollToRightLayout();
                } else {
                    slidingLayout.scrollToLeftLayout();
                }
            }
        });
    }
}
