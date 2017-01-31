package br.ufscar.connect.interfaces;

import java.util.List;

import br.ufscar.connect.Models.Evaluation;
import br.ufscar.connect.Models.Report;
import br.ufscar.connect.Models.User;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
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

    //USUARIOS - CREATE : POST
    @FormUrlEncoded
    @POST("users/create")
    Call<User> usersCreate(@Field("usertype") String user_type, @Field("username") String username,
                           @Field("name") String name, @Field("lastname") String last_name, @Field("email") String email,
                           @Field("password") String password, @Field("image_url") String image_url);

    //USUARIOS - UPDATE : PUT
    @FormUrlEncoded
    @PUT("users/update")
    Call<User> usersUpdate(@Field("usertype") String user_type, @Field("username") String username,
                           @Field("name") String name, @Field("lastname") String last_name, @Field("email") String email,
                           @Field("password") String password, @Field("image_url") String imagem_url);

    //DENUNCIAS - CREATE : POST
    @FormUrlEncoded
    @POST("reports/create")
    Call<Report> reportCreate(@Field("address") String address, @Field("category") String category, @Field("description") String description,
                              @Field("user_id") String USER_ID, @Field("image_url") String imagem_url, @Field("created_at") String craeted_at);

    //AVALIACOES - CREATE : POST
    @FormUrlEncoded
    @POST("evaluation/create")
    Call<Report> evaluationCreate(@Field("placename") String placename, @Field("infra") Float infra, @Field("limp") Float limp, @Field("geral") Float geral,
                                  @Field("seg") Float seg, @Field("acess") Float acess, @Field("user_id") String USER_ID, @Field("created_at") String craeted_at);

    //LISTAR DENUNCIAS : GET
    @GET("reports")
    Call<List<Report>> reportList();

    //LISTAR AVALIACOES : GET
    @GET("evalualtions")
    Call<List<Evaluation>> evaluationList();

}