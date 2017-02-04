package br.ufscar.connect.interfaces;

import java.util.List;

import br.ufscar.connect.models.Evaluation;
import br.ufscar.connect.models.Report;
import br.ufscar.connect.models.User;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;

/**
 * Created by Arthur on 16/01/2017.
 */

// Interface criada para ter todas as chamadas do backend
public interface ConnectUFSCarApi {

    String BASE_URL = "http://138.197.20.132:8000/";

    Retrofit RETROFIT = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build();


    //------------------------------------------------------------------------
    //LISTA DE REQUESTS (RETROFIT2)

    //LOGIN : POST
    @FormUrlEncoded
    @POST("login")
    Call<User> login(@Field("username") String username, @Field("password") String password);

    //------------------------------------------------------------------------
    //USUARIOS - CREATE : POST
    @POST("users/create")
    Call<User> usersCreate(@Body User user);

    //USUARIOS - UPDATE : PUT
    @FormUrlEncoded
    @PUT("users/update")
    Call<User> usersUpdate(@Field("usertype") String user_type, @Field("username") String username,
                           @Field("name") String name, @Field("lastname") String last_name, @Field("email") String email,
                           @Field("password") String password, @Field("image_url") String image_url);

    //------------------------------------------------------------------------
    //DENUNCIAS - CREATE : POST
    @POST("reports/create")
    Call<Report> reportCreate(@Body Report report);

    //LISTAR DENUNCIAS : GET
    @GET("reports")
    Call<List<Report>> reportList();

    //------------------------------------------------------------------------
    //AVALIACOES - CREATE : POST
    @POST("evaluation/create")
    Call<Evaluation> evaluationCreate(@Body Evaluation evaluation);

    //LISTAR AVALIACOES : GET
    @GET("evaluations")
    Call<List<Evaluation>> evaluationList();

}