package com.example.epos.bettin16;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = SignUpFragment.class.getSimpleName();

    private Button mSignUpButton;
    private EditText mNicknameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;


    public SignUpFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mSignUpButton = (Button) rootView.findViewById(R.id.sign_up_btn);
        mNicknameEditText = (EditText) rootView.findViewById(R.id.nickname_et);
        mEmailEditText = (EditText) rootView.findViewById(R.id.email_et);
        mPasswordEditText = (EditText) rootView.findViewById(R.id.password_et);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSignUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_btn:
                tryToSignUp();
                break;
        }
    }

    private void tryToSignUp() {
        String nickname = mNicknameEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        boolean hasErrors = false;

        if(TextUtils.isEmpty(nickname)) {
            hasErrors = true;
            mNicknameEditText.setError("Nazwa nie moze byc pusta!");
        }
        if(TextUtils.isEmpty(email)) {
            hasErrors = true;
            mEmailEditText.setError("Email nie moze byc pusty!");
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmailEditText.setError("Nieprawidłowy adres email!");
            hasErrors = true;
        }
        if(TextUtils.isEmpty(password)) {
            hasErrors = true;
            mPasswordEditText.setError("Hasło nie może być puste!");
        }

        if(!hasErrors) {
            signUp(nickname, email, password);
        }
    }

    private void signUp(String nickname, String email, String password) {

        ParseUser user = ParseObject.create(ParseUser.class); //wywala wyjatek dla takich parsów
        user.setUsername(email);
        user.setEmail(email);
        user.setPassword(password);

        user.put("nickname", nickname);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    if(getActivity() != null) {
                        getActivity().setResult(Activity.RESULT_OK);
                        getActivity().finish();
                    }
                    Log.d(TAG, "Zarejestrowany!");
                } else {
                    if(getActivity() != null) {
                        Toast.makeText(getActivity(), "Błąd rejestracji: " + e.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                    Log.e(TAG, "Błąd rejestracji: " + e.getLocalizedMessage());
                }
            }
        });


    }
}