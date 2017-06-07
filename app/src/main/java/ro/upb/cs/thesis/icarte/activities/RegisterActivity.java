package ro.upb.cs.thesis.icarte.activities;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import ro.upb.cs.thesis.icarte.R;
import ro.upb.cs.thesis.icarte.utils.RegisterRequest;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etFirstName = (EditText) findViewById(R.id.first_name);
        final EditText etFamilyName = (EditText) findViewById(R.id.fam_name);
        final EditText etEmail = (EditText) findViewById(R.id.email_field);
        final EditText etPhone = (EditText) findViewById(R.id.phone_field);
        final EditText etAddress = (EditText) findViewById(R.id.address_field);
        final EditText etUsername = (EditText) findViewById(R.id.user_name);
        final EditText etPassword = (EditText) findViewById(R.id.pwd_field);
        final Button button = (Button) findViewById(R.id.btn_register);
        final Button bCancel = (Button) findViewById(R.id.btn_cancel);

        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                final String firstName = etFirstName.getText().toString();
                final String familyName = etFamilyName.getText().toString();
                final String email = etEmail.getText().toString();
                final String phone = etPhone.getText().toString();
                final String address = etAddress.getText().toString();
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                if(username.isEmpty() || password.isEmpty())
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                    alert.setMessage("The username and password fields are mandatory.\nPlease enter them in order to register.").setNegativeButton("Retry", null).create().show();
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if(success){
                                Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                                //Intent intent = new Intent(RegisterActivity.this, Authentication.class);
                                //startActivity(intent);
                                RegisterActivity.this.finish();
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed!").setNegativeButton("Retry", null).create().show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(firstName, familyName, email, phone, address, username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Intent intent = new Intent(RegisterActivity.this, Authentication.class);
                //startActivity(intent);
                RegisterActivity.this.finish();
            }
        });
    }
}
