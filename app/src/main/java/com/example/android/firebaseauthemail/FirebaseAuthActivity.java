package com.example.android.firebaseauthemail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class FirebaseAuthActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private static final int REQUEST_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_auth);

        firebaseAuth= FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            startActivity (new Intent(this, SignedInActivity.class));
           // onDestroy();
          //  terminar();
          finish();
        }else{
            autenticarUser();
        }

    }

    private void autenticarUser () {
        startActivityForResult (
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(getProviderList())
                        .setIsSmartLockEnabled (false)
                        .build (),
                REQUEST_CODE);
    }

    private List<AuthUI.IdpConfig> getProviderList() {

        List<AuthUI.IdpConfig> providers = new ArrayList<>();

        providers.add (new  AuthUI.IdpConfig.Builder (AuthUI.EMAIL_PROVIDER) .build ());

        return providers;
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        IdpResponse response = IdpResponse.fromResultIntent (data);

        if (requestCode == REQUEST_CODE) {

            if (resultCode == ResultCodes.OK) {
                startActivity (new  Intent (this, SignedInActivity.class));
                return;
            }
        } else {
            if (response == null) {
                // Usuario cancelado Regrese
                ;
            }

            if (response.getErrorCode () == ErrorCodes.NO_NETWORK) {
                // El dispositivo no tiene conexión de red
                ;
            }

            if (response.getErrorCode () == ErrorCodes.UNKNOWN_ERROR) {
                // Se produjo un error desconocido
                return;
            }
        }
    }
}
