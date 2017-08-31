package hr.vsite.repository;

import hr.vsite.model.TestSuit;

public interface CustomTestSuitRepository {

	TestSuit findById(Integer id);
}
