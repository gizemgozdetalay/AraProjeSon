package com.example.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name= "Makale")
public class Makale {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long makaleId;
	
	private String makaleOnay="false";
	
	public String getMakaleOnay() {
		return makaleOnay;
	}

	public void setMakaleOnay(String makaleOnay) {
		this.makaleOnay = makaleOnay;
	}

	private String makaleIsmi;
	
	public List<Esleme> getMakaleEslemeler() {
		return makaleEslemeler;
	}

	public void setMakaleEslemeler(List<Esleme> makaleEslemeler) {
		this.makaleEslemeler = makaleEslemeler;
	}

	public String getMakaleIsmi() {
		return makaleIsmi;
	}

	public void setMakaleIsmi(String makaleIsmi) {
		this.makaleIsmi = makaleIsmi;
	}

	private String makaleUzanti;
	
	public String getMakaleUzanti() {
		return makaleUzanti;
	}

	public void setMakaleUzanti(String makaleUzanti) {
		this.makaleUzanti = makaleUzanti;
	}

	@OneToMany(mappedBy="makale")
	@Fetch(FetchMode.JOIN)
	private List<MakaleAnahtarKelime> makaleAnahtarKelime = new ArrayList<>();
	
	@OneToMany(mappedBy= "eslemeMakale")
	@Fetch(FetchMode.JOIN)
	private List<Esleme> makaleEslemeler = new ArrayList<>();
	
	public List<MakaleAnahtarKelime> getMakaleAnahtarKelime() {
		return makaleAnahtarKelime;
	}

	public void setMakaleAnahtarKelime(List<MakaleAnahtarKelime> makaleAnahtarKelime) {
		this.makaleAnahtarKelime = makaleAnahtarKelime;
	}

	@JsonIgnore
	@ManyToOne
	private Konferans konferansMakale;
	
	@JsonIgnore
	@ManyToOne
	private Yazar yazarMakale;

	public Yazar getYazarMakale() {
		return yazarMakale;
	}

	public void setYazarMakale(Yazar yazarMakale) {
		this.yazarMakale = yazarMakale;
	}

	public long getMakaleId() {
		return makaleId;
	}

	public void setMakaleId(long makaleId) {
		this.makaleId = makaleId;
	}

	public Konferans getKonferansMakale() {
		return konferansMakale;
	}

	public void setKonferansMakale(Konferans konferansMakale) {
		this.konferansMakale = konferansMakale;
	}
	
	

}
