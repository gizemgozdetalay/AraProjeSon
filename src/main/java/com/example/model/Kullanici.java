package com.example.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.CascadeType;


@Entity
@Table(name="Kullanici")
public class Kullanici {
	
	
	
	@Id
	@Column(name="kullanici_id")
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long kullanici_id;
	
	@NotNull
	private String type;
	
	/*@ManyToMany(fetch=FetchType.LAZY,mappedBy= "konferansYoneticiler")
	private Set<Konferans> yonetici_konferanslar = new HashSet<>(0);
	
	
	@ManyToMany(fetch=FetchType.LAZY,mappedBy= "konferans_hakemler")
	private Set<Konferans> hakem_konferanslar = new HashSet<Konferans>(0);
	
	@ManyToMany(fetch=FetchType.LAZY,mappedBy= "konferans_yazarlar")
	private Set<Konferans> yazar_konferanslar = new HashSet<Konferans>(0);*/
	
	
		
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@NotNull
	private String sifre;

	public String getSifre() {
		return sifre;
	}

	public void setSifre(String sifre) {
		this.sifre = sifre;
	}

	@NotNull
	private String email;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotNull
	private String isim;
	
	@NotNull
	private String soyisim;
	
	@NotNull
	private String kurum;
	
	public Kullanici() { }
	
	public Kullanici(String isim,String soyisim,String email,String kurum,String type,String sifre)
	{
		this.isim=isim;
		this.soyisim=soyisim;
		this.email=email;
		this.kurum=kurum;
		this.type=type;
		this.sifre=sifre;
	}
	
	public Kullanici(String email)
	{
		this.email=email;
	}
	
	

	


	public long getKullanici_id() {
		return kullanici_id;
	}

	public void setKullanici_id(long kullanici_id) {
		this.kullanici_id = kullanici_id;
	}

	public List<Yonetici> getYoneticiler() {
		return yoneticiler;
	}

	public void setYoneticiler(List<Yonetici> yoneticiler) {
		this.yoneticiler = yoneticiler;
	}

	public List<Hakem> getHakemler() {
		return hakemler;
	}

	public void setHakemler(List<Hakem> hakemler) {
		this.hakemler = hakemler;
	}

	public List<Yazar> getYazarlar() {
		return yazarlar;
	}

	public void setYazarlar(List<Yazar> yazarlar) {
		this.yazarlar = yazarlar;
	}

	public String getIsim() {
		return isim;
	}

	public void setIsim(String isim) {
		this.isim = isim;
	}

	public String getSoyisim() {
		return soyisim;
	}

	public void setSoyisim(String soyisim) {
		this.soyisim = soyisim;
	}

	public String getKurum() {
		return kurum;
	}

	public void setKurum(String kurum) {
		this.kurum = kurum;
	}
	
	@OneToMany(mappedBy="yoneticiler")
	@Fetch(FetchMode.JOIN)
	@Cascade(CascadeType.ALL)
	private List<Yonetici> yoneticiler = new ArrayList<>();
	
	@OneToMany(mappedBy="hakemler")
	@Fetch(FetchMode.JOIN)
	@Cascade(CascadeType.ALL)
	private List<Hakem> hakemler = new ArrayList<>();
	
	@OneToMany(mappedBy="yazarlar")
	@Fetch(FetchMode.JOIN)
	@Cascade(CascadeType.ALL)
	private List<Yazar> yazarlar = new ArrayList<>();
	
	/*@OneToMany(mappedBy= "kullanicilar")
	@Fetch(FetchMode.JOIN)
	@Cascade(CascadeType.ALL)
	private Set<Yonetici> Yoneticiler = new HashSet<>();
	
	@OneToMany(mappedBy="kullanici")
	@Fetch(FetchMode.JOIN)
	@Cascade(CascadeType.ALL)
	private Set<Hakem> hakemler = new HashSet<>();

	@OneToMany(mappedBy="yazar")
	@Fetch(FetchMode.JOIN)
	@Cascade(CascadeType.ALL)
	private Set<Yazar> yazarlar = new HashSet<>();
	

	public Set<Yonetici> getYoneticiler() {
		return Yoneticiler;
	}

	public void setYoneticiler(Set<Yonetici> yoneticiler) {
		Yoneticiler = yoneticiler;
	}
	
	public Set<Hakem> getHakemler() {
		return hakemler;
	}

	public void setHakemler(Set<Hakem> hakemler) {
		this.hakemler = hakemler;
	}

	public Set<Yazar> getYazarlar() {
		return yazarlar;
	}

	public void setYazarlar(Set<Yazar> yazarlar) {
		this.yazarlar = yazarlar;
	}
*/
	/*public Set<Konferans> getYonetici_konferanslar() {
		return yonetici_konferanslar;
	}

	public void setYonetici_konferanslar(Set<Konferans> yonetici_konferanslar) {
		this.yonetici_konferanslar = yonetici_konferanslar;
	}

	public Set<Konferans> getHakem_konferanslar() {
		return hakem_konferanslar;
	}

	public void setHakem_konferanslar(Set<Konferans> hakem_konferanslar) {
		this.hakem_konferanslar = hakem_konferanslar;
	}

	public Set<Konferans> getYazar_konferanslar() {
		return yazar_konferanslar;
	}

	public void setYazar_konferanslar(Set<Konferans> yazar_konferanslar) {
		this.yazar_konferanslar = yazar_konferanslar;
	}*/
	
	
	

	

}

