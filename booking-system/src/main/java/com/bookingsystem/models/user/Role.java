package com.bookingsystem.models.user;

import javax.persistence.*;

@Entity
//@Table(name = "roles")
@Table(name = "roles",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = "name")
//                @UniqueConstraint(columnNames = "email")
		})
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;

	public Role() {

	}

	public Role(ERole name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}
}