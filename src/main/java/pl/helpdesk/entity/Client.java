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
@Table(name = "klienci")
public class Client implements Serializable{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue
		@Column(name = "Id_Klient",columnDefinition="INTEGER(6) NOT NULL")
		private int id;

		@OneToOne
		@JoinColumn(name = "Id_uzytkownika", nullable=false)
		private User userDataModel;

		@OneToOne
		@JoinColumn(name = "Id_firmy", columnDefinition="INTEGER(5)")
		private Company companyDataModel;
		
		@OneToOne
		@JoinColumn(name = "Id_przedstawiciela", columnDefinition="INTEGER(5) NOT NULL")
		private Agent agentDataModel;

		public Client(){
			super();
		}
		
		public Client(User userDataModel, Company companyDataModel, Agent agentDataModel) {
			super();
			this.userDataModel = userDataModel;
			this.companyDataModel = companyDataModel;
			this.agentDataModel = agentDataModel;
		}

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

		public Agent getAgentDataModel() {
			return agentDataModel;
		}

		public void setAgentDataModel(Agent agentDataModel) {
			this.agentDataModel = agentDataModel;
		}
		
		
}
