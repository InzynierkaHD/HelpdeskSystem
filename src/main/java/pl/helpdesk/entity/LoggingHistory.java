package pl.helpdesk.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Historia_logowania")
public class LoggingHistory {

	
	@Id
	@GeneratedValue
	@Column(name = "Id_Hist_Logowania", columnDefinition="INTEGER(5) NOT NULL")
	private int id;
	
	@OneToOne
	@JoinColumn(name="Id_uzytkownika", columnDefinition="INTEGER(11) NOT NULL")
	private User userDataModel;
	
	@Column(name="Data_logowania", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataLogowania;
	
	@Column(name="Data_wylogowania")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataWylogowania;

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

	public Date getDataLogowania() {
		return dataLogowania;
	}

	public void setDataLogowania(Date dataLogowania) {
		this.dataLogowania = dataLogowania;
	}

	public Date getDataWylogowania() {
		return dataWylogowania;
	}

	public void setDataWylogowania(Date dataWylogowania) {
		this.dataWylogowania = dataWylogowania;
	}
	
	
}
