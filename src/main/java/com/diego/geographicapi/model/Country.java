package com.diego.geographicapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="countries")
@Getter
@Setter
@EqualsAndHashCode
public class Country {
	
	@Id
	@GeneratedValue
	@EqualsAndHashCode.Exclude
	private Long id;
	
	@NotBlank
	@Column(unique=true)
	@Size(min = 3, max = 100, message = "Name should have at least 3 and less than 100 characters")
	private String name;
	
	@NotBlank
	@Column(unique=true)
	@Size(min = 2, max = 2, message = "Country code should have 2 characters")
	private String countryCode;
	
	@OneToMany(cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private List<State> states = new ArrayList<>();

}
