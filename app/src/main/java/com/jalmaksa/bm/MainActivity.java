package com.jalmaksa.bm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // SearchView search;
    ImageButton searchdo, myp;
    TextViewPagerAdapter TextViewPagerAdapter;
    ViewPager viewPager;
    TextView text;
    ImageButton imageButton, youtubebtn, cookcatbtn, recipebtn;
    String urlyoutube = "https://www.youtube.com/channel/UCyn-K7rZLXjGl7VXGweIlcA";
    String urlcookcat = "https://www.youtube.com/channel/UCjrwkxVOpN133r1Yp07583Q";
    String urlrecipe = "https://www.youtube.com/channel/UCKA_6r3CWC76x_EaFO6jsPA";
    int k;

    //해상도
    public Point getScreenSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return  size;
    }
    int standardSize_X, standardSize_Y;
    float density;

    public void getStandardSize() {
        Point ScreenSize = getScreenSize(this);
        density  = getResources().getDisplayMetrics().density;

        standardSize_X = (int) (ScreenSize.x / density);
        standardSize_Y = (int) (ScreenSize.y / density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        text = (TextView) findViewById(R.id.title);

        // search=(SearchView)findViewById(R.id.search);
        searchdo = (ImageButton) findViewById(R.id.searchdo);

        viewPager = (ViewPager) findViewById(R.id.view);
        TextViewPagerAdapter = new TextViewPagerAdapter(this);
        viewPager.setAdapter(TextViewPagerAdapter);


        myp = (ImageButton) findViewById(R.id.myp);
        myp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MyPage.class);
                startActivity(intent);
            }
        });

        searchdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SearchAfterPage.class);
                startActivity(intent);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            myp.setBackground(new ShapeDrawable(new OvalShape()));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            myp.setClipToOutline(true);
        }

        myp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
            }
        });

        youtubebtn = (ImageButton) findViewById(R.id.youtubebtn);
        youtubebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlyoutube));
                startActivity(intent.setPackage("com.google.android.youtube"));
            }
        });

        cookcatbtn = (ImageButton) findViewById(R.id.cookcatbtn);
        cookcatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlcookcat));
                startActivity(intent.setPackage("com.google.android.youtube"));
            }
        });

        recipebtn = (ImageButton) findViewById(R.id.recipebtn);
        recipebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlrecipe));
                startActivity(intent.setPackage("com.google.android.youtube"));
            }
        });
    }
}
