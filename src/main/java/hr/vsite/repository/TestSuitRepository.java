package hr.vsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hr.vsite.model.TestSuit;

@Repository
public interface TestSuitRepository extends JpaRepository<TestSuit, Long>, CustomTestSuitRepository {

	@Override
	@Query("select s from TestSuit s where s.id = :id")
	TestSuit findById(@Param("id") Integer id);
	
}
