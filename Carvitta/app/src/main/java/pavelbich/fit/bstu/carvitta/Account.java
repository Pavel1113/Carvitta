package pavelbich.fit.bstu.carvitta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class Account extends AppCompatActivity {

    DatabaseAdapter adapter;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        adapter = new DatabaseAdapter(this);
        user = adapter.getCorrectPassword();

        TextView loginView = findViewById(R.id.acc_login);
        TextView nameView = findViewById(R.id.acc_name);
        TextView passwordView = findViewById(R.id.acc_password);
        CheckBox showPassCheck = findViewById(R.id.showPassword);
        showPassCheck.setChecked(false);
        loginView.setText("Ваш логин: "+user.getLogin());
        nameView.setText("Ваше имя: "+user.getName());
        passwordView.setText("Ваш пароль: *****");
        showPassCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    passwordView.setText("Ваш пароль: "+user.getPassword());
                }else{
                    passwordView.setText("Ваш пароль: *****");
                }
            }
        });
    }

    public void exit(View view) {
        adapter.exit(user.getLogin());
        Intent auth = new Intent(this,Login.class);
        startActivity(auth);
    }
    public void toMain(View view){
        Intent main = new Intent(this,MainActivity.class);
        startActivity(main);
    }
    public void toAdd(View view){
        Intent add = new Intent(this,AddCar.class);
        startActivity(add);
    }
    public void toMyAds(View view){
        Intent my = new Intent(this,MyCars.class);
        startActivity(my);
    }
}