ALTER TABLE states DROP INDEX UK_state_code;
ALTER TABLE cities DROP INDEX UK_city_code;

CREATE UNIQUE INDEX UK_state_country_code ON states (state_code, country_id);
CREATE UNIQUE INDEX UK_city_state_code ON cities (city_code, state_id);