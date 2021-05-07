package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.q_recipe.Helpers.SliderAdapter;

public class RecipeDetailActivity extends AppCompatActivity {
    ViewPager viewPagerDetailRecipe;
    SliderAdapter sliderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        viewPagerDetailRecipe = findViewById(R.id.viewPagerDetailRecipe);
        sliderAdapter = new SliderAdapter(RecipeDetailActivity.this);

        viewPagerDetailRecipe.setAdapter(sliderAdapter);
    }
}