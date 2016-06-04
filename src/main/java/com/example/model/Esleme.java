package com.example.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name= "Esleme")
public class Esleme {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long eslemeId;
			
	
	@JsonIgnore
	@ManyToOne
	private Makale eslemeMakale;
	
	@JsonIgnore
	@ManyToOne
	private Hakem eslemeHakem;
	
	 @OneToOne(cascade = CascadeType.ALL)
	 @JoinColumn(name="degerlendirmeId")
	 private Degerlendirme degerlendirme;
	
	

	public Degerlendirme getDegerlendirme() {
		return degerlendirme;
	}



	public void setDegerlendirme(Degerlendirme degerlendirme) {
		this.degerlendirme = degerlendirme;
	}



	public Esleme(long eslemeId, Makale eslemeMakale, Hakem eslemeHakem) {
		super();
		this.eslemeId = eslemeId;
		this.eslemeMakale = eslemeMakale;
		this.eslemeHakem = eslemeHakem;
	}
	
	

	public Esleme() {
		super();
	}



	public long getEslemeId() {
		return eslemeId;
	}

	public void setEslemeId(long eslemeId) {
		this.eslemeId = eslemeId;
	}

	public Makale getEslemeMakale() {
		return eslemeMakale;
	}

	public void setEslemeMakale(Makale eslemeMakale) {
		this.eslemeMakale = eslemeMakale;
	}

	public Hakem getEslemeHakem() {
		return eslemeHakem;
	}

	public void setEslemeHakem(Hakem eslemeHakem) {
		this.eslemeHakem = eslemeHakem;
	}
	
	
	

}
