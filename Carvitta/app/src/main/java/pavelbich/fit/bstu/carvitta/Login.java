package pavelbich.fit.bstu.carvitta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    public DatabaseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        adapter = new DatabaseAdapter(this);
        TextView errorView = findViewById(R.id.errorLogin);
        errorView.setVisibility(View.GONE);
        errorView.setTextColor(Color.RED);
    }

    public void toRegActivity(View view) {
        Intent reg = new Intent(this, Register.class);
        startActivity(reg);
    }

    public void authorization(View view) {
        EditText ed_login = findViewById(R.id.authLogin);
        EditText ed_password = findViewById(R.id.authPassword);
        String login  = ed_login.getText().toString();
        String password = ed_password.getText().toString();
        TextView errorView = findViewById(R.id.errorLogin);

        if(login.length() == 0){
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("Заполните поле Логин!");
        }else if(password.length() == 0){
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("Заполните поле Пароль!");
        }else if(adapter.checkLogin(login) == false){
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("Такого логина не существует!");
        }else if(adapter.auth(login,password)==null){
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("Неправильный логин или пароль!");
        }else{
            User user = adapter.auth(login,password);
            user.isAuth = 1;
            adapter.enter(login);
            errorView.setVisibility(View.VISIBLE);
            errorView.setTextColor(Color.GREEN);
            errorView.setText("Успешно!");
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