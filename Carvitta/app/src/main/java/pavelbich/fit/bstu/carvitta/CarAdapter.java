package pavelbich.fit.bstu.carvitta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;

public class CarAdapter extends ArrayAdapter<Car> {
    private Context mContext;
    private int mResource;

    public CarAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Car> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource,parent,false);

        ImageView img = convertView.findViewById(R.id.imgImage1);
        ImageView img2 = convertView.findViewById(R.id.imgImage2);
        TextView markaView = convertView.findViewById(R.id.markaText);
        TextView modelView = convertView.findViewById(R.id.modelText);
        TextView priceView = convertView.findViewById(R.id.priceBYNText);
        TextView priceDollarView = convertView.findViewById(R.id.priceDollarText);
        TextView information = convertView.findViewById(R.id.informationText);
        TextView isSellView = convertView.findViewById(R.id.isSelling);

        String imgUri = getItem(position).getImage();
        String imgUri2 = getItem(position).getImage2();
        try {
            mContext.getContentResolver().takePersistableUriPermission(Uri.parse(imgUri),Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), Uri.parse(imgUri));
            img.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), Uri.parse(getItem(position).getImage2()));
            img2.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String year = getItem(position).getYear();
        String mehauto = getItem(position).getMehAuto();
        String volume = getItem(position).getVolume();
        String typefuel = getItem(position).getTypeFuel();
        String shape = getItem(position).getShape();
        String distance = getItem(position).getDistance();
        Integer isSell = getItem(position).getSell();

        Integer priceBYN = Integer.parseInt(getItem(position).getPrice());
        Double priceDollar = priceBYN/2.7;
        Long priceDollar1 = Math.round(priceDollar);

        if(isSell == 1){
            markaView.setText(getItem(position).getMarka());
            modelView.setText(getItem(position).getModel());
            img.setAlpha(0.5f);
            img2.setAlpha(0.5f);
            markaView.setAlpha(0.5f);
            modelView.setAlpha(0.5f);
            priceView.setAlpha(0.5f);
            priceDollarView.setAlpha(0.5f);
            information.setAlpha(0.5f);
            isSellView.setVisibility(View.VISIBLE);

        }else{
            isSellView.setVisibility(View.INVISIBLE);
            markaView.setText(getItem(position).getMarka());
            modelView.setText(getItem(position).getModel());
        }

        priceView.setText(getItem(position).getPrice()+" BYN");
        priceDollarView.setText("= "+priceDollar1.toString()+"$");
        information.setText(year+" г., "+mehauto+", "+volume+" л, "+typefuel+", "+shape+", "+distance+" км");
        return convertView;
    }
    public void photo(){

    }

}
