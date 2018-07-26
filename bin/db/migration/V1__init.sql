CREATE TABLE countries (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(100) NOT NULL,
  country_code varchar(2) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_country_code (country_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
