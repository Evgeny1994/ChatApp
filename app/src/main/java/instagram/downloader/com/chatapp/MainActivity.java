package instagram.downloader.com.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;


import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static int SIGN_IN_CODE = 1;
    private RelativeLayout activity_main;
    private FirebaseListAdapter<Message> adapter;
    FloatingActionButton floatingActionButton;
    private EditText textField;
    String title = "d6rs0SxulQs:APA91bFrhHvLtgPuJoHvwZg5csA0lH0a_BgrZ--jrXWuiPZkzs27thNpzET9D-2RzI8jPi23rr0p4zB6EZk66AkVd09Qx1oi_908TvGnZpUfMEktWwx5P-C8CXwefZ3_fJoz6kdv5b1G";
    String message = "сука";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity_main = findViewById(R.id.activity_main);
        floatingActionButton = findViewById(R.id.btnSend);
        textField = findViewById(R.id.messageField);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_CODE);
        } else {
            Snackbar.make(activity_main, "Вы авторизованы", Snackbar.LENGTH_LONG).show();
            displayAllMessages();
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textField.getText().toString();

                if (textField.getText().toString() == "") {
                    return;
                }
                /////ВЗГЛЯНУТЬ ПОЧЕМУ ВЫПАДАЕТ ОШИБКА
                FirebaseDatabase.getInstance().getReference().push().setValue(new Message
                        (FirebaseAuth.getInstance().getCurrentUser().getEmail(), textField.getText().toString()));
                FirebaseMessaging.getInstance()
                        .subscribeToTopic("All");
                sendNoti();
                textField.setText("");
                //Как получить токен пользователя
                // String token = FirebaseInstanceId.getInstance().getToken();
                //  Log.i("FCM Registration Token: ", token);
            }
        });
    }

    private void sendNoti() {
        String S2 = textField.getText().toString();
        ApiUtils.getClients().sendNotification(new PushNotification(new NotificationData(message, S2), title))
                .enqueue(new Callback<PushNotification>() {
                    @Override
                    public void onResponse(Call<PushNotification> call, Response<PushNotification> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Notification sucesfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PushNotification> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    private void msg() {
//        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
//            FirebaseAuth.getInstance()
//                    .signInAnonymously()
//                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                        @Override
//                        public void onSuccess(AuthResult authResult) {
//                            FirebaseMessaging.getInstance()
//                                    .subscribeToTopic("All");
//                        }
//                    });

//        }

//    }

    private void displayAllMessages() {
        Calendar now = Calendar.getInstance();
        ListView listOfMessage = findViewById(R.id.list_of_messages);
        adapter = new FirebaseListAdapter<Message>(this, Message.class, R.layout.list_item, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, Message model, int position) {
                TextView mess_user, mess_time, mess_text;
                mess_user = v.findViewById(R.id.message_user);
                mess_time = v.findViewById(R.id.message_time);
                mess_text = v.findViewById(R.id.message_text);
                mess_user.setText(model.getUserName());
                mess_text.setText(model.getTextMessage());
                //ПОСМОТРЕТЬ ПОЧЕМУ ОШИБКА выскакивает
              //  mess_time.setText(now.toString());
            }
        };
        listOfMessage.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(resultCode, resultCode, data);
        if (requestCode == SIGN_IN_CODE) {
            if (resultCode == RESULT_OK) {
                Snackbar.make(activity_main, "Вы авторизованы", Snackbar.LENGTH_LONG).show();
                displayAllMessages();
            } else {
                Snackbar.make(activity_main, "Вы не авторизованы", Snackbar.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
