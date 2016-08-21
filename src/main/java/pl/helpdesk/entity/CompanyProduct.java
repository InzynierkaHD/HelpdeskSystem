package pl.helpdesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "firma_produkt")
public class CompanyProduct {

	@Id
	@GeneratedValue
	@Column(name = "Id_Firma_Produkt", columnDefinition="INTEGER(6) NOT NULL")
	private int id;
	
	@OneToOne
	@JoinColumn(name = "Id_firmy", columnDefinition="INTEGER(5) NOT NULL")
	private Company companyDataModel;
	
	@OneToOne
	@JoinColumn(name = "Id_produktu", columnDefinition="INTEGER(5) NOT NULL")
	private Product productDataModel;
	
	@Column(name = "Czy_pomoc_aktywna")
	private boolean czyAktywna;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Company getCompanyDataModel() {
		return companyDataModel;
	}

	public void setCompanyDataModel(Company companyDataModel) {
		this.companyDataModel = companyDataModel;
	}

	public Product getProductDataModel() {
		return productDataModel;
	}

	public void setProductDataModel(Product productDataModel) {
		this.productDataModel = productDataModel;
	}

	public boolean isCzyAktywna() {
		return czyAktywna;
	}

	public void setCzyAktywna(boolean czyAktywna) {
		this.czyAktywna = czyAktywna;
	}
	
	
	
	
}
