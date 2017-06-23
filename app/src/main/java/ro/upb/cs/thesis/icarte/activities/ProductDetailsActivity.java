package ro.upb.cs.thesis.icarte.activities;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ro.upb.cs.thesis.icarte.R;
import ro.upb.cs.thesis.icarte.models.BaseActivity;
import ro.upb.cs.thesis.icarte.models.Product;
import ro.upb.cs.thesis.icarte.utils.ShoppingCartHelper;

/**
 * Created by Paun on 03.06.2017.
 */

public class ProductDetailsActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.productdetails);

        String[] navMenuTitles;
        TypedArray navMenuIcons;
        // load titles from strings.xml
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        //load icons from strings.xml
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        set(navMenuTitles,navMenuIcons);

        List<Product> catalog;
        final List<Product> cart = ShoppingCartHelper.getCartList();

        int productIndex = getIntent().getExtras().getInt(ShoppingCartHelper.PRODUCT_INDEX);

        String categoryName;
        if(getIntent().hasExtra(ShoppingCartHelper.CATEGORY_NAME)) {
            categoryName = getIntent().getExtras().getString(ShoppingCartHelper.CATEGORY_NAME);
            catalog = ShoppingCartHelper.getCategoryList(categoryName);
        }else
            catalog = ShoppingCartHelper.getCatalog(getResources(), getApplicationContext());

        final Product selectedProduct = catalog.get(productIndex);

        // Set the proper image and text
        ImageView productImageView = (ImageView) findViewById(R.id.ImageViewProduct);
        productImageView.setImageDrawable(selectedProduct.productImage);

        TextView productTitleTextView = (TextView) findViewById(R.id.TextViewProductTitle);
        productTitleTextView.setText(selectedProduct.title);

        TextView productAuthorTextView = (TextView) findViewById(R.id.authorName);
        productAuthorTextView.setText("de " + selectedProduct.author);

        TextView productDetailsTextView = (TextView) findViewById(R.id.TextViewProductDetails);
        productDetailsTextView.setText(selectedProduct.description);

        TextView productPriceTextView = (TextView) findViewById(R.id.TextViewProductPrice);
        productPriceTextView.setText(selectedProduct.price +" RON");

        Button addToCartButton = (Button) findViewById(R.id.ButtonAddToCart);
        addToCartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Add product to cart if the item is not in the cart already
                if(!cart.contains(selectedProduct)) {
                    cart.add(selectedProduct);
                    ShoppingCartHelper.setQuantity(selectedProduct, 1);
                }else{
                    int quantity = ShoppingCartHelper.getProductQuantity(selectedProduct) + 1;
                    ShoppingCartHelper.setQuantity(selectedProduct, quantity);
                }
                // Close the activity
                finish();
            }
        });

    }

}