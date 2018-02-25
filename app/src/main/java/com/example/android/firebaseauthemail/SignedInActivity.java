package com.example.android.firebaseauthemail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignedInActivity extends AppCompatActivity {
    private Button btn_salir;
    private Button btn_eliminar;
    private TextView Name;
    private TextView Email;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in);
        Email=findViewById(R.id.Email);
        Name=findViewById(R.id.name);
        btn_salir=findViewById(R.id.salir);
        btn_eliminar=findViewById(R.id.eliminar);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        if(user!=null){
            Name.setText(user.getDisplayName());
            Email.setText(user.getEmail());
        }else {
            startActivity(new Intent(this,SignedInActivity.class));
            finish();

        }

        btn_salir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                signOut(view);
            }
        });

        btn_eliminar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                deleteAccount(view);
            }
        });

    }

    public void signOut(View view) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(
                                    SignedInActivity.this,
                                    FirebaseAuthActivity.class));
                            finish();
                        } else {
                            // Report error to user
                        }
                    }
                });
    }


    public void deleteAccount(View view) {
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(SignedInActivity.this,
                                    FirebaseAuthActivity.class));
                            finish();
                        } else {
                            // Notify user of error
                        }
                    }
                });
    }


}
