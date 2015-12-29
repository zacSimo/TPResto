package unice.mbds.org.tpresto.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zac on 27/12/2015.
 */
public class CommandeWS {
    private String price;
    private String discount;
    private Employer server;
    private Employer cooker;
    private List<Commande> items;

    public CommandeWS() {
        server = new Employer();
        cooker = new Employer();
        items = new ArrayList<>();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Employer getServer() {
        return server;
    }

    public void setServer(Employer server) {
        this.server = server;
    }

    public Employer getCooker() {
        return cooker;
    }

    public void setCooker(Employer cooker) {
        this.cooker = cooker;
    }

    public List<Commande> getItems() {
        return items;
    }

    public void setItems(List<Commande> items) {
        this.items = items;
    }

    public class Employer{
        private String id;

        public Employer() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public class Commande{
        private String id;

        public Commande() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
