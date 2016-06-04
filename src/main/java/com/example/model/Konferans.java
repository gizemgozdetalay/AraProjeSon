package com.example.model;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
//import org.hibernate.annotations.CascadeType;




@Entity
public class Konferans {
	
	private String konferansKisaltma;
	
	
	public String getKonferansKisaltma() {
		return konferansKisaltma;
	}



	public void setKonferansKisaltma(String konferansKisaltma) {
		this.konferansKisaltma = konferansKisaltma;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "konferansId", unique = true, nullable = false)
	private long konferansId;
	
	@NotNull
	private int maxMakaleSayisi;
/*	@ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinTable(name= "yonetici_konferans", joinColumns= {
			@JoinColumn(name ="konferansId",nullable=false,updatable=false)},
	inverseJoinColumns= {@JoinColumn(name ="kullanici_id",nullable=false,updatable=false) })
	//private Set<Kullanici> konferans_yoneticiler = new HashSet<Kullanici>(0);
	private List<Kullanici> konferansYoneticiler = new ArrayList<>();
	
	@ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name= "hakem_konferans", joinColumns= {
			@JoinColumn(name ="konferansId",nullable=false,updatable=false)},
	inverseJoinColumns= {@JoinColumn(name ="kullanici_id",nullable=false,updatable=false) })
	private List<Kullanici> konferans_hakemler = new ArrayList<>();
	
	@ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name= "yazar_konferans", joinColumns= {
			@JoinColumn(name ="konferansId",nullable=false,updatable=false)},
	inverseJoinColumns= {@JoinColumn(name ="kullanici_id",nullable=false,updatable=false) })
	private List<Kullanici> konferans_yazarlar = new ArrayList<>();*/
	
	
	
	
	public int getMaxMakaleSayisi() {
		return maxMakaleSayisi;
	}



	public void setMaxMakaleSayisi(int maxMakaleSayisi) {
		this.maxMakaleSayisi = maxMakaleSayisi;
	}



	public long getKonferansId() {
		return konferansId;
	}
	
	

	public void setKonferansId(long konferansId) {
		this.konferansId = konferansId;
	}


	@NotNull
	private String konferansIsmi;
	
	private String esleme = "false";
	
	public String getEsleme() {
		return esleme;
	}



	public void setEsleme(String esleme) {
		this.esleme = esleme;
	}


	@NotNull
	private Date konferansBaslamaTarihi;
	
	@NotNull
	private Date konferansBitisTarihi;
	
	@NotNull
	private String konferansMekani;
	
	@NotNull
	private String konferansWebaddress;
//	
//	@NotNull
//	private Date sonGonderimSuresi;
//
//
//	public Date getSonGonderimSuresi() {
//		return sonGonderimSuresi;
//	}
//
//
//
//	public void setSonGonderimSuresi(Date sonGonderimSuresi) {
//		this.sonGonderimSuresi = sonGonderimSuresi;
//	}



	public String getKonferansIsmi() {
		return konferansIsmi;
	}

	public void setKonferansIsmi(String konferansIsmi) {
		this.konferansIsmi = konferansIsmi;
	}

	public Date getKonferansBaslamaTarihi() {
		return konferansBaslamaTarihi;
	}

	public void setKonferansBaslamaTarihi(Date konferansBaslamaTarihi) {
		this.konferansBaslamaTarihi = konferansBaslamaTarihi;
	}

	public Date getKonferansBitisTarihi() {
		return konferansBitisTarihi;
	}

	public void setKonferansBitisTarihi(Date konferansBitisTarihi) {
		this.konferansBitisTarihi = konferansBitisTarihi;
	}

	public String getKonferansMekani() {
		return konferansMekani;
	}

	public void setKonferansMekani(String konferansMekani) {
		this.konferansMekani = konferansMekani;
	}

	public String getKonferansWebaddress() {
		return konferansWebaddress;
	}

