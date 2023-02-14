package com.example.petstoreuser;

import static android.text.TextUtils.isEmpty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText mId;
    EditText mUserName;
    EditText mFirstName;
    EditText mLastName;
    EditText mEmail;
    EditText mPassword;
    EditText mPhone;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mId = findViewById(R.id.EditTextId);
        mUserName = findViewById(R.id.EditTextUsername);
        mFirstName = findViewById(R.id.EditTextFirstName);
        mLastName = findViewById(R.id.EditTextLastName);
        mEmail = findViewById(R.id.EditTextEmail);
        mPassword = findViewById(R.id.EditTextPassword);
        mPhone = findViewById(R.id.EditTextPhone);
        mButton = findViewById(R.id.btn);
    }

    public void OnClick(View view){
        int id       = Integer.parseInt(mId.getText().toString());
        String userName  = mUserName.getText().toString();
        String firstName = mFirstName.getText().toString();
        String lastName  = mLastName.getText().toString();
        String email     = mEmail.getText().toString();
        String password  = mPassword.getText().toString();
        String phone     = mPhone.getText().toString();

        if (!(isEmpty(userName) && isEmpty(firstName) && isEmpty(userName) && isEmpty(lastName) && isEmpty(email) && isEmpty(password) && isEmpty(phone) && id == 0)){
            PetStore user = new PetStore(id, userName, firstName, lastName, email, password, phone, 0);

            PetStoreAPI petStoreAPI = PetStoreAPI.retrofit.create(PetStoreAPI.class);

            final Call<PetStore> call = petStoreAPI.createUser(user);

            call.enqueue(new Callback<PetStore>() {
                @Override
                public void onResponse(Call<PetStore> call, Response<PetStore> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_SHORT).show();

                    }else {
                        ResponseBody errorBody = response.errorBody();

                        try {
                            Toast.makeText(getApplicationContext(), errorBody.string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<PetStore> call, Throwable throwable) {
                    Toast.makeText(getApplicationContext(), "Что-то пошло не так: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


}