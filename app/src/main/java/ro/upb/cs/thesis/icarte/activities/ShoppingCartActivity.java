package ro.upb.cs.thesis.icarte.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import ro.upb.cs.thesis.icarte.R;
import ro.upb.cs.thesis.icarte.listadapters.ProductAdapter;
import ro.upb.cs.thesis.icarte.models.BaseActivity;
import ro.upb.cs.thesis.icarte.models.Product;
import ro.upb.cs.thesis.icarte.utils.ShoppingCartHelper;


public class ShoppingCartActivity extends BaseActivity {

    private List<Product> mCartList;
    private ProductAdapter mProductAdapter;
    private double totalPrice;
    public static String loggedUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        String[] navMenuTitles;
        TypedArray navMenuIcons;
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml

        set(navMenuTitles,navMenuIcons);

        mCartList = ShoppingCartHelper.getCartList();

        if(mCartList.size() == 0){
            TextView emptyCartTextView = (TextView) findViewById(R.id.emptyCart);
            emptyCartTextView.setVisibility(View.VISIBLE);
        }

        // Make sure to clear the selections
        for(int i=0; i<mCartList.size(); i++) {
            mCartList.get(i).selected = false;
        }

        // Create the list
        final ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
        mProductAdapter = new ProductAdapter(mCartList, getLayoutInflater(), true, true, true);
        listViewCatalog.setAdapter(mProductAdapter);

        listViewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Product selectedProduct = mCartList.get(position);
                if(selectedProduct.selected == true)
                    selectedProduct.selected = false;
                else
                    selectedProduct.selected = true;

                mProductAdapter.notifyDataSetInvalidated();
                updatePrice();
            }
        });

        Button removeButton = (Button) findViewById(R.id.ButtonRemoveFromCart);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Loop through and remove all the products that are selected
                // Loop backwards so that the remove works correctly
                for(int i=mCartList.size()-1; i>=0; i--) {

                    if(mCartList.get(i).selected) {
                        ShoppingCartHelper.removeProduct(mCartList.get(i));
                        mCartList.remove(i);
                    }
                }
                mProductAdapter.notifyDataSetChanged();
                updatePrice();
            }
        });


        Button checkout = (Button) findViewById(R.id.checkoutButton);
        if(mCartList.size() == 0)
            checkout.setVisibility(View.INVISIBLE);
        else
            checkout.setVisibility(View.VISIBLE);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalPrice!=0 && !loggedUser.equals("")){
                    Intent intent = new Intent(ShoppingCartActivity.this, PayPalCheckOutActivity.class);
                    intent.putExtra("TOTAL_PRICE", totalPrice);
                    startActivity(intent);
                    finish();
                }
                if(totalPrice == 0){
                    Toast.makeText(ShoppingCartActivity.this, "Cosul de cumparaturi este gol!", Toast.LENGTH_LONG).show();
                }
                if(loggedUser.equals("")){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartActivity.this);

                    builder.setMessage("Pentru a plasa o comanda trebuie sa fiti autentificat!")
                            .setTitle("Eroare");

                    builder.setPositiveButton("Autentificare", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked Log In button
                            Intent intent = new Intent(ShoppingCartActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Refresh the data
        if(mProductAdapter != null) {
            mProductAdapter.notifyDataSetChanged();
        }
        updatePrice();
    }

    private void updatePrice(){
        double subTotal = 0;
        for(Product p : mCartList) {
            int quantity = ShoppingCartHelper.getProductQuantity(p);
            subTotal += p.price * quantity;
        }

        totalPrice = subTotal;

        TextView productPriceTextView = (TextView) findViewById(R.id.TextViewSubtotal);
        productPriceTextView.setText("Subtotal: " + String.format("%.2f", totalPrice) + " RON");
    }
}
