package com.example.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.model.Konferans;
import com.example.model.Kullanici;
import com.example.model.Yazar;

public interface YazarRepository extends CrudRepository<Yazar, Long> {
	
	List<Yazar> findByYazarlar(Kullanici yazar);
	
	Yazar findByYazarKonferanslarAndYazarlar(Konferans konferans, Kullanici yazar);
	
	List<Yazar> findByYazarKonferanslar(Konferans konferans);

}
