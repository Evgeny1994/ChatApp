package instagram.downloader.com.chatapp;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static instagram.downloader.com.chatapp.ValuesClass.CONTENT_TYPE;
import static instagram.downloader.com.chatapp.ValuesClass.SERVER_KEY;

/**
 * Created by Евгений on 04.03.2023.
 */

public interface ApiInterface {
        @Headers({"Authorization: "+SERVER_KEY,"Content-Type:"+CONTENT_TYPE})
        @POST("fcm/send")
        Call<PushNotification> sendNotification(@Body PushNotification pushNotification);
    }
