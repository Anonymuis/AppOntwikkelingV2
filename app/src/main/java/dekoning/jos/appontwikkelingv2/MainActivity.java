package dekoning.jos.appontwikkelingv2;


import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Message";

    public final String ACTIVITY_NAME = this.getClass().getSimpleName();

    EditText editEmail, editPassword, editName;
    Button btnSignIn, btnRegister;


    String URL; //= this.getResources().getString(R.string.server_address);

    JSONParser jsonParser = new JSONParser();

    boolean accountCreated = false; // TODO change to bool

    boolean waitingForLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        URL = this.getResources().getString(R.string.server_address);
        Log.i(TAG, "MainActivity onCreate");

        boolean stayLoggedIn = getPreference("pref", "stay_logged_in");
        if(stayLoggedIn && (getPreference("user_info", "username",true))!= null){
            goToMenu();
        }

        super.onCreate(savedInstanceState); // get saved instance if available
        setContentView(R.layout.activity_main); // Select the XML file for MainActivity
        waitingForLogin = false;
        editEmail = (EditText) findViewById(R.id.editEmail); // Get reference to text field
        editName = (EditText) findViewById(R.id.editName); // Get reference to text field
        editPassword = (EditText) findViewById(R.id.editPassword); // Get reference to text field

        btnSignIn = (Button) findViewById(R.id.btnSignIn); // Get reference to 'SIGN IN' button
        btnRegister = (Button) findViewById(R.id.btnRegister); // Get reference to 'REGISTER' button

        // Setup a 'SIGN IN' button listener
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
                Log.i(TAG, ACTIVITY_NAME + " " + methodName);
                if (!waitingForLogin) {
                    waitingForLogin = true;//true
                    AttemptLogin attemptLogin = new AttemptLogin(); // Create a new login attempt
                    attemptLogin.execute(editName.getText().toString(), editPassword.getText().toString(), ""); // Execute the login attempt (Async)
                }
            }
        });

        // Setup a 'REGISTER' button listener
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
                Log.i(TAG, ACTIVITY_NAME + " " + methodName);

                if (!waitingForLogin) {
                    waitingForLogin = true;
                    if(!accountCreated) {
                        accountCreated = true;
                        editEmail.setVisibility(View.VISIBLE);
                        btnSignIn.setVisibility(View.GONE);
                        btnRegister.setText("CREATE ACCOUNT");
                        waitingForLogin = false;
                    } else {
                        // login
                        btnRegister.setText("REGISTER");
                        editEmail.setVisibility(View.GONE);
                        btnSignIn.setVisibility(View.VISIBLE);
                        accountCreated = false;
                        AttemptLogin attemptLogin = new AttemptLogin(); // Create new login attempt (async)
                        attemptLogin.execute(editName.getText().toString(), editPassword.getText().toString(), editEmail.getText().toString()); // Starts te async task
                    }
                }

            }
        });
    }

    private class AttemptLogin extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {
            // TODO: add connecting to the server message
            super.onPreExecute();
        }

        @Override

        protected JSONObject doInBackground(String... args) {

            String email = args[2];
            String password = args[1];
            String name = args[0];

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", name));
            params.add(new BasicNameValuePair("password", password));
            if(email.length()>0)
                params.add(new BasicNameValuePair("email",email));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);

            return json;
        }

        protected void onPostExecute(JSONObject result) {
            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            try {
                if (result != null) {
                    Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();

                    // if login succesfull login
                    // Check in the postExecute method if the “success” param is true.
                    if(result.getInt("success") == 1) {
                        saveInfo(result.getString("userName"));
                        goToMenu();
                        Log.i(TAG, "Succesfull login");

                    }else{
                        Log.i(TAG, "Login bool false");
                        Log.i(TAG, (String.valueOf(result.getInt("success"))));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            waitingForLogin = false;
        }
    }
    private void saveInfo(String username){
        SharedPreferences sharePref = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharePref.edit();
        prefEditor.putString("username",username);
        prefEditor.apply(); // Submit to file
    }
    private boolean getPreference(String file, String identifier){
        SharedPreferences preferences = this.getSharedPreferences(file, Context.MODE_PRIVATE);
        boolean value = preferences.getBoolean(identifier, false);
        return value;
    }
    private String getPreference(String file, String identifier, boolean b){
        SharedPreferences preferences = this.getSharedPreferences(file, Context.MODE_PRIVATE);
        String value = preferences.getString(identifier, null);
        return value;
    }
    private void goToMenu(){
        Intent myIntent = new Intent(MainActivity.this, MenuActivity.class);
        MainActivity.this.startActivity(myIntent);
    }
}
