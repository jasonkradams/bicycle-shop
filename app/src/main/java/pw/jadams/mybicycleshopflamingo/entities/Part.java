package pw.jadams.mybicycleshopflamingo.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "parts")
public class Part {
    @PrimaryKey(autoGenerate = true)
    private int partId;
    private String partName;
    private double price;
    private int productId;

    public Part(int partId, String partName, double price, int productId) {
        this.partId = partId;
        this.partName = partName;
        this.price = price;
        this.productId = productId;
    }

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
