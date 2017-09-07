package hr.vsite.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="STEPSSS", schema = "LOG")
@SequenceGenerator(name = "STEP_SEQ", initialValue = 1, allocationSize = 1, sequenceName = "STEP_SEQ")
public class TestingSteps {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STEP_SEQ")
	@Column(name="STEP_ID")
	private Integer id;
		
	@Column(name="TEST_STEP")
	private String step;
	
	@Column(name="STEP_CHECKED")
	private Boolean checked;
	
	@ManyToOne(targetEntity = TestCase.class)
	@JoinColumn(name="CASE_ID")
	private TestCase steptestCase;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public TestCase getSteptestCase() {
		return steptestCase;
	}

	public void setSteptestCase(TestCase steptestCase) {
		this.steptestCase = steptestCase;
	}
}
