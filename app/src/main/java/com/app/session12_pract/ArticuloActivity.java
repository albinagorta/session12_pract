package com.app.session12_pract;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticuloActivity extends AppCompatActivity {

    EditText etIdBuscar, etId, etNombre, etDescripcion, etMarca, etPrecio;
    Button btnIdBuscar, btnEliminar, btnTodosBuscar, btnAgregar, btnEditar;

    ListView lvArticulos;

    List<Articulo> listaArticulos = new ArrayList<>();
    Articulo a;
    Retrofit retrofit;
    APIRest api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo);
        getSupportActionBar().hide();

        etIdBuscar = findViewById(R.id.etIdBuscar);
        etId = findViewById(R.id.etCod);
        etNombre = findViewById(R.id.etNombr);
        etDescripcion = findViewById(R.id.etDescr);
        etMarca = findViewById(R.id.etmarc);
        etPrecio = findViewById(R.id.etpre);
        btnIdBuscar = findViewById(R.id.btnIdBuscar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnTodosBuscar = findViewById(R.id.btnTodosBuscar);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnEditar = findViewById(R.id.btnEditar);
        lvArticulos = findViewById(R.id.rvArticulo);

        retrofit = new AdaptadorRetrofit().getAdapter();
        api = retrofit.create(APIRest.class);

        getArticulos(api);

        btnIdBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etIdBuscar.getText().toString().equals("")) {
                    Toast.makeText(getApplication(), "Inserta un CODIGO del Articulo para buscar", Toast.LENGTH_SHORT).show();
                } else {
                    getArticulo(api, etIdBuscar.getText().toString());
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etIdBuscar.getText().toString().equals("")) {
                    Toast.makeText(getApplication(), "Inserta un CODIGO del Articulo para eliminar", Toast.LENGTH_SHORT).show();
                } else {
                    eliminarArticulo(api, etIdBuscar.getText().toString());
                }
            }
        });

        btnTodosBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getArticulos(api);
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                if(etNombre.getText().toString().equals("") || etDescripcion.getText().toString().equals("") || etMarca.getText().toString().equals("") || etPrecio.getText().toString().equals("")) {
                    Toast.makeText(getApplication(), "Se deben de llenar los campos", Toast.LENGTH_SHORT).show();
                } else {
                    agregarArticulo(api, etNombre.getText().toString(), etDescripcion.getText().toString(), etMarca.getText().toString(), etPrecio.getText().toString());
                }
             }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etId.getText().toString().equals("") || etNombre.getText().toString().equals("") || etDescripcion.getText().toString().equals("") || etMarca.getText().toString().equals("") || etPrecio.getText().toString().equals("")) {
                    Toast.makeText(getApplication(), "Se deben de llenar los campos", Toast.LENGTH_SHORT).show();
                } else {
                    editarArticulo(api, etId.getText().toString(), etNombre.getText().toString(), etDescripcion.getText().toString(), etMarca.getText().toString(), etPrecio.getText().toString());
                }
            }
        });

        lvArticulos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                a=listaArticulos.get(position);
                etId.setText(a.getCodigo());
                etNombre.setText(a.getNombre());
                etDescripcion.setText(a.getDescripcion());
                etMarca.setText(a.getMarca());
                etPrecio.setText(a.getPrecio());
            }
        });




    }

    public void getArticulo(final APIRest api, String id) {
        listaArticulos.clear();
        Call<Articulo> call = api.obtenerArticulo(id);

        call.enqueue(new Callback<Articulo>() {
            @Override
            public void onResponse(Call<Articulo> call, Response<Articulo> response) {
                switch (response.code()) {
                    case 200:
                        listaArticulos.add(response.body());

                        etIdBuscar.setText("");

                        ArrayList<String> lista=new ArrayList<>();
                        for(Articulo a:listaArticulos){
                            lista.add("codigo: "+a.getCodigo()+" Nombre: "+a.getNombre()+" Descripcion: "+a.getDescripcion()+"\nMarca: "+a.getMarca()+" Precio: "+a.getPrecio());
                        }

                        ArrayAdapter adapter=new ArrayAdapter<>(getApplication(),android.R.layout.simple_list_item_1,lista);
                        lvArticulos.setAdapter(adapter);

                        break;
                    case 204:
                        Toast.makeText(getApplication(), "No existe ese registro", Toast.LENGTH_SHORT).show();

                        etIdBuscar.setText("");

                        getArticulos(api);
                        break;

                }
            }

            @Override
            public void onFailure(Call<Articulo> call, Throwable t) {

            }
        });
    }

    public void getArticulos(APIRest api) {
        listaArticulos.clear();
        Call<List<Articulo>> call = api.obtenerArticulos();

        call.enqueue(new Callback<List<Articulo>>() {
            @Override
            public void onResponse(Call<List<Articulo>> call, Response<List<Articulo>> response) {
                listaArticulos = new ArrayList<Articulo>(response.body());

                ArrayList<String> lista=new ArrayList<>();
                for(Articulo a:listaArticulos){
                    lista.add("codigo: "+a.getCodigo()+" Nombre: "+a.getNombre()+" Descripcion: "+a.getDescripcion()+"\nMarca: "+a.getMarca()+" Precio: "+a.getPrecio());
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<>(getApplication(),android.R.layout.simple_list_item_1,lista);
                lvArticulos.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Articulo>> call, Throwable t) {

            }
        });
    }

    public void eliminarArticulo(final APIRest api, String id) {
        listaArticulos.clear();

        Call<Void> call = api.eliminarArticulo(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 200:
                        Toast.makeText(getApplication(), "Se elimino correctamente", Toast.LENGTH_SHORT).show();
                        etIdBuscar.setText("");
                        getArticulos(api);
                        break;
                    case 204:
                        Toast.makeText(getApplication(), "No se elimino el registro", Toast.LENGTH_SHORT).show();
                        etIdBuscar.setText("");
                        break;

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void agregarArticulo(final APIRest api, String nombre, String descripcion, String marca, String precio) {
        listaArticulos.clear();
        Articulo articulo = new Articulo();
        articulo.setNombre(nombre);
        articulo.setDescripcion(descripcion);
        articulo.setMarca(marca);
        articulo.setPrecio(precio);

        Call<Void> call = api.agregarArticulo(articulo);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 400:
                        Toast.makeText(getApplication(), "Faltaron campos.", Toast.LENGTH_SHORT).show();
                        etNombre.setText("");
                        etDescripcion.setText("");
                        etMarca.setText("");
                        etPrecio.setText("");
                        break;
                    case 200:
                        Toast.makeText(getApplication(), "Se inserto correctamente", Toast.LENGTH_SHORT).show();
                        etNombre.setText("");
                        etDescripcion.setText("");
                        etMarca.setText("");
                        etPrecio.setText("");
                        getArticulos(api);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void editarArticulo(final APIRest api, String id, String nombre, String descripcion, String marca, String precio) {
        listaArticulos.clear();
        Articulo articulo = new Articulo();
        articulo.setCodigo(id);
        articulo.setNombre(nombre);
        articulo.setDescripcion(descripcion);
        articulo.setMarca(marca);
        articulo.setPrecio(precio);

        Call<Void> call = api.editarArticulo(articulo);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 400:
                        Toast.makeText(getApplication(), "No se puede editar.", Toast.LENGTH_SHORT).show();
                        etId.setText("");
                        etNombre.setText("");
                        etDescripcion.setText("");
                        etMarca.setText("");
                        etPrecio.setText("");
                        break;
                    case 200:
                        Toast.makeText(getApplication(), "Se edito correctamente", Toast.LENGTH_SHORT).show();
                        etId.setText("");
                        etNombre.setText("");
                        etDescripcion.setText("");
                        etMarca.setText("");
                        etPrecio.setText("");
                        getArticulos(api);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}