package com.app.session12_pract;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.session12_pract.DAO.Usuario;
import com.app.session12_pract.DAO.UsuarioDAO;

public class RegistrarseActivity extends AppCompatActivity {

    UsuarioDAO daousu=new UsuarioDAO(this);
    EditText edtusu,edtpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        daousu.openBD();
        edtusu=findViewById(R.id.txtusur);
        edtpass=findViewById(R.id.txtpassr);

    }

    public void Registrarse(View v){

        daousu.registrarUsuario(new Usuario(edtusu.getText().toString(),edtpass.getText().toString()));
        Toast.makeText(this, "USUARIO REGISTRADO", Toast.LENGTH_SHORT).show();
    }

    public void Regresar_ingresar_registrarse(View v){
        Intent i =new Intent(this,MainActivity.class);
        startActivity(i);
    }


}