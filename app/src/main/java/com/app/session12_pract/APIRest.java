package com.app.session12_pract;


import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface APIRest {
    String URL_PHP="API.php";

    @GET(URL_PHP)
    Call<List<Articulo>> obtenerArticulos();

    @GET(URL_PHP)
    Call<Articulo> obtenerArticulo(
            @Query("codigo") String codigo
    );

    @POST(URL_PHP)
    Call<Void> agregarArticulo(
            @Body Articulo articulo
    );

    @PUT(URL_PHP)
    Call<Void> editarArticulo(
            @Body Articulo articulo
    );

    @DELETE(URL_PHP)
    Call<Void> eliminarArticulo(
            @Query("codigo") String codigo
    );
}