	public void setKonferansWebaddress(String konferansWebaddress) {
		this.konferansWebaddress = konferansWebaddress;
	}
	


	
	
	
	
	
	/*public List<Kullanici> getKonferans_hakemler() {
		return konferans_hakemler;
	}

	public List<Kullanici> getKonferansYoneticiler() {
		return konferansYoneticiler;
	}

	public void setKonferansYoneticiler(List<Kullanici> konferansYoneticiler) {
		this.konferansYoneticiler = konferansYoneticiler;
	}

	public void setKonferans_hakemler(List<Kullanici> konferans_hakemler) {
		this.konferans_hakemler = konferans_hakemler;
	}

	public List<Kullanici> getKonferans_yazarlar() {
		return konferans_yazarlar;
	}

	public void setKonferans_yazarlar(List<Kullanici> konferans_yazarlar) {
		this.konferans_yazarlar = konferans_yazarlar;
	}*/

	
	
	
	
	public Konferans( String konferansIsmi, Date konferansBaslamaTarihi,
			Date konferansBitisTarihi, String konferansMekani, String konferansWebaddress, int maxMakaleSayisi, String konferansKisaltma) {
		super();
		
		this.konferansIsmi = konferansIsmi;
		this.konferansBaslamaTarihi = konferansBaslamaTarihi;
		this.konferansBitisTarihi = konferansBitisTarihi;
		this.konferansMekani = konferansMekani;
		this.konferansWebaddress = konferansWebaddress;
//		this.sonGonderimSuresi = sonGonderimSuresi;
		this.maxMakaleSayisi = maxMakaleSayisi;
		this.konferansKisaltma = konferansKisaltma;
	}
	
	


	public Konferans() {
		super();
	}


	@OneToMany(mappedBy="konferansMakale")
	@Fetch(FetchMode.JOIN)
	private Set<Makale> konferansMakale = new HashSet<>();


	public Set<Makale> getKonferansMakale() {
		return konferansMakale;
	}

	public void setKonferansMakale(Set<Makale> konferansMakale) {
		this.konferansMakale = konferansMakale;
	}
	
	@OneToMany(mappedBy="konferansAnahtarKelime")
	@Fetch(FetchMode.JOIN)
	private List<AnahtarKelime> konferansAnahtarKelime = new ArrayList<>();


	public List<AnahtarKelime> getKonferansAnahtarKelime() {
		return konferansAnahtarKelime;
	}

	public void setKonferansAnahtarKelime(List<AnahtarKelime> konferansAnahtarKelime) {
		this.konferansAnahtarKelime = konferansAnahtarKelime;
	}
	
	@OneToMany(mappedBy="yoneticiKonferanslar")
	@Fetch(FetchMode.JOIN)
	//@Cascade(CascadeType.ALL)
	private List<Yonetici> yoneticiKonferanslar = new ArrayList<>();
	
	@OneToMany(mappedBy="hakemKonferanslar")
	@Fetch(FetchMode.JOIN)
	private List<Hakem> hakemKonferanslar = new ArrayList<>();
	
	@OneToMany(mappedBy="yazarKonferanslar")
	@Fetch(FetchMode.JOIN)
	private List<Yazar> yazarKonferanslar = new ArrayList<>();

	public List<Yonetici> getYoneticiKonferanslar() {
		return yoneticiKonferanslar;
	}



	public void setYoneticiKonferanslar(List<Yonetici> yoneticiKonferanslar) {
		this.yoneticiKonferanslar = yoneticiKonferanslar;
	}



	public List<Hakem> getHakemKonferanslar() {
		return hakemKonferanslar;
	}



	public void setHakemKonferanslar(List<Hakem> hakemKonferanslar) {
		this.hakemKonferanslar = hakemKonferanslar;
	}



	public List<Yazar> getYazarKonferanslar() {
		return yazarKonferanslar;
	}



	public void setYazarKonferanslar(List<Yazar> yazarKonferanslar) {
		this.yazarKonferanslar = yazarKonferanslar;
	}
	
	
	
	

	
	

}
