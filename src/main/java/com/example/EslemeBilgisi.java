package com.example;

public class EslemeBilgisi {
	
	
	private int makaleninId;
	
	private long hakeminId;
	
	private int yakinlik;

	public int getYakinlik() {
		return yakinlik;
	}

	public void setYakinlik(int yakinlik) {
		this.yakinlik = yakinlik;
	}

	public int getMakaleninId() {
		return makaleninId;
	}

	public void setMakaleninId(int makaleninId) {
		this.makaleninId = makaleninId;
	}

	public long getHakeminId() {
		return hakeminId;
	}

	public void setHakeminId(long hakeminId) {
		this.hakeminId = hakeminId;
	}

	public EslemeBilgisi(int makaleninId, long hakeminId, int yakinlik) {
		super();
		this.makaleninId = makaleninId;
		this.hakeminId = hakeminId;
		this.yakinlik = yakinlik;
	}
	
	public EslemeBilgisi()
	{
		
	}

}
