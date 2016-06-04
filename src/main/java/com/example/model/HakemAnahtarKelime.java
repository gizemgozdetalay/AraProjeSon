package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class HakemAnahtarKelime {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long hakemAnahtarKelimeId;
	
	private long anahtarKelimeId;
	

	public long getAnahtarKelimeId() {
		return anahtarKelimeId;
	}

	public void setAnahtarKelimeId(long anahtarKelimeId) {
		this.anahtarKelimeId = anahtarKelimeId;
	}

	@JsonIgnore
	@ManyToOne
	private Hakem hakem;

	public long getHakemAnahtarKelimeId() {
		return hakemAnahtarKelimeId;
	}

	public void setHakemAnahtarKelimeId(long hakemAnahtarKelimeId) {
		this.hakemAnahtarKelimeId = hakemAnahtarKelimeId;
	}

	public Hakem getHakem() {
		return hakem;
	}

	public void setHakem(Hakem hakem) {
		this.hakem = hakem;
	}

	public HakemAnahtarKelime() {
		super();
	}
	
	
	
	

}
