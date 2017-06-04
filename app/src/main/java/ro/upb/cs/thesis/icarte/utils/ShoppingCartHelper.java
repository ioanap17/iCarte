package ro.upb.cs.thesis.icarte.utils;

import android.content.res.Resources;

import java.util.List;
import java.util.Vector;

import ro.upb.cs.thesis.icarte.R;
import ro.upb.cs.thesis.icarte.models.Product;

/**
 * Created by Paun on 03.06.2017.
 */

public class ShoppingCartHelper {
    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    private static List<Product> catalog;
    private static List<Product> cart;

    public static List<Product> getCatalog(Resources res){
        if(catalog == null) {
            catalog = new Vector<Product>();
            catalog.add(new Product("Maitreyi", res.getDrawable(R.mipmap.ic_maitreyi),
                    "Maitreyi de Mircea Eliade", 19.99));
        }

        return catalog;
    }

    public static List<Product> getCart() {
        if(cart == null) {
            cart = new Vector<Product>();
        }

        return cart;
    }

}
