package com.diego.geographicapi.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.when;

import com.diego.geographicapi.dto.CityDto;
import com.diego.geographicapi.dto.CountryDto;
import com.diego.geographicapi.dto.StateDto;
import com.diego.geographicapi.model.City;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.model.State;

@RunWith(MockitoJUnitRunner.class)
public class StateDtoModelTransformerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private CityDtoModelTransformer cityDtoModelTransformer;
	
	@InjectMocks
	private StateDtoModelTransformer stateDtoModelTransformer;
	
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
		
		when(cityDtoModelTransformer.transformToDto(cityModel)).thenReturn(cityDto);
		when(cityDtoModelTransformer.transformToModel(cityDto)).thenReturn(cityModel);
	}
	
	@Test
	public void shouldReturnTransformedStateDtoWhenStateModelToDtoTransformerIsCalledWithExistingModel() {
		StateDto returnedDto = stateDtoModelTransformer.transformToDto(stateModel);
		
		assertEquals(stateDto, returnedDto);
	}
	
	@Test
	public void shouldReturnNullWhenStateModelToDtoTransformerIsCalledWithNull() {
		StateDto returnedDto = stateDtoModelTransformer.transformToDto(null);
		
		assertNull(returnedDto);
	}
	
	@Test
	public void shouldReturnTransformedStateModelWhenStateDtoToModelTransformerIsCalledWithExistingDto(){
		State returnedModel = stateDtoModelTransformer.transformToModel(stateDto);
		
		assertEquals(stateModel.getStateCode(), returnedModel.getStateCode());
		assertEquals(stateModel.getId(), returnedModel.getId());
	}
	
	@Test
	public void shouldReturnNullWhenStateyDtoToModelTransformerIsCalledWithNull() {
		State returnedModel = stateDtoModelTransformer.transformToModel(null);
		
		assertNull(returnedModel);
	}
}
