package com.example.model;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="Yonetici")
public class Yonetici {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long yoneticiId;

	
	@JsonIgnore
	@ManyToOne
	private Kullanici yoneticiler;
	
	@JsonIgnore
	@ManyToOne
	private Konferans yoneticiKonferanslar;
	
	
	public Kullanici getYoneticiler() {
		return yoneticiler;
	}


	public void setYoneticiler(Kullanici yoneticiler) {
		this.yoneticiler = yoneticiler;
	}


	
	public long getYoneticiId() {
		return yoneticiId;
	}


	public void setYoneticiId(long yoneticiId) {
		this.yoneticiId = yoneticiId;
	}


	public Konferans getYoneticiKonferanslar() {
		return yoneticiKonferanslar;
	}


	public void setYoneticiKonferanslar(Konferans yoneticiKonferanslar) {
		this.yoneticiKonferanslar = yoneticiKonferanslar;
	}


	

	
	
	
	/*public Yonetici(Kullanici kullanicilar) {
		super();
		this.kullanicilar = kullanicilar;
	}*/
	
	public Yonetici()
	{
		
	}
	
	
	

	
/*	@JoinColumn(table ="KONFERANS", name = "konferans_id")
	private Konferans konferans;*/


	


	/*public Konferans getKonferans() {
		return konferans;
	}


	public void setKonferans(Konferans konferans) {
		this.konferans = konferans;
	}*/


	


	

}
