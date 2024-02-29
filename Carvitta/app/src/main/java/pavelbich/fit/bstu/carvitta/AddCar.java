package pavelbich.fit.bstu.carvitta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Messenger;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class AddCar extends AppCompatActivity {
    static final int GALLERY_REQUEST = 1;
    String uri1;
    String uri2;
    public DatabaseAdapter adapter;
    int SELECT_PICTURES = 1;
    public List<String> pathList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        adapter = new DatabaseAdapter(this);
    }
    public void toMain(View view){
        Intent main = new Intent(this,MainActivity.class);
        startActivity(main);
    }
    public void toAccount(View view){
        Intent acc = new Intent(this,Account.class);
        startActivity(acc);
    }
    public void addCar(View view){

        Spinner markiSpin = findViewById(R.id.markaSpin);
        Spinner modelsSpin = findViewById(R.id.modelSpin);
        EditText priceED = findViewById(R.id.priceEditText);
        Spinner yearSpin = findViewById(R.id.yearSpin);
        RadioButton mehRadio = findViewById(R.id.mehRadio);
        RadioButton autoRadio = findViewById(R.id.autoRadio);
        EditText volumeED = findViewById(R.id.volumeEditText);
        RadioButton benzinRadio = findViewById(R.id.benzinRadio);
        RadioButton diezelRadio = findViewById(R.id.diezelRadio);
        Spinner kuzovSpin = findViewById(R.id.kuzovSpin);
        EditText distanceED = findViewById(R.id.distanceEditText);
        ImageView imageView1 = findViewById(R.id.imageViewFirst);
        ImageView imageView2 = findViewById(R.id.imageViewSecond);
        EditText phoneED = findViewById(R.id.phoneEditText);
        String markiValue = markiSpin.getSelectedItem().toString();
        String modelvalue = modelsSpin.getSelectedItem().toString();
        String priceValue = priceED.getText().toString();
        String yearValue = yearSpin.getSelectedItem().toString();
        String mehAuto;
        if(mehRadio.isChecked()){
            mehAuto = "механика";
        }else{
            mehAuto = "автомат.";
        }
        String volumeValue = volumeED.getText().toString();
        String fuelValue;
        if(benzinRadio.isChecked()){
            fuelValue = "бензин";
        }else{
            fuelValue = "дизель";
        }
        String kuzovValue = kuzovSpin.getSelectedItem().toString();
        String distanceValue = distanceED.getText().toString();
        User user = adapter.checkAuthUser();
        Integer usid = user.getUsid();
        String phone = phoneED.getText().toString();
        Car car = new Car(0,usid,markiValue,modelvalue,priceValue,yearValue,mehAuto,volumeValue,fuelValue,kuzovValue,distanceValue,pathList.get(0),pathList.get(1),phone,0);

        adapter.addCar(car,this);

        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
    public void addImageFirst(View view){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*"); //allows any image file type. Change * to specific extension to limit it
//**The following line is the important one!
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURES);
    }
    public void addImageSecond(View view){
        Intent photoPickerIntent1 = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        photoPickerIntent1.setType("image/*");
        startActivityForResult(photoPickerIntent1, GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_PICTURES) {
            if(resultCode == Activity.RESULT_OK) {
                pathList = new ArrayList<>();
                if(data.getClipData() != null) {
                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for(int i = 0; i < count; i++) {

                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        this.getContentResolver().takePersistableUriPermission(imageUri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        pathList.add(imageUri.toString());
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                    }

                    ImageView imageView1 = findViewById(R.id.imageViewFirst);
                    ImageView imageView2 = findViewById(R.id.imageViewSecond);
                    Uri uri1  = Uri.parse(pathList.get(0));
                    try {
                        Bitmap bitmap = null;
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(pathList.get(0)));
                        imageView1.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Bitmap bitmap = null;
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(pathList.get(1)));
                        imageView2.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if(data.getData() != null) {
                String imagePath = data.getData().getPath();
                //do something with the image (save it to some directory or whatever you need to do with it here)
            }
        }
    }
}

