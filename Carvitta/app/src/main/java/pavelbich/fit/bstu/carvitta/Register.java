package pavelbich.fit.bstu.carvitta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {


    public DatabaseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView errorView = findViewById(R.id.errorText);
        errorView.setVisibility(View.GONE);
        errorView.setTextColor(Color.RED);
        adapter = new DatabaseAdapter(this);
    }

    public void toAuthActivity(View view) {
        Intent auth = new Intent(this, Login.class);
        startActivity(auth);
    }

    public void register(View view) {
        EditText ed_login = findViewById(R.id.fieldLogin);
        EditText ed_name = findViewById(R.id.filedName);
        EditText ed_password = findViewById(R.id.filedPassword);
        EditText ed_password2 = findViewById(R.id.filedPassword2);
        TextView errorView = findViewById(R.id.errorText);

        String login = ed_login.getText().toString();
        String name = ed_name.getText().toString();
        String password = ed_password.getText().toString();
        String password2 = ed_password2.getText().toString();

        if(login.length() <= 4 ){
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("Длина логина должна быть больше 4 символов!");
        }else if(name.length() <= 4){
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("Длина имени должна быть больше 4 символов!");
        }else if(password.length() <= 5){
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("Длина пароля должна быть больше 5 символов!");
        }else if(!password.equals(password2)){
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("Пароли не совпадают!");
        }else if(adapter.checkLogin(login) == true){
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("Такой логин уже используется!");
        }else{
            adapter.open();
            adapter.addUser(login,name,password,0);
            adapter.close();
            errorView.setVisibility(View.VISIBLE);
            errorView.setTextColor(Color.GREEN);
            errorView.setText("Успешно!");
            Handler handler = new Handler();
            Intent auth = new Intent(this,Login.class);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(auth);
                }
            },1000);
        }


    }
}