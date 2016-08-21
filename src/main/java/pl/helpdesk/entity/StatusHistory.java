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
@Table(name = "historia_statusow")
public class StatusHistory {

	@Id
	@GeneratedValue
	@Column(name = "Id_Hist_Statusu", columnDefinition="INTEGER(11) NOT NULL")
	private int id;
	
	@OneToOne
	@JoinColumn(name="Id_zgloszenia", columnDefinition="INTEGER(8) NOT NULL")
	private Problem problemDataModel;
	
	@OneToOne
	@JoinColumn(name="Id_pracownika", columnDefinition="INTEGER(5) NOT NULL")
	private Employee employeeDataModel;
	
	@OneToOne
	@JoinColumn(name="Id_statusu", columnDefinition="INTEGER(2) NOT NULL")
	private Status statusDataModel;
	
	@Column(name="Data", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Problem getProblemDataModel() {
		return problemDataModel;
	}

	public void setProblemDataModel(Problem problemDataModel) {
		this.problemDataModel = problemDataModel;
	}

	public Employee getEmployeeDataModel() {
		return employeeDataModel;
	}

	public void setEmployeeDataModel(Employee employeeDataModel) {
		this.employeeDataModel = employeeDataModel;
	}

	public Status getStatusDataModel() {
		return statusDataModel;
	}

	public void setStatusDataModel(Status statusDataModel) {
		this.statusDataModel = statusDataModel;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	
}
