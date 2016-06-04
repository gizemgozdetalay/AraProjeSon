package com.example.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.model.Konferans;
import com.example.model.Makale;

public interface MakaleRepository extends CrudRepository<Makale, Long>{
	
	List <Makale> findByKonferansMakale(Konferans konferans);
	
	Makale findByMakaleUzanti(String makaleUzanti);

}
