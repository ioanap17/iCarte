package ro.upb.cs.thesis.icarte.utils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paun on 07.06.2017.
 */

public class CatalogRequest extends StringRequest {

    private static final String CATALOG_REQUEST_URL = "https://claw.000webhostapp.com/Catalog.php";
    private Map<String, String> params;

    public CatalogRequest(String titlu, String autor, Response.Listener<String> listener){

        super(Request.Method.POST, CATALOG_REQUEST_URL, listener, null);
        params = new HashMap<String, String>();
        params.put("titlu", titlu);
        params.put("autor", autor);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
