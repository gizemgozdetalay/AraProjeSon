package com.example.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.example.model.Kullanici;

public interface KullaniciRepository extends CrudRepository<Kullanici, Long> {
	
	List<Kullanici> findByType(String type);
	
	List<Kullanici> findByEmail(String email);
	
	Kullanici findByEmailAndType(String email,String type);

}
