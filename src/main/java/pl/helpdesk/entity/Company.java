package pl.helpdesk.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "firmy")
public class Company implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "Id_Firma", columnDefinition = "INTEGER(5) NOT NULL")
	private int id;

	@Column(name = "Nazwa", columnDefinition = "VARCHAR(100) NOT NULL")
	private String nazwa;

	@Column(name = "Ulica", columnDefinition = "VARCHAR(30) NOT NULL")
	private String ulica;

	@Column(name = "Numer", columnDefinition = "VARCHAR(11) NOT NULL")
	private String numer;

	@Column(name = "Kod_pocztowy", columnDefinition = "CHAR(5) NOT NULL")
	private String kod_pocztowy;

	@Column(name = "Miejscowosc", columnDefinition = "VARCHAR(30) NOT NULL")
	private String miejscowosc;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getUlica() {
		return ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public String getNumer() {
		return numer;
	}

	public void setNumer(String numer) {
		this.numer = numer;
	}

	public String getKod_pocztowy() {
		return kod_pocztowy;
	}

	public void setKod_pocztowy(String kod_pocztowy) {
		this.kod_pocztowy = kod_pocztowy;
	}

	public String getMiejscowosc() {
		return miejscowosc;
	}

	public void setMiejscowosc(String miejscowosc) {
		this.miejscowosc = miejscowosc;
	}
	
	@Override
	public String toString() {
		
		return this.nazwa;
	}

}
