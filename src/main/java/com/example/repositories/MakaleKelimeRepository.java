package com.example.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.model.Makale;
import com.example.model.MakaleAnahtarKelime;

public interface MakaleKelimeRepository extends CrudRepository<MakaleAnahtarKelime, Long> {
	
	List<MakaleAnahtarKelime> findByMakale(Makale makale);

}
