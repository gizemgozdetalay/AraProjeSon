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
@Table(name="Yazar")
public class Yazar {

	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long yazarId;
	/*public Yazar(Kullanici yazar) {
		super();
		this.yazar = yazar;
	}*/
	
	public Yazar()
	{
		
	}

	public long getYazarId() {
		return yazarId;
	}

	public void setYazarId(long yazarId) {
		this.yazarId = yazarId;
	}

	/*public Kullanici getYazar() {
		return yazar;
	}

	public void setYazar(Kullanici yazar) {
		this.yazar = yazar;
	}*/

	@JsonIgnore
	@ManyToOne
	private Kullanici yazarlar;
	
	@JsonIgnore
	@ManyToOne
	private Konferans yazarKonferanslar;
	
	
	
	
	@OneToMany(mappedBy="yazarMakale")
	@Fetch(FetchMode.JOIN)
	private List<Makale> yazarMakale = new ArrayList<>();

	public Konferans getYazarKonferanslar() {
		return yazarKonferanslar;
	}

	public void setYazarKonferanslar(Konferans yazarKonferanslar) {
		this.yazarKonferanslar = yazarKonferanslar;
	}

	public Kullanici getYazarlar() {
		return yazarlar;
	}

	public void setYazarlar(Kullanici yazarlar) {
		this.yazarlar = yazarlar;
	}
	
	
	//@JsonIgnore
	//@ManyToOne
	//private Kullanici yazar;
	
/*	@JsonIgnore
	@ManyToMany
	private Konferans yazarKon;

	public Konferans getYazarKon() {
		return yazarKon;
	}

	public void setYazarKon(Konferans yazarKon) {
		this.yazarKon = yazarKon;
	}*/
}
