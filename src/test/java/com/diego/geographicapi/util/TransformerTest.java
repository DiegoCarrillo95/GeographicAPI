package com.diego.geographicapi.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.diego.geographicapi.dto.CityDto;
import com.diego.geographicapi.dto.CountryDto;
import com.diego.geographicapi.dto.StateDto;
import com.diego.geographicapi.model.City;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.model.State;

@RunWith(MockitoJUnitRunner.class)
public class TransformerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private Country countryModel = new Country();
	private CountryDto countryDto = new CountryDto();
	private State stateModel = new State();
	private StateDto stateDto = new StateDto();
	private City cityModel = new City();
	private CityDto cityDto = new CityDto();
	
	private final long countryId = 1;
	private final String countryName = "Brazil";
	private final String countryCode = "BR";
	private final long stateId = 2;
	private final String stateName = "Paraná";
	private final String stateCode = "PR";
	private final long cityId = 3;
	private final String cityName = "Curitiba";
	private final String cityCode = "CWB";
	
	@Before
	public void setup() {
		countryModel.setName(countryName);
		countryModel.setCountryCode(countryCode);
		countryModel.setId(countryId);
		countryDto.setName(countryName);
		countryDto.setCountryCode(countryCode);
		countryDto.setId(countryId);
		
		stateModel.setName(stateName);
		stateModel.setStateCode(stateCode);
		stateModel.setId(stateId);
		stateDto.setName(stateName);
		stateDto.setStateCode(stateCode);
		stateDto.setId(stateId);
		
		cityModel.setName(cityName);
		cityModel.setCityCode(cityCode);
		cityModel.setId(cityId);
		cityDto.setName(cityName);
		cityDto.setCityCode(cityCode);
		cityDto.setId(cityId);
		
		stateModel.addCity(cityModel);
		countryModel.addState(stateModel);
		
		stateDto.getCities().add(cityDto);
		countryDto.getStates().add(stateDto);
	}

	@Test
	public void shouldReturnTransformedCountryDtoWhenCountryModelToDtoTransformerIsCalledWithExistingModel() {
		CountryDto returnedDto = Transformer.countryModelToDtoTransformer(countryModel);
		
		assertEquals(countryDto, returnedDto);
	}
	
	@Test
	public void shouldReturnNullWhenCountryModelToDtoTransformerIsCalledWithNull() {
		CountryDto returnedDto = Transformer.countryModelToDtoTransformer(null);
		
		assertNull(returnedDto);
	}
	
	@Test
	public void shouldReturnTransformedCountryModelWhenCountryDtoToModelTransformerIsCalledWithExistingDto(){
		Country returnedModel = Transformer.countryDtoToModelTransformer(countryDto);
		
		assertEquals(countryModel, returnedModel);
	}
	
	@Test
	public void shouldReturnNullWhenCountryDtoToModelTransformerIsCalledWithNull() {
		Country returnedModel = Transformer.countryDtoToModelTransformer(null);
		
		assertNull(returnedModel);
	}
	
	@Test
	public void shouldReturnTransformedStateDtoWhenStateModelToDtoTransformerIsCalledWithExistingModel() {
		StateDto returnedDto = Transformer.stateModelToDtoTransformer(stateModel);
		
		assertEquals(stateDto, returnedDto);
	}
	
	@Test
	public void shouldReturnNullWhenStateModelToDtoTransformerIsCalledWithNull() {
		StateDto returnedDto = Transformer.stateModelToDtoTransformer(null);
		
		assertNull(returnedDto);
	}
	
	@Test
	public void shouldReturnTransformedStateModelWhenStateDtoToModelTransformerIsCalledWithExistingDto(){
		State returnedModel = Transformer.stateDtoToModelTransformer(stateDto);
		
		assertEquals(stateModel.getStateCode(), returnedModel.getStateCode());
		assertEquals(stateModel.getId(), returnedModel.getId());
	}
	
	@Test
	public void shouldReturnNullWhenStateyDtoToModelTransformerIsCalledWithNull() {
		State returnedModel = Transformer.stateDtoToModelTransformer(null);
		
		assertNull(returnedModel);
	}
	
	@Test
	public void shouldReturnTransformedCityDtoWhenCityModelToDtoTransformerIsCalledWithExistingModel() {
		CityDto returnedDto = Transformer.cityModelToDtoTransformer(cityModel);
		
		assertEquals(cityDto, returnedDto);
	}
	
	@Test
	public void shouldReturnNullWhenCityModelToDtoTransformerIsCalledWithNull() {
		CityDto returnedDto = Transformer.cityModelToDtoTransformer(null);
		
		assertNull(returnedDto);
	}
	
	@Test
	public void shouldReturnTransformedCityModelWhenCityDtoToModelTransformerIsCalledWithExistingDto(){
		City returnedModel = Transformer.cityDtoToModelTransformer(cityDto);
		
		assertEquals(cityModel.getCityCode(), returnedModel.getCityCode());
		assertEquals(cityModel.getId(), returnedModel.getId());
	}
	
	@Test
	public void shouldReturnNullWhenCityDtoToModelTransformerIsCalledWithNull() {
		City returnedModel = Transformer.cityDtoToModelTransformer(null);
		
		assertNull(returnedModel);
	}
}
