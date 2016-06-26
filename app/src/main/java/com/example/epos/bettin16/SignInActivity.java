package com.example.epos.bettin16;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by DefaultUser on 25.06.2016.
 */
public class SignInActivity extends Activity implements View.OnClickListener {

    public static final int REQUEST_CODE = 123;
    private TextView mCreateAccountTextView;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button signInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mCreateAccountTextView = (TextView)findViewById(R.id.create_account_tv);
        usernameEditText = (EditText)findViewById(R.id.sign_in_username_et);
        passwordEditText = (EditText)findViewById(R.id.sign_in_password_et);
        signInButton = (Button) findViewById(R.id.sign_in_button);
        mCreateAccountTextView.setOnClickListener(this);

        signInButton.setOnClickListener(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) { //jezeli rejestracja sie udała, to przenosi do Maina

            ParseUser currentUser = ParseUser.getCurrentUser();
            if(currentUser != null ){ //moze znalezc jakas podobana metode?
                goToMain();
           }
        }
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.sign_in, menu);
        return true;
    }

   // @Override
    public boolean onOptionItemSelected(MenuItem item){
        int id= item.getItemId();
        if(id==R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick (View view){
    switch (view.getId()){
        case R.id.create_account_tv:
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
            break;
        case R.id.sign_in_button:
            tryToSignIn();
            break;
    }
    }

    private void tryToSignIn() {
        String nickname = usernameEditText.getText().toString();
       // String email = mEmailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        boolean hasErrors = false;

        if(TextUtils.isEmpty(nickname)) {
            hasErrors = true;
            usernameEditText.setError("Nazwa nie moze byc pusta!");
        }
       
        
        if(TextUtils.isEmpty(password)) {
            hasErrors = true;
            passwordEditText.setError("Hasło nie może być puste!");
        }

        if(!hasErrors) {
            signIn(nickname, password);
        }
    }

    private void signIn(String nickname, String password) {
    ParseUser.logInInBackground(nickname, password, new LogInCallback() {
        @Override
        public void done(ParseUser parseUser, ParseException e) {
            if(parseUser !=null){
                goToMain();
            } else {
                Toast.makeText(getApplicationContext(), "Błąd logowania " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    });

    }

}

