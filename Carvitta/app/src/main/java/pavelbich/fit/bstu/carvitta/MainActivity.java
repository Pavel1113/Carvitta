package pavelbich.fit.bstu.carvitta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    User user;
    DatabaseAdapter adapter;
    CheckConnection checkConnection = new CheckConnection(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean isConnected = checkConnection.check();
        if(!isConnected){
            Intent no_internet = new Intent(this,No_Internet.class);
            startActivity(no_internet);
            return;
        }
        adapter = new DatabaseAdapter(this);
        if(adapter.checkAuthUser()==null){
            Intent auth = new Intent(this,Login.class);
            startActivity(auth);
        }else{
            user = adapter.checkAuthUser();
        }
        ListView ads = findViewById(R.id.ads_cars);
        ArrayList<Car> arraylist = adapter.getAllCars();

        JSONHelper.exportToJSON(this,arraylist);

        Intent carInfo = new Intent(this, CarInfo.class);
        CarAdapter carAdapter = new CarAdapter(this,R.layout.list_item,arraylist);
        ads.setAdapter(carAdapter);
        ads.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Car car = (Car) adapterView.getItemAtPosition(i);
                carInfo.putExtra(Car.class.getSimpleName(),car);
                startActivity(carInfo);
            }
        });
    }

    public void toAccount(View view){
        Intent acc = new Intent(this,Account.class);
        startActivity(acc);
    }
    public void toAdd(View view){
        Intent add = new Intent(this,AddCar.class);
        startActivity(add);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void toFilters(MenuItem item) {
        Intent filters = new Intent(this,Filters.class);
        startActivity(filters);
    }
}