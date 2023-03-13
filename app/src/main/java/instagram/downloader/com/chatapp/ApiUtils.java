package instagram.downloader.com.chatapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static instagram.downloader.com.chatapp.ValuesClass.BASE_URL;

/**
 * Created by Евгений on 04.03.2023.
 */

public class ApiUtils {
    public static Retrofit retrofit = null;
    public static ApiInterface getClients(){
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }
}
