/*
 * Copyright (C) 2015 Alexander Hong
 */

package com.github.thealexhong.practice1;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private int mClickCounter = 0; /* click counter */
    private TextView mMessage; /* text message to display number of clicks  */
    private ImageView mImage; /* dog image is set visible/invisible  */
    private TextView mWoofMessage; /* woof message when dog appears! */

    /**
     * Creates initial main activity GUI
     * @param savedInstanceState Bundle object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        mMessage = (TextView) findViewById(R.id.click_counter);
        Button mRecordClick = (Button)findViewById(R.id.register_click);
        Button mToggleImage = (Button)findViewById(R.id.toggle_image);
        mImage = (ImageView) findViewById(R.id.dog);
        mWoofMessage = (TextView) findViewById(R.id.woof);
        mRecordClick.setOnClickListener(this);
        mToggleImage.setOnClickListener(this);
    }

    /**
     * Creates menu options
     * @param menu Menu object
     * @return status of Menu creation
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Menu item behaviour when clicked
     * @param item MenuItem object
     * @return status of MenuItem event
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_reset:
                mClickCounter = 0;
                mMessage.setText(R.string.init_clicks);
                mImage.setVisibility(View.INVISIBLE);
                mWoofMessage.setVisibility(View.INVISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Define button click behaviour
     * @param view View object
     */
    public void onClick(View view)
    {
        switch(view.getId()) {
            case R.id.register_click:
                recordClick(view);
                break;
            case R.id.toggle_image:
                toggleImage(view);
                break;
            default:
                break;
        }
    }

    /**
     * Records and displays number of user button clicks
     * @param view View object
     */
    public void recordClick(View view) {
        mClickCounter++;
        mMessage.setText("Clicked " + mClickCounter + " time");
        if (mClickCounter > 1) {
            mMessage.append("s");
        }
    }

    /**
     * Toggles and displays image
     * @param view View object
     */
    public void toggleImage(View view) {
        if (mImage.getVisibility() == View.INVISIBLE) {
            mImage.setVisibility(View.VISIBLE);
            mWoofMessage.setVisibility(View.VISIBLE);
        }
        else {
            mImage.setVisibility(View.INVISIBLE);
            mWoofMessage.setVisibility(View.INVISIBLE);
        }
    }
}
