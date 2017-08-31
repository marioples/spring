package hr.vsite.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="ROLE", schema = "LOG")
@SequenceGenerator(name = "ROLE_SEQ", initialValue = 1, allocationSize = 1, sequenceName = "ROLE_SEQ")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ROLE_SEQ")
	private Integer id;
	
	@Column(name="NAME")
	private String name;
	
	@ManyToMany(mappedBy = "rolesOfUsers")
	private Collection<User> users;
	
	public Role(){}
	
	public Role(String name, Collection<User> user){
		this.name = name;
		this.users = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}
}
