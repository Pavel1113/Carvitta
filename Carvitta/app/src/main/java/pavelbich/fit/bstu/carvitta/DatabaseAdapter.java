package pavelbich.fit.bstu.carvitta;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatabaseAdapter {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    String response = null;
    HttpHandler httpHandler = new HttpHandler();

    final Object lock = new Object();
    public DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }
//    public static Bitmap getBitmapfromUri(Context context,Uri uri) throws IOException{
//        InputStream inputStream = context.getContentResolver().openInputStream(uri);
//        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//        inputStream.close();
//        return bitmap;
//    }
    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public long addUser(String login, String name, String password, Integer isAuth){
        ContentValues cv = new ContentValues();
        open();
        cv.put(DatabaseHelper.COLUMN_LOGIN,login);
        cv.put(DatabaseHelper.COLUMN_NAME, name);
        cv.put(DatabaseHelper.COLUMN_PASSWORD,password);
        cv.put(DatabaseHelper.COLUMN_ISAUTH,isAuth);
        database.insert(DatabaseHelper.TABLE_USERS, null, cv);
        close();
        password = Encryption_SHA256.encrypt(password);
        JSONObject json = new JSONObject();
        try{
            json.put("p_login",login);
            json.put("p_name",name);
            json.put("p_password",password);
            json.put("p_isAuth",isAuth);

        }catch(JSONException e){
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    httpHandler.post("http://10.0.2.2:5559/addUser",json.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();






        return  database.insert(DatabaseHelper.TABLE_USERS, null, cv);
    }
//    private byte[] convertImageToByteArray(Bitmap imageBitmap) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        return stream.toByteArray();
//    }
    public void addCar(Car car, Context context){
//        open();
//        ContentValues cv = new ContentValues();
//        cv.put(DatabaseHelper.COLUMN_USID, car.getUsid());
//        cv.put(DatabaseHelper.COLUMN_MARKA, car.getMarka());
//        cv.put(DatabaseHelper.COLUMN_MODEL, car.getModel());
//        cv.put(DatabaseHelper.COLUMN_PRICE, car.getPrice());
//        cv.put(DatabaseHelper.COLUMN_YEAR, car.getYear());
//        cv.put(DatabaseHelper.COLUMN_MEHAUTO, car.getMehAuto());
//        cv.put(DatabaseHelper.COLUMN_VOLUME, car.getVolume());
//        cv.put(DatabaseHelper.COLUMN_TYPEFUEL, car.getTypeFuel());
//        cv.put(DatabaseHelper.COLUMN_SHAPE, car.getShape());
//        cv.put(DatabaseHelper.COLUMN_DISTANCE,car.getDistance());
//        cv.put(DatabaseHelper.COLUMN_IMG, car.getImage());
//        cv.put(DatabaseHelper.COLUMN_IMG2,car.getImage2());
//        cv.put(DatabaseHelper.COLUMN_PHONE,car.getPhone());
//        cv.put(DatabaseHelper.COLUMN_ISSELL,false);
        Integer usid = car.getUsid();
        String marka = car.getMarka();
        String model = car.getModel();
        String price = car.getPrice();
        String year = car.getYear();
        String mehauto = car.getMehAuto();
        String volume = car.getVolume();
        String typefuel = car.getTypeFuel();
        String shape = car.getShape();
        String distance = car.getDistance();
//        Bitmap bitmap1;
//        Bitmap bitmap2;
//        byte[] imgByteArray1 = new byte[0];
//        byte[] imgByteArray2 = new byte[0];
        String img1 = car.getImage();
        String img2 = car.getImage2();
        String phone = car.getPhone();
        Integer issell = car.getSell();
        try{
//            bitmap1 = getBitmapfromUri(context,Uri.parse(car.getImage()));
//            bitmap2 = getBitmapfromUri(context,Uri.parse(car.getImage()));
//            imgByteArray1 = convertImageToByteArray(bitmap1);
//            imgByteArray2 = convertImageToByteArray(bitmap2);
//            img1 = Base64.encodeToString(imgByteArray1,Base64.DEFAULT);
//            img2 = Base64.encodeToString(imgByteArray2,Base64.DEFAULT);

        }catch (Exception e){
            e.printStackTrace();
        }
        JSONObject json = new JSONObject();
        try{
            json.put("p_usid",usid);
            json.put("p_marka",marka);
            json.put("p_model",model);
            json.put("p_price",price);
            json.put("p_year",year);
            json.put("p_mehauto",mehauto);
            json.put("p_volume",volume);
            json.put("p_typefuel",typefuel);
            json.put("p_shape",shape);
            json.put("p_distance",distance);
            json.put("p_img",img1);
            json.put("p_img2",img2);
            json.put("p_phone",phone);
            json.put("p_issell",issell);

        }catch(JSONException e){
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    httpHandler.post("http://10.0.2.2:5559/addCar",json.toString());
                    synchronized (lock){
                        lock.notify();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        synchronized (lock){
            try{
                lock.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        //close();
    }
    private Cursor allCarsCursor(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_CARID, DatabaseHelper.COLUMN_USID,DatabaseHelper.COLUMN_MARKA, DatabaseHelper.COLUMN_MODEL,DatabaseHelper.COLUMN_PRICE,DatabaseHelper.COLUMN_YEAR,DatabaseHelper.COLUMN_MEHAUTO,
                DatabaseHelper.COLUMN_VOLUME,DatabaseHelper.COLUMN_TYPEFUEL,DatabaseHelper.COLUMN_SHAPE,DatabaseHelper.COLUMN_DISTANCE,DatabaseHelper.COLUMN_IMG,DatabaseHelper.COLUMN_IMG2, DatabaseHelper.COLUMN_PHONE};
        return database.query(DatabaseHelper.TABLE_CARS,columns,null,null,null,null,null);
    }
    private Cursor myCarsCursor(Integer usid){
        return database.rawQuery("SELECT * FROM "+DatabaseHelper.TABLE_CARS+" WHERE "+DatabaseHelper.COLUMN_USID+" = ?",new String[] {usid.toString()});
    }
    public ArrayList<Car> getMyCars(Integer userid){
        ArrayList<Car> myarray = new ArrayList<>();
        JSONObject json = new JSONObject();
        try{
            json.put("p_usid",userid);
        }catch(JSONException e){
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    response = httpHandler.post("http://10.0.2.2:5559/getMyCars",json.toString());

                    synchronized (lock){
                        lock.notify();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        synchronized (lock){
            try{
                lock.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        try {
            JSONArray arrayJson = new JSONArray(response);
            for (int i = 0; i < arrayJson.length(); i++) {
                JSONObject carJson = arrayJson.getJSONObject(i);
                Integer id = carJson.getInt("id");
                Integer usid = carJson.getInt("usid");
                String marka = carJson.getString("marka");
                String model = carJson.getString("model");
                String price = carJson.getString("price");
                String year = carJson.getString("year");
                String mehauto = carJson.getString("mehauto");
                String volume = carJson.getString("volume");
                String typefuel = carJson.getString("typefuel");
                String shape = carJson.getString("shape");
                String distance = carJson.getString("distance");
                String img1 = carJson.getString("img");
                String img2 = carJson.getString("img2");
                String phone = carJson.getString("phone");
                Integer issell = carJson.getInt("issell");
                Car car = new Car(
                        id,
                        usid,
                        marka,
                        model,
                        price,
                        year,
                        mehauto,
                        volume,
                        typefuel,
                        shape,
                        distance,
                        img1,
                        img2,
                        phone,
                        issell
                );
                myarray.add(car);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return myarray;
    }
    public ArrayList<Car> getAllCars(){
        ArrayList<Car> arrayCars = new ArrayList<>();
        JSONObject json = new JSONObject();
        try{
            json.put("p_issell",0);
        }catch(JSONException e){
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    response = httpHandler.post("http://10.0.2.2:5559/getAllCars",json.toString());

                    synchronized (lock){
                        lock.notify();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        synchronized (lock){
            try{
                lock.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        try {
            JSONArray arrayJson = new JSONArray(response);
            for (int i = 0; i < arrayJson.length(); i++) {
                JSONObject carJson = arrayJson.getJSONObject(i);
                Integer id = carJson.getInt("id");
                Integer usid = carJson.getInt("usid");
                String marka = carJson.getString("marka");
                String model = carJson.getString("model");
                String price = carJson.getString("price");
                String year = carJson.getString("year");
                String mehauto = carJson.getString("mehauto");
                String volume = carJson.getString("volume");
                String typefuel = carJson.getString("typefuel");
                String shape = carJson.getString("shape");
                String distance = carJson.getString("distance");
                String img1 = carJson.getString("img");
                String img2 = carJson.getString("img2");
                String phone = carJson.getString("phone");
                Integer issell = carJson.getInt("issell");
                Car car = new Car(
                        id,
                        usid,
                        marka,
                        model,
                        price,
                        year,
                        mehauto,
                        volume,
                        typefuel,
                        shape,
                        distance,
                        img1,
                        img2,
                        phone,
                        issell
                );
                arrayCars.add(car);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return arrayCars;
    }
    public void SellCar(Integer carId){
        JSONObject json = new JSONObject();
        try{
            json.put("p_id",carId);
        }catch(JSONException e){
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    response = httpHandler.post("http://10.0.2.2:5559/sellCar",json.toString());

                    synchronized (lock){
                        lock.notify();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        synchronized (lock){
            try{
                lock.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public boolean checkLogin(String login){
        Boolean a = false;

        User user = null;
        JSONObject json = new JSONObject();
        try{
            json.put("p_login",login);
        }catch(JSONException e){
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    response = httpHandler.post("http://10.0.2.2:5559/checkLogin",json.toString());
                    Log.d("qqqqq",response.toString());
                    synchronized (lock){
                        lock.notify();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        synchronized (lock){
            try{
                lock.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        try{
            JSONArray jsonArray = new JSONArray(response);
            if(!jsonArray.isNull(0)){
                a = true;
            }else{
                a = false;
            }
            Log.d("loginqqq",a.toString());
        }catch(Exception e){
            e.printStackTrace();
        }

        return a;
    }

    public User auth(String login, String password){
        password = Encryption_SHA256.encrypt(password);
        User user = null;
        JSONObject json = new JSONObject();
        Integer id;
        String name;
        Integer isAuth;
        try{
            json.put("p_login",login);
            json.put("p_password",password);
        }catch(JSONException e){
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    response = httpHandler.post("http://10.0.2.2:5559/auth",json.toString());
                    Log.d("qqqqq",response.toString());
                    synchronized (lock){
                        lock.notify();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        synchronized (lock){
            try{
                lock.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        Log.d("eee",response);
        try{
           JSONArray arrayJson = new JSONArray(response);
           JSONObject userJson = arrayJson.getJSONObject(0);

           id = userJson.getInt("id");
           name = userJson.getString("name");
           isAuth = userJson.getInt("isAuth");
           Log.d("wwwww",name);
           if(id != null && name != null && isAuth != null){
               user = new User(id,login,name,password,isAuth);
           }
        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }
    public User checkAuthUser(){
        User user = null;
        JSONObject json = new JSONObject();
        try{
            json.put("p_isAuth",1);
        }catch(JSONException e){
            e.printStackTrace();
        }
        Integer id;
        String login;
        String name;
        String password;
        Integer isAuth;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    response = httpHandler.post("http://10.0.2.2:5559/checkAuthUser",json.toString());
                    Log.d("checkauth",response.toString());
                    synchronized (lock){
                        lock.notify();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        synchronized (lock){
            try{
                lock.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        try{
            JSONArray arrayJson = new JSONArray(response);

            if(!arrayJson.isNull(0)){
                JSONObject userJson = arrayJson.getJSONObject(0);

                id = userJson.getInt("id");
                login = userJson.getString("login");
                name = userJson.getString("name");
                password = userJson.getString("password");
                isAuth = userJson.getInt("isAuth");
                Log.d("wwwww",name);
                user = new User(id,login,name,password,isAuth);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return user;
    }
    public void enter(String login){
        open();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_ISAUTH,1);
        database.update(DatabaseHelper.TABLE_USERS,cv,DatabaseHelper.COLUMN_LOGIN+ " = ?", new String[] { login });
        close();
        JSONObject json = new JSONObject();
        try{
            json.put("p_login",login);
        }catch(JSONException e){
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    response = httpHandler.post("http://10.0.2.2:5559/enter",json.toString());

                    synchronized (lock){
                        lock.notify();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        synchronized (lock){
            try{
                lock.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public void exit(String login){
        JSONObject json = new JSONObject();
        try{
            json.put("p_login",login);
        }catch(JSONException e){
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    response = httpHandler.post("http://10.0.2.2:5559/exit0",json.toString());

                    synchronized (lock){
                        lock.notify();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        synchronized (lock){
            try{
                lock.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public User getCorrectPassword(){
        User user = null;
        open();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.TABLE_USERS+" WHERE "+DatabaseHelper.COLUMN_ISAUTH+" = ? ",new String[] { "1" });
        if(cursor.moveToFirst()){
            Integer id = cursor.getInt(0);
            String Login  = cursor.getString(1);
            String Name = cursor.getString(2);
            String Password = cursor.getString(3);
            Integer isAuth = cursor.getInt(4);
            user = new User(id,Login,Name,Password,isAuth);
        }
        close();
        return user;
    }
}
