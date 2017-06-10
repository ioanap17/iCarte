package ro.upb.cs.thesis.icarte.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import ro.upb.cs.thesis.icarte.activities.MainActivity;
import ro.upb.cs.thesis.icarte.R;
import ro.upb.cs.thesis.icarte.activities.ProductDetailsActivity;
import ro.upb.cs.thesis.icarte.activities.ShoppingCartActivity;
import ro.upb.cs.thesis.icarte.listadapters.ProductAdapter;
import ro.upb.cs.thesis.icarte.models.Product;
import ro.upb.cs.thesis.icarte.utils.ShoppingCartHelper;


public class Catalog extends MainActivity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productlist);

        String[] navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        TypedArray navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        set(navMenuTitles, navMenuIcons);

        // Obtain a reference to the product catalog
        List<Product> mProductList = ShoppingCartHelper.getCatalog(getResources(), getApplicationContext());

        // Create the list
        ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
        listViewCatalog.setAdapter(new ProductAdapter(mProductList, getLayoutInflater(), false, false, false));

        listViewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent productDetailsIntent = new Intent(getBaseContext(),ProductDetailsActivity.class);
                productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX, position);
                startActivity(productDetailsIntent);
            }
        });

        Button viewShoppingCart = (Button) findViewById(R.id.ButtonViewCart);
        viewShoppingCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent viewShoppingCartIntent = new Intent(getBaseContext(), ShoppingCartActivity.class);
                startActivity(viewShoppingCartIntent);
            }
        });

    }
}