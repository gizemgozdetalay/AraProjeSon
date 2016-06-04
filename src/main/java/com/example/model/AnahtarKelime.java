package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="AnahtarKelime")
public class AnahtarKelime {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "anahtarKelimeId", unique = true, nullable = false)
	private long anahtarKelimeId;
	
	private String anahtarKelimeDeger;
	
	public String getAnahtarKelimeDeger() {
		return anahtarKelimeDeger;
	}

	public void setAnahtarKelimeDeger(String anahtarKelimeDeger) {
		this.anahtarKelimeDeger = anahtarKelimeDeger;
	}

	@JsonIgnore
	@ManyToOne
	private Konferans konferansAnahtarKelime;

	public long getAnahtarKelimeId() {
		return anahtarKelimeId;
	}

	public void setAnahtarKelimeId(long anahtarKelimeId) {
		this.anahtarKelimeId = anahtarKelimeId;
	}

	public Konferans getKonferansAnahtarKelime() {
		return konferansAnahtarKelime;
	}

	public void setKonferansAnahtarKelime(Konferans konferans_anahtar_kelime) {
		this.konferansAnahtarKelime = konferans_anahtar_kelime;
	}

	public AnahtarKelime( String anahtarKelimeDeger, Konferans konferansAnahtarKelime) {
		super();
		//this.anahtar_kelime_id = anahtar_kelime_id;
		this.anahtarKelimeDeger = anahtarKelimeDeger;
		this.konferansAnahtarKelime = konferansAnahtarKelime;
	}

	public AnahtarKelime() {
		super();
	}
	
	
	


}
