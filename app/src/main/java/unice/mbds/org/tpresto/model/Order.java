package unice.mbds.org.tpresto.model;

/**
 * Created by Zac on 16/12/2015.
 */
public class Order extends Product {
    private String quantite;

    public Order() {

    }

    public Order(String name, String description, String price, String calories, String type, String discount,
                 String picture, String dessert, String createdAt, String updatedAt, String id, String quantite) {
        super(name, description, price, calories, type, discount, picture, dessert, createdAt, updatedAt, id);
        this.quantite = quantite;
    }

    public String getQuantite() {
        return quantite;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }
}
