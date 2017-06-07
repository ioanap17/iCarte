package ro.upb.cs.thesis.icarte.activities;

import android.content.res.TypedArray;
import android.os.Bundle;

import ro.upb.cs.thesis.icarte.R;
import ro.upb.cs.thesis.icarte.models.BaseActivity;

public class MainActivity extends BaseActivity{

    private String products_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] navMenuTitles;
        TypedArray navMenuIcons;
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml

        set(navMenuTitles,navMenuIcons);

    }

    public String getProducts_category() {
        return products_category;
    }

    public void setProducts_category(String products_category) {
        this.products_category = products_category;
    }
}
