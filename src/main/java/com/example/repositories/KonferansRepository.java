package com.example.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.model.Konferans;
import com.example.model.Kullanici;
import com.example.model.Yonetici;

public interface KonferansRepository extends CrudRepository<Konferans, Long> {

	
	//List<Konferans> findByKonferans_ismi(String konferans_ismi);
	
//	List<Konferans> findByKonferans_id(long konferans_id);
	
	//List<Konferans> findByKonferans_ismi(String konferans_ismi);
	
	//List<Konferans> findByKonferansYoneticiler(List<Kullanici> konferansYoneticiler);
	
	//List<Konferans> findByYoneticiKonferanslar(List<Yonetici> yoneticiKonferanslar);
	
	
	List<Konferans> findByKonferansId(long id);
}
