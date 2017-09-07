package hr.vsite.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.vsite.model.TestCase;
import hr.vsite.repository.TestCaseRepository;
import hr.vsite.services.interfaces.TestCaseServise;

@Service
public class TestCaseServiceImpl implements TestCaseServise{

	@Autowired
	private TestCaseRepository testCaseRepository;
	
	@Override
	public void save(TestCase testCase) {
		testCaseRepository.save(testCase);
	}

	@Override
	public void delete(TestCase testcase) {
		TestCase case1 = testCaseRepository.findbyId(testcase.getId());
		testCaseRepository.delete(case1);		
	}

	@Override
	public TestCase findbyId(int id) {
		TestCase testCase = testCaseRepository.findbyId(id);
		return testCase;
	}

	@Override
	public List<TestCase> getDailyTests() {
		return testCaseRepository.findDailyTests();		 
	}

	@Override
	public List<TestCase> ovredueTests(String name) {
		return testCaseRepository.ovredueTests(name);
	}

	@Override
	public Long countOvredueTests(String user) {		
		return testCaseRepository.countOvredueTests(user);
	}

	@Override
	public List<TestCase> findAllCasesWithParametar(String SuitName) {
		return testCaseRepository.findAllCasesWithParametar(SuitName);
	}

	@Override
	public Long countTestcases(String date, String status) {
		return testCaseRepository.countTestcases(date, status);
	}

	@Override
	public Long countCases(String Status) {
		return testCaseRepository.countCases(Status);
	}

	@Override
	public Long countTotal() {
		return testCaseRepository.countTotal();
	}

	@Override
	public List<TestCase> findAllTestCases() {
		return testCaseRepository.findAllTestCases();
	}
}
