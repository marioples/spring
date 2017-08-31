package hr.vsite.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="COMMENTS", schema = "LOG")
@SequenceGenerator(name = "COMMENT_SEQ", initialValue = 1, allocationSize = 1, sequenceName = "COMMENT_SEQ")
public class Comment implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COMMENT_SEQ")
	@Column(name="COMMENT_ID")
	private Integer id;
	
	@Column(name="USER_COMMENT")
	private String userComment;
	
	@Column(name="COMMENT_DESCRIPTION")
	private String commentDescription;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="POSTED_DATE")
	private java.util.Date posted_date;
	
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=TestCase.class)
	@JoinColumn(name="CASE_ID")
	private TestCase testCase;

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCommentDescription() {
		return commentDescription;
	}


	public void setCommentDescription(String commentDescription) {
		this.commentDescription = commentDescription;
	}


	public java.util.Date getPosted_date() {
		return posted_date;
	}


	public void setPosted_date(java.util.Date posted_date) {
		this.posted_date = posted_date;
	}


	public String getUserComment() {
		return userComment;
	}


	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}


	public TestCase getTestCase() {
		return testCase;
	}


	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}
}
