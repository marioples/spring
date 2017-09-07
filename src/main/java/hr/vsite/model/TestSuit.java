package hr.vsite.model;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TESTSUITS", schema = "LOG")
@SequenceGenerator(name = "TEST_SUIT_SEQ", initialValue = 1, allocationSize = 1, sequenceName = "TEST_SUIT_SEQ")
public class TestSuit implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TEST_SUIT_SEQ")
	private Integer id;
	
	@NotNull
	private String testSuitName;
		
	@NotNull
	private String expectation;
	
	@OneToMany(mappedBy="suit", orphanRemoval=true)
	private List<TestCase> testCase;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTestSuitName() {
		return testSuitName;
	}

	public void setTestSuitName(String testSuitName) {
		this.testSuitName = testSuitName;
	}

	public String getExpectation() {
		return expectation;
	}

	public void setExpectation(String expectation) {
		this.expectation = expectation;
	}

	public List<TestCase> getTestCase() {
		return testCase;
	}

	public void setTestCase(List<TestCase> testCase) {
		this.testCase = testCase;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestSuit other = (TestSuit) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
