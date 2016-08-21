package pl.helpdesk.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pracownicy")
public class Employee implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "Id_Pracownik", columnDefinition = "INTEGER(4) NOT NULL")
	private int id;

	@OneToOne
	@JoinColumn(name = "Id_uzytkownika", nullable = false)
	private User userDataModel;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUserDataModel() {
		return userDataModel;
	}

	public void setUserDataModel(User userDataModel) {
		this.userDataModel = userDataModel;
	}

}
