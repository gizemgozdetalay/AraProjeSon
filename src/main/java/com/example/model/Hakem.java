package com.example.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name= "Hakem")
public class Hakem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long hakemId;
	
	//@JsonIgnore
	//@ManyToOne
	//private Kullanici kullanici;
	
	/*@JsonIgnore
	@ManyToMany
	private Konferans hakemKon;

	public Konferans getHakemKon() {
		return hakemKon;
	}

	public void setHakemKon(Konferans hakemKon) {
		this.hakemKon = hakemKon;
	}*/

	public long getHakemId() {
		return hakemId;
	}

	public void setHakemId(long hakemId) {
		this.hakemId = hakemId;
	}

	/*public Kullanici getKullanici() {
		return kullanici;
	}

	public void setKullanici(Kullanici kullanici) {
		this.kullanici = kullanici;
	}

	public Hakem(Kullanici kullanici) {
		super();
		this.kullanici = kullanici;
	}*/
	
	@JsonIgnore
	@ManyToOne
	private Kullanici hakemler;
	
	@JsonIgnore
	@ManyToOne
	private Konferans hakemKonferanslar;
	
	@OneToMany(mappedBy="hakem")
	@Fetch(FetchMode.JOIN)
	private List<HakemAnahtarKelime> hakemAnahtarKelime = new ArrayList<>();
	
	@OneToMany(mappedBy="eslemeHakem")
	@Fetch(FetchMode.JOIN)
	private List<Esleme> hakemEslemeler = new ArrayList<>();

	
	public List<HakemAnahtarKelime> getHakemAnahtarKelime() {
		return hakemAnahtarKelime;
	}

	public void setHakemAnahtarKelime(List<HakemAnahtarKelime> hakemAnahtarKelime) {
		this.hakemAnahtarKelime = hakemAnahtarKelime;
	}

	public Konferans getHakemKonferanslar() {
		return hakemKonferanslar;
	}

	public void setHakemKonferanslar(Konferans hakemKonferanslar) {
		this.hakemKonferanslar = hakemKonferanslar;
	}

	public Kullanici getHakemler() {
		return hakemler;
	}

	public void setHakemler(Kullanici hakemler) {
		this.hakemler = hakemler;
	}

	public Hakem()
	{
		
	}
	
	

}
