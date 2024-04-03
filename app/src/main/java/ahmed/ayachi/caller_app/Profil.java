package ahmed.ayachi.caller_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Profil{
    String nom,prenom,numero;
    public Profil(String nom, String prenom, String numero) {
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
    }

    public String getNom(){
        return nom;
    }


    public Profil setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public Profil setPrenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public Profil setNumero(String numero) {
        this.numero = numero;
        return this;
    }



    @Override
    public String toString() {
        return "Profil{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", numero='" + numero + '\'' +
                '}';
    }
}