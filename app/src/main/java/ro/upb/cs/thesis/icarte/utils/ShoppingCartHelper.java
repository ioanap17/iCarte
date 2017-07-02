package ro.upb.cs.thesis.icarte.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import ro.upb.cs.thesis.icarte.models.Product;
import ro.upb.cs.thesis.icarte.models.ShoppingCartEntry;

public class ShoppingCartHelper {

    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";
    public static final String CATEGORY_NAME = "CATEGORY_NAME";

    private static ArrayList<Product> catalog;
    private static Map<Product, ShoppingCartEntry> cartMap = new HashMap<Product, ShoppingCartEntry>();
    private static Map<String, ArrayList<Product>> categoryMap = new HashMap<>();

    public static ArrayList<Product> getCatalog(final Resources res, final Context context){
        if(catalog == null) {
            catalog = new ArrayList<Product>();

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String titlu = jsonObject.getString("titlu");
                            String autor = jsonObject.getString("autor");
                            String imagine = jsonObject.getString("imagine");
                            String descriere = jsonObject.getString("descriere");
                            double pret = jsonObject.getDouble("pret");
                            String categorii = jsonObject.getString("categorii");

                            Product currentProduct = new Product(titlu, autor, getImage(context, res, imagine), descriere, pret);
                            catalog.add(currentProduct);

                            String[] listaCategorii = categorii.split(" ");
                            for(int j = 0; j < listaCategorii.length; j++){
                                if(categoryMap.containsKey(listaCategorii[j])){
                                    categoryMap.get(listaCategorii[j]).add(currentProduct);
                                }else{
                                    ArrayList<Product> currentList = new ArrayList();
                                    currentList.add(currentProduct);
                                    categoryMap.put(listaCategorii[j], currentList);
                                }
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };

            String CATALOG_REQUEST_URL = "https://claw.000webhostapp.com/Catalog.php";
            StringRequest request = new StringRequest(Request.Method.POST, CATALOG_REQUEST_URL, responseListener, null);
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(request);
        }

        return catalog;
    }

    public static void setQuantity(Product product, int quantity) {
        // Get the current cart entry
        ShoppingCartEntry curEntry = cartMap.get(product);

        // If the quantity is zero or less, remove the products
        if(quantity <= 0) {
            if(curEntry != null)
                removeProduct(product);
            return;
        }

        // If a current cart entry doesn't exist, create one
        if(curEntry == null) {
            curEntry = new ShoppingCartEntry(product, quantity);
            cartMap.put(product, curEntry);
            return;
        }

        // Update the quantity
        curEntry.setQuantity(quantity);
    }

    public static int getProductQuantity(Product product) {
        // Get the current cart entry
        ShoppingCartEntry curEntry = cartMap.get(product);

        if(curEntry != null)
            return curEntry.getQuantity();

        return 0;
    }

    public static void removeProduct(Product product) {
        cartMap.remove(product);
    }

    public static void clearCart() { cartMap.clear();}

    public static List<Product> getCartList() {
        List<Product> cartList = new Vector<Product>(cartMap.keySet().size());
        for(Product p : cartMap.keySet()) {
            cartList.add(p);
        }

        return cartList;
    }

    public static Drawable getImage(Context c, Resources res, String ImageName) {
        return res.getDrawable(res.getIdentifier(ImageName, "mipmap", c.getPackageName()));
    }

    public static ArrayList<Product> getCategoryList(String categorie){
        return categoryMap.get(categorie);
    }
}
