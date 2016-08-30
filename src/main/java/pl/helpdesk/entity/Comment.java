package pl.helpdesk.entity;

import java.io.Serializable;
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
@Table(name = "komentarze")
public class Comment implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "Id_Komentarz", columnDefinition = "INTEGER(11) NOT NULL")
	private int id;

	@OneToOne
	@JoinColumn(name = "Id_uzytkownika", columnDefinition = "INTEGER(11) NOT NULL")
	private User userDataModel;

	@OneToOne
	@JoinColumn(name = "Id_zgloszenia", columnDefinition = "INTEGER(8) NOT NULL")
	private Issue problemDataModel;

	@Column(name = "Tresc", columnDefinition = "TEXT NOT NULL")
	private String tresc;

	@Column(name = "Data_dodania", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDodania;

	@Column(name = "Czy_wewnetrzny")
	private boolean czyWewnetrzny;

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

	public Issue getProblemDataModel() {
		return problemDataModel;
	}

	public void setProblemDataModel(Issue problemDataModel) {
		this.problemDataModel = problemDataModel;
	}

	public String getTresc() {
		return tresc;
	}

	public void setTresc(String tresc) {
		this.tresc = tresc;
	}

	public Date getDataDodania() {
		return dataDodania;
	}

	public void setDataDodania(Date dataDodania) {
		this.dataDodania = dataDodania;
	}

	public boolean isCzyWewnetrzny() {
		return czyWewnetrzny;
	}

	public void setCzyWewnetrzny(boolean czyWewnetrzny) {
		this.czyWewnetrzny = czyWewnetrzny;
	}

}
