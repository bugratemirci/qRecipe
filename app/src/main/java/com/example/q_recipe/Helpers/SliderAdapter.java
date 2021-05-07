package com.example.q_recipe.Helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.q_recipe.R;

public class SliderAdapter extends PagerAdapter {
    private int[] imageResources = {
            R.drawable.hunkarbegendi1,
            R.drawable.hunkarbegendi2,
            R.drawable.hunkarbegendi3,
            R.drawable.hunkarbegendi4
    };
    private Context context;
    private LayoutInflater layoutInflater;
    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageResources.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.recipeviewpager, container, false);

        ImageView imageView = item_view.findViewById(R.id.slider_page);
        imageView.setImageResource(imageResources[position]);
        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
