CREATE TABLE cities (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(100) NOT NULL,
  city_code varchar(3) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_city_code (city_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE states_cities (
  state_id bigint(20) NOT NULL,
  cities_id bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
