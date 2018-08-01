package com.diego.geographicapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
	@GeneratedValue
	@EqualsAndHashCode.Exclude
	private Long id;

	@NotBlank
	@Column(unique = true)
	private String name;

	@NotBlank
	@Column(unique = true)
	private String cityCode;

}
