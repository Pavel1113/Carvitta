package pavelbich.fit.bstu.carvitta;

import java.io.Serializable;
import java.io.StringBufferInputStream;

public class Car implements Serializable {
    public Integer id;
    public Integer usid;
    public String marka;
    public String model;
    public String price;
    public String year;
    public String mehAuto;
    public String volume;
    public String typeFuel;
    public String shape;
    public String distance;
    public String image;
    public String image2;
    public String phone;
    public Integer isSell;

    public Car(Integer Id, Integer Usid, String Marka,String Model,String Price, String Year, String MehAuto, String Volume, String TypeFuel, String Shape, String Distance,String Image,String Image2, String Phone, Integer IsSell){
        this.id = Id;
        this.usid = Usid;
        this.marka = Marka;
        this.model = Model;
        this.price = Price;
        this.image = Image;
        this.image2 = Image2;
        this.year = Year;
        this.mehAuto = MehAuto;
        this.volume = Volume;
        this.typeFuel = TypeFuel;
        this.shape = Shape;
        this.distance = Distance;
        this.phone = Phone;
        this.isSell = IsSell;
    }

    public Integer getSell() {
        return isSell;
    }

    public void setSell(Integer sell) {
        isSell = sell;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsid() {
        return usid;
    }

    public void setUsid(Integer usid) {
        this.usid = usid;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMehAuto() {
        return mehAuto;
    }

    public void setMehAuto(String mehAuto) {
        this.mehAuto = mehAuto;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getTypeFuel() {
        return typeFuel;
    }

    public void setTypeFuel(String typeFuel) {
        this.typeFuel = typeFuel;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }
}
