package com.diego.geographicapi.util;

import com.diego.geographicapi.dto.CountryDto;
import com.diego.geographicapi.model.Country;

public class Transformer {
	public static CountryDto countryModelToDtoTransformer(Country countryModel) {
		CountryDto countryDto = new CountryDto();
		countryDto.setId(countryModel.getId());
		countryDto.setName(countryModel.getName());
		countryDto.setCountryCode(countryModel.getCountryCode());
		//TODO: countryDto.setStates(states);
		return countryDto;
	}

	public static Country countryDtoToModelTransformer(CountryDto countryDto) {
		Country countryModel = new Country();
		countryModel.setId(countryDto.getId());
		countryModel.setName(countryDto.getName());
		countryModel.setCountryCode(countryDto.getCountryCode());
		//TODO: countryModel.setStates(states);
		return countryModel;
	}
}
