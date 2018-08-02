package com.diego.geographicapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cities")
@Getter
@Setter
@EqualsAndHashCode
public class City {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Exclude
	private Long id;

	@NotBlank
	@Column(unique = true)
	@Size(min = 3, message = "Name should have at least 3 and less than 100 characters")
	private String name;

	@NotBlank
	@Column(unique = true)
	@Size(min = 3, max = 3, message = "City code should have 3 characters")
	private String cityCode;

}
