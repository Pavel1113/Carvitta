package pavelbich.fit.bstu.carvitta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MyCars extends AppCompatActivity {

    User user;
    DatabaseAdapter adapter;
    CarAdapter carAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cars);
        adapter = new DatabaseAdapter(this);
        if(adapter.checkAuthUser()==null){
            Intent auth = new Intent(this,Login.class);
            startActivity(auth);
        }else{
            user = adapter.checkAuthUser();
        }
        ListView mys = findViewById(R.id.my_cars);
        registerForContextMenu(mys);
        ArrayList<Car> arraylist = adapter.getMyCars(user.getUsid());
        carAdapter = new CarAdapter(this,R.layout.list_item,arraylist);
        mys.setAdapter(carAdapter);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.sell:
                sell(info.position); // метод, выполняющий действие при редактировании пункта меню
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    public void sell(Integer pos){
        Car selectedCar = carAdapter.getItem(pos);
        adapter.SellCar(selectedCar.getId());
        carAdapter.clear();
        ArrayList<Car> arraylist = adapter.getMyCars(user.getUsid());
        carAdapter.addAll(arraylist);
        carAdapter.notifyDataSetChanged();
    }
}