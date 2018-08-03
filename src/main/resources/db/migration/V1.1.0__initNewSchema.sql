drop table if exists countries;
drop table if exists states;
drop table if exists cities;

CREATE TABLE countries (
  id bigint NOT NULL auto_increment,
  name varchar(100) NOT NULL,
  country_code varchar(2) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_country_code (country_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE states (
  id bigint NOT NULL auto_increment,
  name varchar(100) NOT NULL,
  state_code varchar(2) NOT NULL,
  country_id bigint,
  PRIMARY KEY (id),
  FOREIGN KEY (country_id) REFERENCES countries(id),
  UNIQUE KEY UK_state_code (state_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE cities (
  id bigint NOT NULL auto_increment,
  city_code varchar(3) NOT NULL,
  name varchar(100) NOT NULL,
  state_id bigint,
  PRIMARY KEY (id),
  FOREIGN KEY (state_id) REFERENCES states(id),
  UNIQUE KEY UK_city_code (city_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO countries(name, country_code) VALUES('United States of America', 'US');
INSERT INTO countries(name, country_code) VALUES('Spain', 'ES');
INSERT INTO countries(name, country_code) VALUES('China', 'CN');
INSERT INTO countries(name, country_code) VALUES('Japan', 'JP');
INSERT INTO countries(name, country_code) VALUES('South Africa', 'ZN');
