package com.example.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.web.bind.annotation.RequestParam;

@Entity
@Table(name ="Degerlendirme")
public class Degerlendirme {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "degerlendirmeId", unique = true, nullable = false)
	private long degerlendirmeId;
	
	private int puan=0;
	
	private String onay;
	
	private String degerlendirme ;
	
	private String kategori;
	
	private String hakemBilgisi;
	
	private String aday;
	
	private String uzunluk;
	
	private String deger;
	
	
	
	
	
	

	public String getDeger() {
		return deger;
	}

	public void setDeger(String deger) {
		this.deger = deger;
	}

	public String getDegerlendirme() {
		return degerlendirme;
	}

	public void setDegerlendirme(String degerlendirme) {
		this.degerlendirme = degerlendirme;
	}

	public String getKategori() {
		return kategori;
	}

	public void setKategori(String kategori) {
		this.kategori = kategori;
	}

	public String getHakemBilgisi() {
		return hakemBilgisi;
	}

	public void setHakemBilgisi(String hakemBilgisi) {
		this.hakemBilgisi = hakemBilgisi;
	}

	public String getAday() {
		return aday;
	}

	public void setAday(String aday) {
		this.aday = aday;
	}

	public String getUzunluk() {
		return uzunluk;
	}

	public void setUzunluk(String uzunluk) {
		this.uzunluk = uzunluk;
	}

	public long getDegerlendirmeId() {
		return degerlendirmeId;
	}

	public void setDegerlendirmeId(long degerlendirmeId) {
		this.degerlendirmeId = degerlendirmeId;
	}

	public int getPuan() {
		return puan;
	}

	public void setPuan(int puan) {
		this.puan = puan;
	}

	public String getOnay() {
		return onay;
	}

	public void setOnay(String onay) {
		this.onay = onay;
	}

	public Degerlendirme() {
		super();
	}

	public Degerlendirme(int puan, String degerlendirme, String kategori, String hakemBilgisi, String aday,
			String uzunluk,String deger) {
		super();
		this.puan = puan;
		this.degerlendirme = degerlendirme;
		this.kategori = kategori;
		this.hakemBilgisi = hakemBilgisi;
		this.aday = aday;
		this.uzunluk = uzunluk;
		this.deger=deger;
	}

	

}
