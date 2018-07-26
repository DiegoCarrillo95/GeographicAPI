CREATE TABLE states (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(100) NOT NULL,
  state_code varchar(2) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_state_code (state_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE countries_states (
  country_id bigint(20) NOT NULL,
  states_id bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

