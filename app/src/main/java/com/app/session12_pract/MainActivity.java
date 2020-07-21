package com.app.session12_pract;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.session12_pract.DAO.Usuario;
import com.app.session12_pract.DAO.UsuarioDAO;

public class MainActivity extends AppCompatActivity {

    UsuarioDAO daousu=new UsuarioDAO(this);
    EditText edtusu,edtpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        daousu.openBD();
        edtusu=findViewById(R.id.txtusu);
        edtpass=findViewById(R.id.txtpass);
    }

    public void INGRESAR_USU(View v){
        if(!edtusu.getText().toString().isEmpty() || !edtpass.getText().toString().isEmpty()) {
            if (daousu.ingresar(edtusu.getText().toString(), edtpass.getText().toString())) {
                Intent i = new Intent(this, ArticuloActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(this, "USUARIO NO EXISTE EN LA bd", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "INGRESE USUARIO Y PASS", Toast.LENGTH_SHORT).show();
        }


    }

    public void ir_a_registrarse(View v){
        Intent i =new Intent(this,RegistrarseActivity.class);
        startActivity(i);
    }
}