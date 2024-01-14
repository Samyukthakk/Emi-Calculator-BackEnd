package com.si.pnrcalc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.si.pnrcalc.api.exception.RestErrorResponse;
import com.si.pnrcalc.dto.ComputeDTO;
import com.si.pnrcalc.persistence.repository.ComputeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class PnrCalcApplicationTests {

	@Autowired
	protected MockMvc mvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected ComputeRepository repository;

	@Test
	void testComputeInterest_SuccessResponse() throws Exception {

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/pnr")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(
								getComputeDTO(new BigDecimal(46000), new BigDecimal(30),
										new BigDecimal(4.5))))).andReturn();
		ComputeDTO response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
				ComputeDTO.class);
		assertEquals(200, mvcResult.getResponse().getStatus(), "Unexpected response code");
		assertEquals(repository.findAll().size(), 1);
		assertEquals(response.getResult().intValue(), 1624);
	}

	@Test
	void testComputeInterest_MissingFields_ErrorResponse() throws Exception {
		ComputeDTO requestBody = getComputeDTO(null, new BigDecimal(12),
				new BigDecimal(4));
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/pnr")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestBody))).andReturn();

		RestErrorResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
				RestErrorResponse.class);
		assertEquals(400, mvcResult.getResponse().getStatus(), "Unexpected response code");
		assertTrue(response.getMessages().contains("Principle amount is mandatory to calculate emi"));
	}

	@Test
	void testComputeInterest_FieldRangeError_ErrorResponse() throws Exception {
		ComputeDTO requestBody = getComputeDTO(new BigDecimal(10000), new BigDecimal(31),
				new BigDecimal(4));
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/pnr")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestBody))).andReturn();

		RestErrorResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
				RestErrorResponse.class);
		assertEquals(400, mvcResult.getResponse().getStatus(), "Unexpected response code");
		assertTrue(response.getMessages().contains("Months should be between 1 to 30"));
	}

	private ComputeDTO getComputeDTO(BigDecimal principle, BigDecimal months, BigDecimal roi) {
		ComputeDTO computeDTO = new ComputeDTO();
		computeDTO.setMonths(months);
		computeDTO.setPrinciple(principle);
		computeDTO.setRoi(roi);
		return computeDTO;
	}

}
