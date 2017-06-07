package ro.upb.cs.thesis.icarte.utils;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paun on 06.06.2017.
 */

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://claw.000webhostapp.com/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String firstName, String familyName, String email, String phone, String address, String username, String password, Response.Listener<String> listener){

        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<String, String>();
        params.put("first_name", firstName);
        params.put("family_name", familyName);
        params.put("email", email);
        params.put("address", address);
        params.put("username", username);
        params.put("password", password);
        params.put("phone", phone);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
