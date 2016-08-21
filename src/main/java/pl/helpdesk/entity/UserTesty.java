package pl.helpdesk.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="uzytkownicyTest")

public class UserTesty implements Serializable{

	private static final long serialVersionUID = 4166445420618861774L;

	@Id
	@GeneratedValue
	@Column(name="Id_Uzytkownika")
	private int id;
	
	@Column(name="Login", unique=true ,columnDefinition="VARCHAR(20) NOT NULL")
	private String login;
	
	@Column(name="Haslo",columnDefinition="VARCHAR(90) NOT NULL")
	private String haslo;
	
	@Column(name="Imie", columnDefinition="VARCHAR(20) NOT NULL")
	private String imie;
	
	@Column(name="Nazwisko", columnDefinition="VARCHAR(30) NOT NULL")
	private String nazwisko;
	
	@Column(name="Email", unique=true ,columnDefinition="VARCHAR(45) NOT NULL")
	private String email;
	
	
	@Column(name="Pensja")
	private double pensja;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getHaslo() {
		return haslo;
	}


	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}


	public String getImie() {
		return imie;
	}


	public void setImie(String imie) {
		this.imie = imie;
	}


	public String getNazwisko() {
		return nazwisko;
	}


	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public double getPensja() {
		return pensja;
	}


	public void setPensja(double pensja) {
		this.pensja = pensja;
	}
	

	



}
