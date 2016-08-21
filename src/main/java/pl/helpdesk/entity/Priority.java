package pl.helpdesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "priorytety")
public class Priority {

	
	@Id
	@GeneratedValue
	@Column(name = "Id_Priorytet", columnDefinition="INTEGER(2) NOT NULL")
	private int id;
	
	@Column(name = "Nazwa", columnDefinition="VARCHAR(15) NOT NULL", unique=true)
	private String nazwa;
	
	@Column(name = "Stopien_waznosci", columnDefinition="INTEGER(2) NOT NULL")
	private String stopienWaznosci;

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

	public String getStopienWaznosci() {
		return stopienWaznosci;
	}

	public void setStopienWaznosci(String stopienWaznosci) {
		this.stopienWaznosci = stopienWaznosci;
	}
	
	
}
