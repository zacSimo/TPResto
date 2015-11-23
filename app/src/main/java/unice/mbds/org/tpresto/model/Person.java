package unice.mbds.org.tpresto.model;

/**
 * Created by Zac on 27/10/2015.
 */
public class Person {
    private String nom;
    private String prenom;
    private String sexe;
    private String telephone;
    private String email;
    private String password;
    private String createdBy;
    private String pays;

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    private boolean status;

    public Person() {
    }

    public Person( String sexe, String email, String password,
                   String createdBy, String prenom, String nom, String telephone) {
        this.sexe = sexe;
        this.email = email;
        this.password = password;
        this.createdBy = createdBy;
        this.prenom = prenom;
        this.nom = nom;
        this.telephone = telephone;

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
