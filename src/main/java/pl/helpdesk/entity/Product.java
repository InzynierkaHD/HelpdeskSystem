package pl.helpdesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "produkty")
public class Product {
	
	@Id
	@GeneratedValue
	@Column(name = "Id_Produkt", columnDefinition="INTEGER(5) NOT NULL")
	private int id;
	
	@Column(name = "Nazwa", columnDefinition="VARCHAR(30) NOT NULL", unique=true)
	private String nazwa;
	
	@Column(name = "Cena", columnDefinition="Decimal(10,2) NOT NULL")
	private double cena;
	
	@Column(name = "Opis", columnDefinition="TEXT NOT NULL")
	private String opis;

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

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	
}
