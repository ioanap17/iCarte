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

        List<Product> catalog = ShoppingCartHelper.getCatalog(getResources(), getApplicationContext());
        final List<Product> cart = ShoppingCartHelper.getCartList();

        int productIndex = getIntent().getExtras().getInt(ShoppingCartHelper.PRODUCT_INDEX);
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

        /*// Update the current quantity in the cart
        TextView textViewCurrentQuantity = (TextView) findViewById(R.id.textViewCurrentlyInCart);
        textViewCurrentQuantity.setText("Currently in Cart: "
                + ShoppingCartHelper.getProductQuantity(selectedProduct));

        // Save a reference to the quantity edit text
        final EditText editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);*/

        Button addToCartButton = (Button) findViewById(R.id.ButtonAddToCart);
        addToCartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Check to see that a valid quantity was entered
                /*int quantity = 0;
                try {
                    quantity = Integer.parseInt(editTextQuantity.getText().toString());

                    if (quantity < 0) {
                        Toast.makeText(getBaseContext(),
                                "Please enter a quantity of 0 or higher",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (Exception e) {
                    Toast.makeText(getBaseContext(),
                            "Please enter a numeric quantity",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                // If we make it here, a valid quantity was entered
                ShoppingCartHelper.setQuantity(selectedProduct, quantity);*/

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