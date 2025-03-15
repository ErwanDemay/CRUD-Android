package com.example.gestionvisiteurs.modele;

import java.util.Date;

public class Visiteur {
private String identifiant;
private String nom;
private String prenom;
private String login;
private String motDePasse;
private String adresse;
private String codePostal;
private String ville;
private String dateEmbauche;

    public Visiteur(String identifiant, String nom, String prenom, String login, String adresse, String motDePasse, String codePostal, String ville, String dateEmbauche) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.adresse = adresse;
        this.motDePasse = motDePasse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.dateEmbauche = dateEmbauche;
    }

    public Visiteur(String identifiant, String nom, String prenom) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(String dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    @Override
    public String toString() {
        return identifiant + " - " + nom + " " + prenom;  // Affiche uniquement l'ID, le nom et le prénom
    }

}
