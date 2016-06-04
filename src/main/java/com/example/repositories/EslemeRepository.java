package com.example.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.model.Esleme;
import com.example.model.Hakem;
import com.example.model.Makale;

public interface EslemeRepository extends CrudRepository<Esleme, Long> {
	
	List<Esleme> findByEslemeHakem(Hakem hakem);
	
	Esleme findByEslemeMakaleAndEslemeHakem(Makale makale,Hakem hakem);
	
	List<Esleme> findByEslemeMakale(Makale makale);

}
