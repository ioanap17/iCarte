package ro.upb.cs.thesis.icarte.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ro.upb.cs.thesis.icarte.activities.MainActivity;
import ro.upb.cs.thesis.icarte.R;
import ro.upb.cs.thesis.icarte.activities.ProductDetailsActivity;
import ro.upb.cs.thesis.icarte.activities.ShoppingCartActivity;
import ro.upb.cs.thesis.icarte.listadapters.ProductAdapter;
import ro.upb.cs.thesis.icarte.models.BaseActivity;
import ro.upb.cs.thesis.icarte.models.Product;
import ro.upb.cs.thesis.icarte.utils.ShoppingCartHelper;


public class Catalog extends BaseActivity {

    String criteriuSortare = "";
    ArrayList<Product> mProductList;
    ProductAdapter productAdapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productlist);

        String[] navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        TypedArray navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        set(navMenuTitles, navMenuIcons);

        if(getIntent().hasExtra("CRITERIU")) {
            criteriuSortare = getIntent().getExtras().getString("CRITERIU");
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("Alegere sortare");
        list.add("Pret crescator");
        list.add("Pret descrescator");
        list.add("Alfabetic");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                if(item == "Pret crescator"){
                    criteriuSortare = "crescator";
                    sortare();
                }
                if(item == "Pret descrescator") {
                    criteriuSortare = "descrescator";
                    sortare();
                }
                if(item == "Alfabetic") {
                    criteriuSortare = "alfabetic";
                    sortare();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                criteriuSortare = "";
            }
        });

        // Obtain a reference to the product catalog
        mProductList = ShoppingCartHelper.getCatalog(getResources(), getApplicationContext());

        // Create the list
        ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
        productAdapter = new ProductAdapter(mProductList, getLayoutInflater(), false, false, false);
        listViewCatalog.setAdapter(productAdapter);

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

    public void sortare(){

            Collections.sort(mProductList, new Comparator<Product>() {
                @Override
                public int compare(Product prod1, Product prod2) {
                    int result = 0;
                    if(criteriuSortare.equals("alfabetic")){
                        result = prod1.title.compareTo(prod2.title);
                    }else {
                        result = Double.compare(prod1.price, prod2.price);
                        if (criteriuSortare.equals("descrescator"))
                            result = result * (-1);
                    }
                    return result;
                }
            });
            productAdapter.notifyDataSetChanged();

    }
}