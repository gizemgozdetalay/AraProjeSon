package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class MakaleAnahtarKelime {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long makaleAnahtarKelimeId;
	
	private long anahtarKelimeId;
	
	@JsonIgnore
	@ManyToOne
	private Makale makale;

	

	public long getMakaleAnahtarKelimeId() {
		return makaleAnahtarKelimeId;
	}

	public void setMakaleAnahtarKelimeId(long makaleAnahtarKelimeId) {
		this.makaleAnahtarKelimeId = makaleAnahtarKelimeId;
	}

	public long getAnahtarKelimeId() {
		return anahtarKelimeId;
	}

	public void setAnahtarKelimeId(long anahtarKelimeId) {
		this.anahtarKelimeId = anahtarKelimeId;
	}

	public Makale getMakale() {
		return makale;
	}

	public void setMakale(Makale makale) {
		this.makale = makale;
	}

	
	
}
