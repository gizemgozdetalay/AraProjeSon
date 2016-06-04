package com.example.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.model.Kullanici;
import com.example.model.Yonetici;

public interface YoneticiRepository extends CrudRepository<Yonetici, Long> {
	
	List<Yonetici> findByYoneticiler(Kullanici yoneticiler);
	
	
	

}
