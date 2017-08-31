package hr.vsite.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;

@Entity
@Table(name = "USERS", schema = "LOG")
@SequenceGenerator(name = "USER_SEQ", initialValue = 1, allocationSize = 1, sequenceName = "USER_SEQ")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_SEQ")
	private Integer id;
	
	@Column(name = "USERNAME")
	private String username;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "ROLE_USER", joinColumns = @JoinColumn(name = "u_id", referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name = "r_id", referencedColumnName = "id"))
	private Collection<Role> rolesOfUsers;
	
	public User(){}
	
	public User(String username, String email, String password, Collection<Role> role){
		this.username = username;
		this.email = email;
		this.password = password;
		this.rolesOfUsers = role;
	}
	
	@Transient
	private String passwordConfirm;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public Collection<Role> getRolesOfUsers() {
		return rolesOfUsers;
	}

	public void setRolesOfUsers(Collection<Role> rolesOfUsers) {
		this.rolesOfUsers = rolesOfUsers;
	}
}
