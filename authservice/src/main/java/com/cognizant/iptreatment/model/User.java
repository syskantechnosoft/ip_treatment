package com.cognizant.iptreatment.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "us_id")
	@NotNull

	int id;
	@NotNull
	@Column(name = "us_name")
	String username;

	@Column(name = "us_password")
	@NotNull(message = "Password should not be null")
	@NotBlank(message = "Password should not be blank")
	String password;

	@Column(name = "us_first_name")
	String firstName;

	@Column(name = "us_last_name")
	String lastName;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "ur_us_id"), inverseJoinColumns = @JoinColumn(name = "ur_ro_id"))
	private Set<Role> roleList;

	public User() {
		super();
	}

	public User(@NotNull int id, @NotNull String username,
			@NotNull(message = "Password should not be null") @NotBlank(message = "Password should not be blank") String password,
			String firstName, String lastName, Set<Role> roleList) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roleList = roleList;
//		this.menuItemList = menuItemList;
	}

	public User(@NotNull String username,
			@NotNull(message = "Password should not be null") @NotBlank(message = "Password should not be blank") String password,
			String firstName, String lastName, Set<Role> roleList) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roleList = roleList;
//		this.menuItemList = menuItemList;
	}

	public User(@NotNull String username,
			@NotNull(message = "Password should not be null") @NotBlank(message = "Password should not be blank") String password,
			String firstName, String lastName) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(Set<Role> roleList) {
		this.roleList = roleList;
	}

//	public List<MenuItem> getMenuItemList() {
//		return menuItemList;
//	}

//	public void setMenuItemList(List<MenuItem> menuItemList) {
//		this.menuItemList = menuItemList;
//	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + "]";
	}

}
