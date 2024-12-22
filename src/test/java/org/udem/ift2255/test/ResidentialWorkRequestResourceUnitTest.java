package org.udem.ift2255.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.udem.ift2255.dto.ResidentialWorkRequestDTO;
import org.udem.ift2255.model.ResidentialWorkRequest;
import org.udem.ift2255.resource.ResidentialWorkRequestResource;
import org.udem.ift2255.service.ResidentialWorkRequestService;

import jakarta.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ResidentialWorkRequestResourceUnitTest {

    private ResidentialWorkRequestResource resource;
    private ResidentialWorkRequestService mockService;

    @BeforeEach
    void setUp() {
        // Create the mock service
        mockService = Mockito.mock(ResidentialWorkRequestService.class);

        // Instantiate the resource and inject the mock service
        resource = new ResidentialWorkRequestResource();
        resource.setWorkRequestService(mockService);
    }

    // -------------------------------------------------------------
    // 1) createWorkRequest(...) -> 3 tests
    // -------------------------------------------------------------

    @Test
    void testCreateWorkRequest_Success() {
        Long residentId = 1L;
        ResidentialWorkRequestDTO dto = new ResidentialWorkRequestDTO();
        dto.setWorkTitle("Fix Something");
        dto.setWorkWishedStartDate(LocalDate.now().plusDays(1));

        // Mock the service to do nothing (no exception)
        doNothing().when(mockService).saveRequest(dto, residentId);

        // Act
        Response response = resource.createWorkRequest(residentId, dto);

        // Assert
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity().toString().contains("Work request created successfully"));

        verify(mockService, times(1)).saveRequest(dto, residentId);
    }

    @Test
    void testCreateWorkRequest_InvalidData() {
        Long residentId = 999L;
        ResidentialWorkRequestDTO dto = new ResidentialWorkRequestDTO();
        dto.setWorkTitle(""); // invalid title
        dto.setWorkWishedStartDate(LocalDate.now().minusDays(1)); // invalid date

        // Mock the service to throw an exception
        doThrow(new IllegalArgumentException("Invalid data"))
                .when(mockService).saveRequest(dto, residentId);

        Response response = resource.createWorkRequest(residentId, dto);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity().toString().contains("Invalid data"));
        verify(mockService, times(1)).saveRequest(dto, residentId);
    }

    @Test
    void testCreateWorkRequest_ResidentNotFound() {
        Long residentId = 2L;
        ResidentialWorkRequestDTO dto = new ResidentialWorkRequestDTO();
        dto.setWorkTitle("Title");
        dto.setWorkWishedStartDate(LocalDate.now().plusDays(2));

        // Suppose the service says "Resident not found"
        doThrow(new IllegalArgumentException("Resident not found"))
                .when(mockService).saveRequest(dto, residentId);

        Response response = resource.createWorkRequest(residentId, dto);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity().toString().contains("Resident not found"));
        verify(mockService, times(1)).saveRequest(dto, residentId);
    }

    // -------------------------------------------------------------
    // 2) getWorkRequestsByResident(...) -> 3 tests
    // -------------------------------------------------------------

    @Test
    void testGetWorkRequestsByResident_Success() {
        Long residentId = 10L;

        // We'll emulate a non-empty list from the service
        List<ResidentialWorkRequest> mockList = new ArrayList<>();

        ResidentialWorkRequest req1 = new ResidentialWorkRequest();
        req1.id = 101L;
        // Fix: set the date so it's not null
        req1.setWorkWishedStartDate(LocalDate.of(2024, 1, 15));

        ResidentialWorkRequest req2 = new ResidentialWorkRequest();
        req2.id = 102L;
        // Fix: set the date so it's not null
        req2.setWorkWishedStartDate(LocalDate.of(2024, 2, 20));

        mockList.add(req1);
        mockList.add(req2);

        when(mockService.getWorkRequestsByResident(residentId)).thenReturn(mockList);

        Response response = resource.getWorkRequestsByResident(residentId);

        // Because the list is not empty, the resource should return 200 with the DTO array
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        // Optionally, we could parse the returned entity (list of DTOs),
        // but here we just check the status code is 200

        verify(mockService, times(1)).getWorkRequestsByResident(10L);
    }

    @Test
    void testGetWorkRequestsByResident_Empty() {
        Long residentId = 20L;
        // The service returns an empty list -> resource returns 404
        when(mockService.getWorkRequestsByResident(residentId)).thenReturn(Collections.emptyList());

        Response response = resource.getWorkRequestsByResident(residentId);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity().toString().contains("No work requests found for resident ID 20"));

        verify(mockService, times(1)).getWorkRequestsByResident(20L);
    }

    @Test
    void testGetWorkRequestsByResident_Exception() {
        Long residentId = 30L;
        doThrow(new RuntimeException("DB failure"))
                .when(mockService).getWorkRequestsByResident(residentId);

        Response response = resource.getWorkRequestsByResident(residentId);

        // The resource catches the exception -> returns 500
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity().toString().contains("DB failure"));

        verify(mockService, times(1)).getWorkRequestsByResident(30L);
    }

    // -------------------------------------------------------------
    // 3) getAllWorkRequests() -> 3 tests
    // -------------------------------------------------------------

    @Test
    void testGetAllWorkRequests_Success() {
        // The service returns a non-empty list
        List<ResidentialWorkRequest> mockRequests = new ArrayList<>();
        ResidentialWorkRequest mockReq = new ResidentialWorkRequest();
        mockReq.id = 501L;
        mockReq.setWorkWishedStartDate(LocalDate.now().plusDays(2));  // Non-null date
        mockRequests.add(mockReq);

        when(mockService.getAllRequests()).thenReturn(mockRequests);

        Response response = resource.getAllWorkRequests();
        // If not empty => 200
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(mockService, times(1)).getAllRequests();
    }

    @Test
    void testGetAllWorkRequests_Empty() {
        // The service returns an empty list => 404
        when(mockService.getAllRequests()).thenReturn(Collections.emptyList());

        Response response = resource.getAllWorkRequests();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity().toString().contains("No work requests found"));

        verify(mockService, times(1)).getAllRequests();
    }

    @Test
    void testGetAllWorkRequests_Exception() {
        // The service throws => resource returns 500
        when(mockService.getAllRequests()).thenThrow(new RuntimeException("Server error"));

        Response response = resource.getAllWorkRequests();
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity().toString().contains("Server error"));

        verify(mockService, times(1)).getAllRequests();
    }
}
