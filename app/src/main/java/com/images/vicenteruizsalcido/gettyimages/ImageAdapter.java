package com.images.vicenteruizsalcido.gettyimages;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vicente.ruiz.salcido on 4/1/2017.
 */

public class ImageAdapter extends BaseAdapter{

    private Context context;
    private GridView grid;
    private List<String> images;

    public ImageAdapter(Context context, GridView gridview, List<String> images) {
        this.context = context;
        this.grid = gridview;
        this.images = images;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(
                    grid.getWidth()/2,  grid.getHeight()/2));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(context).load(images.get(position)).into(imageView);
        return imageView;
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public int getCount() {
        return images.size();
    }
}
