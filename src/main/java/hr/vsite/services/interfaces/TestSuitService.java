package hr.vsite.services.interfaces;

import java.util.List;

import hr.vsite.model.TestSuit;

public interface TestSuitService {

	void save(TestSuit testSuit);

	List<TestSuit> findAll();

	TestSuit findById(int id);
	
	void delete(TestSuit testSuit);
}
