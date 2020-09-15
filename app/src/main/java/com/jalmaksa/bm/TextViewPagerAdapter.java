package com.jalmaksa.bm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

public class TextViewPagerAdapter extends PagerAdapter {
    private int[] images = {R.drawable.one, R.drawable.two, R.drawable.th};
    private LayoutInflater inflater;
    private Context context;
    private int i;


    public TextViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.slider, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageview);
        imageView.setImageResource(images[position]);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==0){
                    Intent readMore = new Intent(v.getContext(), Jachal.class);
                    v.getContext().startActivity(readMore);}
                if(position==1){
                    Intent readMore = new Intent(v.getContext(), Knowhow.class);
                    v.getContext().startActivity(readMore);}
                if(position==2){
                    Intent readMore = new Intent(v.getContext(), FoodRecomend.class);
                    v.getContext().startActivity(readMore);
                }
            }
        });

        container.addView(v);
        return v;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }
}