package android.example.inventoryapp.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "inventory_table")
public class Inventory {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String model;
    private int price;
    private int quantity;
    private String supplier;
    private String img;

    public Inventory(String title, String model, int price, int quantity, String supplier, String img) {
        this.title = title;
        this.model = model;
        this.price = price;
        this.quantity = quantity;
        this.supplier = supplier;
        this.img = img;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getModel() {
        return model;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getImg() {
        return img;
    }


}
