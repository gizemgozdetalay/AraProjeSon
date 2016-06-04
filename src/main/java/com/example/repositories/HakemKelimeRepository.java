package com.example.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.model.Hakem;
import com.example.model.HakemAnahtarKelime;

public interface HakemKelimeRepository extends CrudRepository<HakemAnahtarKelime, Long> {
	
	List<HakemAnahtarKelime> findByHakem(Hakem hakem);

}
