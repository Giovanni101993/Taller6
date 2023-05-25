package com.example.doglife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText editNombre, editEmail, editPassword, editPassword2;
    Button btnRegistro, btnLogin;

    ProgressDialog progressDialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editNombre= findViewById(R.id.editNombre);
        editEmail= findViewById(R.id.editEmail);
        editPassword= findViewById(R.id.editPassword);
        editPassword2= findViewById(R.id.editPassword2);
        btnRegistro= findViewById(R.id.btnRegistro);
        btnLogin= findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registrando usuario");

        btnRegistro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String nombre= editNombre.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                String password2 = editPassword2.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editEmail.setError("Email incorrecto");
                    editEmail.setFocusable(true);
                }
                else if (password.length()<8){
                    editPassword.setError("La contraseña debe tener 8 o más carácteres");
                    editPassword.setFocusable(true);
                }
                else if (!password2.matches(password)){
                    editPassword2.setError("Las contraseñas no coinciden");
                    editPassword2.setFocusable(true);
                }

                else {
                    registerUser(nombre, email, password);
                    startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));
                    finish();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarLogin();
            }
        });
    }

    private void cambiarLogin(){
        Intent cambiar = new Intent(this, LoginActivity.class);
        startActivity(cambiar);
        finish();
    }

    private void registerUser(String nombre, String email, String password){
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();

                            String  email =user.getEmail();
                            String uid = user.getUid();

                            HashMap<Object, String> hashMap = new HashMap<>();
                            hashMap.put("email", email);
                            hashMap.put("uid", uid);
                            hashMap.put("nombre","");
                            hashMap.put("telefono", "");
                            hashMap.put("imagen", "");

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("Users");
                            reference.child(uid).setValue(hashMap);
                            Toast.makeText(RegisterActivity.this,
                                    "Registrado\n"+user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));
                            finish();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Fallo en autentificación",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}