package unice.mbds.org.tpresto.model;

/**
 * Created by Zac on 06/01/2016.
 */
public class Commande{
    private String id;

    public Commande(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}