package com.example.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.model.AnahtarKelime;
import com.example.model.Konferans;

public interface AnahtarKelimeRepository extends CrudRepository<AnahtarKelime, Long> {
	
	//List<AnahtarKelime> findByAnahtarKelimeIdAndKonferansAnahtarKelime(long anahtarKelimeId,Konferans konferans );
	List<AnahtarKelime> findByKonferansAnahtarKelime(Konferans konferans);

}
