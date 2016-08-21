package pl.helpdesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "typy_zgloszen")
public class Type {
	
	
	@Id
	@GeneratedValue
	@Column(name = "Id_Typ", columnDefinition="INTEGER(2) NOT NULL")
	private int id;
	

	@Column(name = "Nazwa", unique=true, columnDefinition="VARCHAR(20) NOT NULL")
	private String nazwa;


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

	
}
