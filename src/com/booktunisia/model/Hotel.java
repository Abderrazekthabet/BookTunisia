package com.booktunisia.model;

import java.io.Serializable;

public class Hotel implements Serializable {
	private int id;
	private String nom;
	private String ville;
	private String telephone;
	private String nbre_etoile;
	private double prix_chambre_single;
	private double prix_chambre_double;
	private double prix_chambre_triple;
	private int promo;
	private String latitude;
	private String longitude;
	private String logo;

	public Hotel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Hotel(int id, String nom, String ville, String telephone,
			String nbre_etoile, double prix_chambre_single,
			double prix_chambre_double, double prix_chambre_triple, int promo,
			String latitude, String longitude) {
		super();
		this.id = id;
		this.nom = nom;
		this.ville = ville;
		this.telephone = telephone;
		this.nbre_etoile = nbre_etoile;
		this.prix_chambre_single = prix_chambre_single;
		this.prix_chambre_double = prix_chambre_double;
		this.prix_chambre_triple = prix_chambre_triple;
		this.promo = promo;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Hotel(int id, String nom, String ville, String telephone,
			String nbre_etoile, double prix_chambre_single,
			double prix_chambre_double, double prix_chambre_triple, int promo,
			String latitude, String longitude, String logo) {
		super();
		this.id = id;
		this.nom = nom;
		this.ville = ville;
		this.telephone = telephone;
		this.nbre_etoile = nbre_etoile;
		this.prix_chambre_single = prix_chambre_single;
		this.prix_chambre_double = prix_chambre_double;
		this.prix_chambre_triple = prix_chambre_triple;
		this.promo = promo;
		this.latitude = latitude;
		this.longitude = longitude;
		this.setLogo(logo);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getNbre_etoile() {
		return nbre_etoile;
	}

	public void setNbre_etoile(String nbre_etoile) {
		this.nbre_etoile = nbre_etoile;
	}

	public double getPrix_chambre_single() {
		return prix_chambre_single;
	}

	public void setPrix_chambre_single(double prix_chambre_single) {
		this.prix_chambre_single = prix_chambre_single;
	}

	public double getPrix_chambre_double() {
		return prix_chambre_double;
	}

	public void setPrix_chambre_double(double prix_chambre_double) {
		this.prix_chambre_double = prix_chambre_double;
	}

	public double getPrix_chambre_triple() {
		return prix_chambre_triple;
	}

	public void setPrix_chambre_triple(double prix_chambre_triple) {
		this.prix_chambre_triple = prix_chambre_triple;
	}

	public int getPromo() {
		return promo;
	}

	public void setPromo(int promo) {
		this.promo = promo;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public String toString() {
		return "Information sur l'hotel"
				+ "\nNom Hotel: "
				+ nom
				+ "\nNbre étoiles: "
				+ nbre_etoile
				+ "\nVille: "
				+ ville
				+ "\nTel: "
				+ telephone
				+ "\nPrix chambre single: "
				+ prix_chambre_single+ " DT"
				+ "\nPrix chambre double: "
				+ prix_chambre_double+ " DT"
				+ "\nPrix chambre triple: "
				+ prix_chambre_triple+ " DT";
	}

}
