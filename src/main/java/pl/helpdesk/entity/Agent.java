package pl.helpdesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "przedstawiciele")
public class Agent {

	@Id
	@GeneratedValue
	@Column(name = "Id_Pzedstawiciel",columnDefinition="INTEGER(5) NOT NULL")
	private int id;

	@OneToOne
	@JoinColumn(name = "Id_uzytkownika", nullable=false)
	private User userDataModel;

	@OneToOne
	@JoinColumn(name = "Id_firmy", columnDefinition="INTEGER(5) NOT NULL")
	private Company companyDataModel;
	
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

	public Company getCompanyDataModel() {
		return companyDataModel;
	}

	public void setCompanyDataModel(Company companyDataModel) {
		this.companyDataModel = companyDataModel;
	}

	
	
}
