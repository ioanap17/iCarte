package ro.upb.cs.thesis.icarte.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.Executor;

import ro.upb.cs.thesis.icarte.R;
import ro.upb.cs.thesis.icarte.utils.ShoppingCartHelper;

import static java.security.AccessController.getContext;

public class StripePaymentActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_payment);

        CardInputWidget mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);

        Card validCard = new Card("4242424242424242", 12, 2018, "123");

        Card card = mCardInputWidget.getCard();
        if (card == null) {
            Log.e("ERROR","Invalid Card Data");
        }else{
            Stripe stripe = new Stripe(getApplicationContext(), "ac_ApAsTax3fJt3HE8629PVYpqN10lItKmL");
            stripe.createToken(
                    card,
                    new TokenCallback() {
                        public void onSuccess(Token token) {
                            // Send token to your server

                        }
                        public void onError(Exception error) {
                            // Show localized error message
                            Toast.makeText(getApplicationContext(),
                                    error.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            );
        }

        Button chargeButton = (Button) findViewById(R.id.button_charge);
        chargeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StripePaymentActivity.this, "Plata efectuata cu succes!", Toast.LENGTH_LONG).show();
                ShoppingCartHelper.clearCart();
                Intent intent = new Intent(StripePaymentActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

   /* private EditText editCardNumber, editCvcNumber, editExpiryDate, editEmail, editRefDesc;
    private String userPublishableKey = "ca_ApAdTQtCELiZGcVCr51N5Hu67zDKZfHt";
    private String userSecretKey = "sk_test_xoBGmWoUEzCO6aUvUJAHZAzD";
    private String cardNumber, cardCVC, userDescription, userEmail;
    private int cardExpMonth;
    private Integer cardExpYear;
    private boolean isLive = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_payment);

        editCardNumber = (EditText) findViewById(R.id.editCardNumber);
        editCvcNumber = (EditText) findViewById(R.id.editCvcNumber);
        editExpiryDate =(EditText) findViewById(R.id.editExpiryDate);
        editEmail =(EditText) findViewById(R.id.editEmail);
        editRefDesc =(EditText) findViewById(R.id.editRefDesc);

        isCardValid();

        Card card = new Card(cardNumber, cardExpMonth, cardExpYear, cardCVC);
        try {
            Stripe stripe = new Stripe(userPublishableKey);
            Executor publishableKey = null;
            stripe.createToken(
                    card,
                    publishableKey,
                    new TokenCallback() {
                        public void onSuccess(Token token) {
                            if (checkInternetConnection(getApplicationContext())) {
                                sendCardToInfoToStripe(token.getId(), isLive);
                            }
                            else {
                                Log.e("InternetAlert", "No Internet Connection");
                            }
                        }
                        public void onError(Exception error) {
                            Log.e("Error", error.getMessage());
                        }
                    });
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        final Dialog dialog = new Dialog(ChargeCardActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_connect_alert_dialog_with_msg);
        dialog.setCanceledOnTouchOutside(false);

        TextView textDialog = (TextView) dialog.findViewById(R.id.textDialog);
        textDialog.setText(getString(R.string.lbl_stripe_not_connected));
        TextView textCancel = (TextView) dialog.findViewById(R.id.textCancel);
        TextView textOk = (TextView) dialog.findViewById(R.id.textOk);
// if button is clicked, close the custom dialog
        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        textOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(ChargeCardActivity.this, WebviewPaymentActivity.class);
                intent.putExtra("ISLIVE", isLive);
                intent.putExtra("CARD_NUMBER", cardNumber);
                intent.putExtra("CARD_CVC", cardCVC);
                intent.putExtra("CARD_EXP_MONTH", cardExpMonth);
                intent.putExtra("CARD_EXP_YEAR", cardExpYear);
                intent.putExtra("CENT_AMOUNT", centAmount);
                intent.putExtra("USER_DESCRIPTION", userDescription);
                intent.putExtra("USER_EMAIL", userEmail);
                intent.putExtra("USER_CURRENCY", sessionManager.getStringDetail("USER_CURRENCY"));
                intent.putExtra("USER_PUBLISHABLE_KEY", userPublishableKey);
                startActivity(intent);
            }
        });
        dialog.show();
    }

    *//**
     * This is standerd api call method which will get the user's key from server
     **//*
    private void getUserKey() {
// Call api and get user publishable & secret key here and assign key value in userPublishableKey & userSecretKey

    }

    private boolean isCardValid() {
        cardNumber = editCardNumber.getText().toString().replace(" ", "");
        cardCVC = editCvcNumber.getText().toString();
        StringTokenizer tokens = new StringTokenizer(editExpiryDate.getText().toString(), "/");
        cardExpMonth = Integer.parseInt(tokens.nextToken());
        cardExpYear = Integer.parseInt(tokens.nextToken());

        Card card = new Card(cardNumber, cardExpMonth, cardExpYear, cardCVC);
        AlertDialog alertDialog = new AlertDialog.Builder(StripePaymentActivity.this).create();
        alertDialog.setTitle("Error");
        boolean validation = card.validateCard();
        if (validation) {
            return true;
        } else if (!card.validateNumber()) {
            alertDialog.setMessage("The card number that you entered is invalid.");
            alertDialog.show();
        } else if (!card.validateExpiryDate()) {
            alertDialog.setMessage( "The expiration date that you entered is invalid.");
            alertDialog.show();
        } else if (!card.validateCVC()) {
            alertDialog.setMessage("The CVC code that you entered is invalid.");
            alertDialog.show();
        } else {
            alertDialog.setMessage("The card details that you entered are invalid.");
            alertDialog.show();
        }
        return false;
    }


    public boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++){
                    if (info[i].getState()==NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void sendCardToInfoToStripe(final String cardToken, final boolean isLive) {
        userDescription = editRefDesc.getText().toString(); // Get User Description[bad code]
        userEmail = editEmail.getText().toString(); // Get User Email Id
        new Thread(new Runnable() {
            @Override
            public void run() {

                Map<String, Object> chargeParams = new HashMap<String, Object>();
                chargeParams.put("amount", 1000);
                chargeParams.put("currency", "usd");
                chargeParams.put("source", cardToken);
                chargeParams.put("description", userDescription);
                chargeParams.put("receipt_email", userEmail);
                chargeParams.put("application_fee", 100);
                RequestOptions requestOptions;
                requestOptions = RequestOptions.builder().setStripeAccount(
                        sessionManager.getStringDetail(userPublishableKey)).setApiKey(userSecretKey).build();

                try {
                    Charge.create(chargeParams, requestOptions);
                } catch (final Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("Error", e.getMessage());
                        }
                    });
                }
            }
        }).start();

    }*/
}
