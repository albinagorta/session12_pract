package com.app.session12_pract.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.app.session12_pract.util.BDUsuario;
import com.app.session12_pract.util.ConstantesBD;

import java.util.ArrayList;

public class UsuarioDAO {
    BDUsuario bdusuario;
    SQLiteDatabase database;

    public UsuarioDAO(Context c) {
        bdusuario=new BDUsuario(c);
    }
    public  void openBD(){
        database=bdusuario.getWritableDatabase();
    }
    public  void closeBD(){
        bdusuario.close();
        database.close();
    }
    public  void registrarUsuario(Usuario u){
        try{
            ContentValues values=new ContentValues();
            values.put("usu",u.getUsu());
            values.put("pass",u.getPass());
            database.insert(ConstantesBD.NOMBRETABLA,null,values);
        }catch (Exception e){

        }
    }



    public boolean ingresar(String usu,String pass){
        Boolean rest=false;
        try {
            Cursor c=database.rawQuery("SELECT * FROM "+ConstantesBD.NOMBRETABLA+" WHERE usu='"+usu+"' AND pass='"+pass+"'",
                    null);
            while (c.moveToNext()){
                rest=true;
            }

        }catch (Exception e){
            rest= false;
        }
        return rest;

    }

}