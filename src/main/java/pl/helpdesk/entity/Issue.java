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
@Table(name = "zgloszenia")
public class Issue implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "Id_Zgloszenie", columnDefinition = "INTEGER(8) NOT NULL")
	private int id;

	@OneToOne
	@JoinColumn(name = "Id_user", columnDefinition = "INTEGER(6) NOT NULL")
	private User user;

	@OneToOne
	@JoinColumn(name = "Id_typu", columnDefinition = "INTEGER(2) NOT NULL")
	private IssueType typeDataModel;

	@Column(name = "Temat", columnDefinition = "VARCHAR(50) NOT NULL")
	private String temat;

	@OneToOne
	@JoinColumn(name = "Id_priorytetu", columnDefinition = "INTEGER(2) NOT NULL")
	private Priority prioritoryDataModel;

	@OneToOne
	@JoinColumn(name = "Id_firma_produkt", columnDefinition = "INTEGER(6) NOT NULL")
	private CompanyProduct companyProductDataModel;

	@Column(name = "Data_dodania", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDodania;

	@Column(name = "Data_zakonczenia")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataZakonczenia;

	@OneToOne
	@JoinColumn(name = "Id_wlasciciela", columnDefinition = "INTEGER(5)")
	private Employee employeeDataModel;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setClientDataModel(User user) {
		this.user = user;
	}

	public IssueType getTypeDataModel() {
		return typeDataModel;
	}

	public void setTypeDataModel(IssueType typeDataModel) {
		this.typeDataModel = typeDataModel;
	}

	public String getTemat() {
		return temat;
	}

	public void setTemat(String temat) {
		this.temat = temat;
	}

	public Priority getPrioritoryDataModel() {
		return prioritoryDataModel;
	}

	public void setPrioritoryDataModel(Priority prioritoryDataModel) {
		this.prioritoryDataModel = prioritoryDataModel;
	}

	public CompanyProduct getCompanyProductDataModel() {
		return companyProductDataModel;
	}

	public void setCompanyProductDataModel(CompanyProduct companyProductDataModel) {
		this.companyProductDataModel = companyProductDataModel;
	}

	public Date getDataDodania() {
		return dataDodania;
	}

	public void setDataDodania(Date dataDodania) {
		this.dataDodania = dataDodania;
	}

	public Date getDataZakonczenia() {
		return dataZakonczenia;
	}

	public void setDataZakonczenia(Date dataZakonczenia) {
		this.dataZakonczenia = dataZakonczenia;
	}

	public Employee getEmployeeDataModel() {
		return employeeDataModel;
	}

	public void setEmployeeDataModel(Employee employeeDataModel) {
		this.employeeDataModel = employeeDataModel;
	}

}
