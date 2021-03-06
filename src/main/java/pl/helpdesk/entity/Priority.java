package pl.helpdesk.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "priorytety")
public class Priority implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "Id_Priorytet", columnDefinition = "INTEGER(2) NOT NULL")
	private int id;

	@Column(name = "Nazwa", columnDefinition = "VARCHAR(15) NOT NULL", unique = true)
	private String nazwa;

	@Column(name = "Stopien_waznosci", columnDefinition = "INTEGER(2) NOT NULL")
	private int stopienWaznosci;

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

	public int getStopienWaznosci() {
		return stopienWaznosci;
	}

	public void setStopienWaznosci(int stopienWaznosci) {
		this.stopienWaznosci = stopienWaznosci;
	}
	
	@Override
	public String toString() {
		return this.nazwa;
	}

}
