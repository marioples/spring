package hr.vsite.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.vsite.model.TestSuit;
import hr.vsite.repository.TestSuitRepository;
import hr.vsite.services.interfaces.TestSuitService;

@Service
public class TestSuitServiceImpl implements TestSuitService {

	@Autowired
	private TestSuitRepository testSuitRepository;

	@Override
	public void save(TestSuit testSuit) {
		testSuitRepository.save(testSuit);
	}

	@Override
	public List<TestSuit> findAll() {
		return testSuitRepository.findAll();		 
	}

	@Override
	public TestSuit findById(int id) {
		return testSuitRepository.findById(id);
	}

	@Override
	public void delete(TestSuit testSuit) {
		testSuitRepository.delete(testSuit);
	}	
}
