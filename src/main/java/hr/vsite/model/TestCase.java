package hr.vsite.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "TESTCASE", schema = "LOG")
@SequenceGenerator(name = "TEST_CASE_SEQ", initialValue = 1, allocationSize = 1, sequenceName = "TEST_CASE_SEQ")
public class TestCase implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TEST_CASE_SEQ")
	@Column(name="CASE_ID")
	private Integer id;
	
	@NotNull
	@Column(name="CASE_NAME")
	private String caseName;
	
	@NotNull
	@Column(name="PRIORITY")
	private String priority;
	
	@Column(name="PREREQUISITES")
	private String prerequisites;
	
	@Column(name="DESCRIPTION", length=512)
	private String desription;
	
	@Column(name="AUTHOR")
	private String author;
	
	@Column(name="OWNER")
	private String owner;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private java.util.Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private java.util.Date updatedDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXECUTION_DATE")
	private java.util.Date executedDate;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ElementCollection
	@CollectionTable(name="Links")
	@MapKeyColumn (name = "LinkPosition")
	@Column(name = "LinkTitle")
	private List<String> releatedLink = new ArrayList<String>();
	
	@Column(name="PROGRESS")
	private Integer progress;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="ENVIRONMENT")
	private String environment;
	
	@Column(name="SUB_ENVIRONMENT")
	private String subEnvironment;	
	
	@Column(name = "ASSIGNED")
	private Boolean isAssigned;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = TestSuit.class)
	private TestSuit suit;
	
	@OneToMany(mappedBy="steptestCase", orphanRemoval=true, fetch = FetchType.EAGER, targetEntity=TestingSteps.class, cascade=CascadeType.ALL)
	private List<TestingSteps> testingSteps;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="testCase", targetEntity=Comment.class, cascade=CascadeType.ALL)
	@OrderBy("posted_date DESC")
	private List<Comment> comments;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(String prerequisites) {
		this.prerequisites = prerequisites;
	}

	public String getDesription() {
		return desription;
	}

	public void setDesription(String desription) {
		this.desription = desription;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TestSuit getSuit() {
		return suit;
	}

	public void setSuit(TestSuit suit) {
		this.suit = suit;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	
	public List<String> getReleatedLink() {
		return releatedLink;
	}

	public void setReleatedLink(List<String> releatedLink) {
		this.releatedLink = releatedLink;
	}

	public java.util.Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}

	public java.util.Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(java.util.Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public java.util.Date getExecutedDate() {
		return executedDate;
	}

	public void setExecutedDate(java.util.Date executedDate) {
		this.executedDate = executedDate;
	}

	public String getSubEnvironment() {
		return subEnvironment;
	}

	public void setSubEnvironment(String subEnvironment) {
		this.subEnvironment = subEnvironment;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<TestingSteps> getTestingSteps() {
		return testingSteps;
	}

	public void setTestingSteps(List<TestingSteps> testingSteps) {
		this.testingSteps = testingSteps;
	}

	public Boolean getIsAssigned() {
		return isAssigned;
	}

	public void setIsAssigned(Boolean isAssigned) {
		this.isAssigned = isAssigned;
	}
}
