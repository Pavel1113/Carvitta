package pavelbich.fit.bstu.carvitta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class CarInfo extends AppCompatActivity {

    Car car;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);

        Bundle args = getIntent().getExtras();
        if(args!=null){
            car = (Car) args.getSerializable(Car.class.getSimpleName());
        }
        ImageView imgView1 = findViewById(R.id.imageCarInfo1);
        ImageView imgView2 = findViewById(R.id.imageCarInfo2);
        TextView markaModelView = findViewById(R.id.markaModelCarInfo);
        TextView priceView = findViewById(R.id.priceCarInfo);
        TextView infoView = findViewById(R.id.infoCarInfo);
        TextView phoneView2 = findViewById(R.id.phoneCarInfo2);

        try {
            this.getContentResolver().takePersistableUriPermission(Uri.parse(car.getImage()), Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(car.getImage()));
            Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(car.getImage2()));
            imgView2.setImageBitmap(bitmap1);
            imgView1.setImageBitmap(bitmap2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        markaModelView.setText(car.getMarka()+" "+car.getModel());
        priceView.setText(car.getPrice()+" BYN");
        infoView.setText(car.getYear()+" г., "+car.getMehAuto()+", "+car.getVolume()+" л, "+car.getTypeFuel()+", "+car.getShape()+", "+car.getDistance()+" км");
        phoneView2.setText(car.getPhone());
    }
    public void call(View view){
        TextView uPhone = findViewById(R.id.phoneCarInfo2);
        String phoneNo = uPhone.getText().toString();
        if (!TextUtils.isEmpty(phoneNo)) {
            String dial = "tel:" + phoneNo;
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
        }
    }
    public void toCars(View view){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
    public void toAccount(View view){
        Intent i = new Intent(this,Account.class);
        startActivity(i);
    }
    public void toAddCar(View view){
        Intent i = new Intent(this,AddCar.class);
        startActivity(i);
    }
}