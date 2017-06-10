package ro.upb.cs.thesis.icarte.activities;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ro.upb.cs.thesis.icarte.R;
import ro.upb.cs.thesis.icarte.listadapters.CategoryAdapter;
import ro.upb.cs.thesis.icarte.models.BaseActivity;
import ro.upb.cs.thesis.icarte.models.Product;
import ro.upb.cs.thesis.icarte.utils.ShoppingCartHelper;

public class MainActivity extends BaseActivity{

    ArrayList<String> categories;

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

        categories = new ArrayList<>();
        categories.add("UNIVERSAL");
        categories.add("ROMANA");
        categories.add("CONTEMPORAN");
        categories.add("CLASIC");
        categories.add("POLITIST");
        categories.add("DRAGOSTE");
        categories.add("FICTIUNE");

        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, getLayoutInflater(), getApplicationContext());

        ListView categoryList = (ListView) findViewById(R.id.category_list);
        categoryList.setAdapter(categoryAdapter);
        categoryList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        // Obtain a reference to the product catalog
        List<Product> mProductList = ShoppingCartHelper.getCatalog(getResources(), getApplicationContext());
    }


}
