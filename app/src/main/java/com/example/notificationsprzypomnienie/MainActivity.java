package com.example.notificationsprzypomnienie;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID_DEFAULT = "channel_default";
    private static final String CHANNEL_ID_LOW = "channel_low";
    private static final String CHANNEL_ID_HIGH = "channel_high";
    private static int notificationID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        createNotificationChannels();

        Button buttonLong = findViewById(R.id.ButtonLong);
        Button pictureButton = findViewById(R.id.PictureButton);
        Button addLineButton = findViewById(R.id.ButtonAddLine);

        buttonLong.setOnClickListener(v -> sendNotificationLong());
        pictureButton.setOnClickListener(v -> sendNotificationPicture());
        addLineButton.setOnClickListener(v -> sendNotificationAddLine());
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            NotificationChannel channelDefault = new NotificationChannel(
                    CHANNEL_ID_DEFAULT, "Domyślny kanał", NotificationManager.IMPORTANCE_DEFAULT);
            channelDefault.setDescription("Opis domyślnego kanału powiadomień");

            NotificationChannel channelLow = new NotificationChannel(
                    CHANNEL_ID_LOW, "Kanał niski", NotificationManager.IMPORTANCE_LOW);
            channelLow.setDescription("Opis kanału o niskim priorytecie");

            NotificationChannel channelHigh = new NotificationChannel(
                    CHANNEL_ID_HIGH, "Kanał wysoki", NotificationManager.IMPORTANCE_HIGH);
            channelHigh.setDescription("Opis kanału o wysokim priorytecie");

            notificationManager.createNotificationChannel(channelDefault);
            notificationManager.createNotificationChannel(channelLow);
            notificationManager.createNotificationChannel(channelHigh);
        }
    }

    private void sendNotificationLong() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
                return;
            }
        }

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID_HIGH)
                .setSmallIcon(R.drawable.dice1)
                .setContentTitle("Długie Powiadomienie")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        "To jest długie powiadomienie z dużą ilością tekstu. Możesz tutaj dodać więcej informacji."))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationID++, builder.build());
    }

    private void sendNotificationPicture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
                return;
            }
        }

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dice1);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID_HIGH)
                .setSmallIcon(R.drawable.dice1)
                .setContentTitle("Powiadomienie z obrazem")
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationID++, builder.build());
    }

    private void sendNotificationAddLine() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
                return;
            }
        }

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID_LOW)
                .setSmallIcon(R.drawable.dice1)
                .setContentTitle("Powiadomienie z liniami")
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("Linia 1")
                        .addLine("Linia 2")
                        .addLine("Linia 3"))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationID++, builder.build());
    }

}