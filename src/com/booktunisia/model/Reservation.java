package com.booktunisia.model;

import java.io.Serializable;

public class Reservation implements Serializable {

	private int id;
	private int id_hotel;

	private int nbre_single;
	private int nbre_double;
	private int nbre_triple;
	private int nbre_nuits;
	private String remarque;
	private String datedeb;

	private String nom_client;
	private String prenom_client;
	private int cin_client;
	private String email_client;
	private int tel_client;

	private double prix_total;

	public Reservation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Reservation(int id_hotel) {
		super();
		this.id_hotel = id_hotel;
	}

	public Reservation(int id_hotel, int nbre_single, int nbre_double,
			int nbre_triple, int nbre_nuits, String remarque, String datedeb,
			double prix, String nom_client, String prenom_client,
			int cin_client, String email_client, int tel_client) {
		super();
		this.id_hotel = id_hotel;
		this.nbre_single = nbre_single;
		this.nbre_double = nbre_double;
		this.nbre_triple = nbre_triple;
		this.nbre_nuits = nbre_nuits;
		this.remarque = remarque;
		this.datedeb = datedeb;
		this.prix_total = prix;
		this.nom_client = nom_client;
		this.prenom_client = prenom_client;
		this.cin_client = cin_client;
		this.email_client = email_client;
		this.tel_client = tel_client;
	}

	public Reservation(int id_hotel, int nbre_single, int nbre_double,
			int nbre_triple, int nbre_nuits, String remarque, String datedeb,
			double prix) {
		super();
		this.id_hotel = id_hotel;
		this.nbre_single = nbre_single;
		this.nbre_double = nbre_double;
		this.nbre_triple = nbre_triple;
		this.nbre_nuits = nbre_nuits;
		this.remarque = remarque;
		this.prix_total = prix;
		this.datedeb = datedeb;
	}

	public String getDatedeb() {
		return datedeb;
	}

	public void setDatedeb(String datedeb) {
		this.datedeb = datedeb;
	}

	public double getPrix_total() {
		return prix_total;
	}

	public void setPrix_total(double prix_total) {
		this.prix_total = prix_total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_hotel() {
		return id_hotel;
	}

	public void setId_hotel(int id_hotel) {
		this.id_hotel = id_hotel;
	}

	public int getNbre_single() {
		return nbre_single;
	}

	public void setNbre_single(int nbre_single) {
		this.nbre_single = nbre_single;
	}

	public int getNbre_double() {
		return nbre_double;
	}

	public void setNbre_double(int nbre_double) {
		this.nbre_double = nbre_double;
	}

	public int getNbre_triple() {
		return nbre_triple;
	}

	public void setNbre_triple(int nbre_triple) {
		this.nbre_triple = nbre_triple;
	}

	public int getNbre_nuits() {
		return nbre_nuits;
	}

	public void setNbre_nuits(int nbre_nuits) {
		this.nbre_nuits = nbre_nuits;
	}

	public String getRemarque() {
		return remarque;
	}

	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}

	public String getNom_client() {
		return nom_client;
	}

	public void setNom_client(String nom_client) {
		this.nom_client = nom_client;
	}

	public String getPrenom_client() {
		return prenom_client;
	}

	public void setPrenom_client(String prenom_client) {
		this.prenom_client = prenom_client;
	}

	public int getCin_client() {
		return cin_client;
	}

	public void setCin_client(int cin_client) {
		this.cin_client = cin_client;
	}

	public String getEmail_client() {
		return email_client;
	}

	public void setEmail_client(String email_client) {
		this.email_client = email_client;
	}

	public int getTel_client() {
		return tel_client;
	}

	public void setTel_client(int tel_client) {
		this.tel_client = tel_client;
	}

	public String toString() {
		return "Réservation Hôtel" + "\n\nInformation de la réservation"
				+ "\nId Hotel: "
				+ id_hotel
				+ "\nNbre ch single: "
				+ nbre_single
				+ "\nNbre ch double: "
				+ nbre_double
				+ "\nNbre ch triple: "
				+ nbre_triple
				+ "\nNbre nuits: "
				+ nbre_nuits
				+ "\nRemarque: "
				+ remarque
				+ "\nDate Début: "
				+ datedeb
				+ "\n\nInformation sur le client"
				+ "\nNom et prénom: "
				+ nom_client
				+ " "
				+ prenom_client
				+ "\nCIN: "
				+ cin_client
				+ "\nEmail: "
				+ email_client
				+ "\nTéléphone: "
				+ tel_client
				+ "\n\nPrix Total" + "\n" + prix_total;
	}

}
