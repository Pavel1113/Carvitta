package pavelbich.fit.bstu.carvitta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class No_Internet extends AppCompatActivity {

    CheckConnection checkConnection = new CheckConnection(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
    }

    public void reloadInternet(View view){
        boolean isConnected = checkConnection.check();
        if(isConnected){
            TextView noInternetText = findViewById(R.id.textNoInternet);
            noInternetText.setText("ПОДКЛЮЧЕНИЕ ВОССТАНОВЛЕНО");
            noInternetText.setTextColor(Color.GREEN);
            Handler handler = new Handler();
            Intent main = new Intent(this,MainActivity.class);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(main);
                }
            },1000);
        }
    }
}