package com.example.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.model.Hakem;
import com.example.model.Konferans;
import com.example.model.Kullanici;

public interface HakemRepository extends CrudRepository<Hakem, Long> {
	
	//Hakem findByHakemKonferanslar(Konferans konferans);
	Hakem findByHakemKonferanslarAndHakemler(Konferans konferans,Kullanici hakem);
	List<Hakem> findByHakemKonferanslar(Konferans konferans);
	
	List<Hakem> findByHakemler(Kullanici hakemler);

}
