package com.ensah.Petitions.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ensah.Petitions.Entity.Signature;

@Repository
public interface SignatureRepository extends MongoRepository<Signature, String>{

	public int countByIdCase(String idCase);
	public List<Signature> getByIdCase(String idCase);
	public void deleteByIdCase(String idCase);
	public List<Signature> findByCinAndIdCase(String cin, String idCase);
	public List<Signature> findByEmailAndIdCase(String email, String idCase);

}
