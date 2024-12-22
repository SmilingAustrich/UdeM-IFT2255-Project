package org.udem.ift2255.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.junit.QuarkusTest;
import org.udem.ift2255.dto.ResidentialWorkRequestDTO;
import org.udem.ift2255.model.Resident;
import org.udem.ift2255.model.ResidentialWorkRequest;
import org.udem.ift2255.repository.ResidentRepository;
import org.udem.ift2255.repository.ResidentialWorkRequestRepository;
import org.udem.ift2255.service.ResidentialWorkRequestService;

import io.quarkus.hibernate.orm.panache.PanacheQuery;

@QuarkusTest
public class ResidentialWorkRequestServiceTest {

    @Inject
    ResidentialWorkRequestService service;

    ResidentialWorkRequestRepository mockWorkRequestRepo;
    ResidentRepository mockResidentRepo;

    @BeforeEach
    void setUp() {
        mockWorkRequestRepo = mock(ResidentialWorkRequestRepository.class);
        mockResidentRepo = mock(ResidentRepository.class);
        service.setRepositories(mockWorkRequestRepo, mockResidentRepo);
    }

    // -------------------------------------------------
    // FUNCTIONALITY 1: saveRequest(...)
    // -------------------------------------------------
    @Test
    @Transactional
    void testSaveRequest_ValidData() {
        Long residentId = 1L;
        Resident mockResident = new Resident();
        mockResident.id = residentId;
        when(mockResidentRepo.findById(residentId)).thenReturn(mockResident);

        ResidentialWorkRequestDTO dto = new ResidentialWorkRequestDTO();
        dto.setWorkTitle("Fix Street");
        dto.setDetailedWorkDescription("Fix the potholes on Main Street.");
        dto.setWorkType("Roadwork");
        dto.setNeighbourhood("Downtown");
        dto.setWorkWishedStartDate(LocalDate.now().plusDays(5));

        doAnswer(inv -> {
            ResidentialWorkRequest req = inv.getArgument(0);
            req.id = 123L;
            return null;
        }).when(mockWorkRequestRepo).persist(any(ResidentialWorkRequest.class));

        service.saveRequest(dto, residentId);

        verify(mockResidentRepo, times(1)).findById(residentId);
        verify(mockWorkRequestRepo, times(1)).persist(any(ResidentialWorkRequest.class));
    }

    @Test
    @Transactional
    void testSaveRequest_MissingTitle() {
        Long residentId = 2L;
        Resident mockResident = new Resident();
        mockResident.id = residentId;
        when(mockResidentRepo.findById(residentId)).thenReturn(mockResident);

        ResidentialWorkRequestDTO dto = new ResidentialWorkRequestDTO();
        dto.setWorkTitle(""); // Missing/empty
        dto.setWorkWishedStartDate(LocalDate.now().plusDays(10));

        assertThrows(IllegalArgumentException.class, () -> {
            service.saveRequest(dto, residentId);
        });
        verify(mockWorkRequestRepo, never()).persist(any(ResidentialWorkRequest.class));
    }

    @Test
    @Transactional
    void testSaveRequest_InvalidDate() {
        Long residentId = 3L;
        Resident mockResident = new Resident();
        mockResident.id = residentId;
        when(mockResidentRepo.findById(residentId)).thenReturn(mockResident);

        ResidentialWorkRequestDTO dto = new ResidentialWorkRequestDTO();
        dto.setWorkTitle("New Playground");
        dto.setWorkWishedStartDate(LocalDate.now().minusDays(1)); // Past

        assertThrows(IllegalArgumentException.class, () -> {
            service.saveRequest(dto, residentId);
        });
        verify(mockWorkRequestRepo, never()).persist(any(ResidentialWorkRequest.class));
    }

    // -------------------------------------------------
    // FUNCTIONALITY 2: getAllRequests()
    // -------------------------------------------------
    @Test
    void testGetAllRequests_NonEmpty() {
        ResidentialWorkRequest mockReq = new ResidentialWorkRequest();
        mockReq.id = 10L;
        List<ResidentialWorkRequest> mockList = new ArrayList<>();
        mockList.add(mockReq);

        when(mockWorkRequestRepo.listAll()).thenReturn(mockList);

        List<ResidentialWorkRequest> result = service.getAllRequests();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).id);
    }

    @Test
    void testGetAllRequests_Empty() {
        when(mockWorkRequestRepo.listAll()).thenReturn(Collections.emptyList());

        List<ResidentialWorkRequest> result = service.getAllRequests();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllRequests_NullReturn() {
        when(mockWorkRequestRepo.listAll()).thenReturn(null);

        List<ResidentialWorkRequest> result = service.getAllRequests();
        assertNotNull(result, "The result should not be null even if the repository returns null.");
        assertTrue(result.isEmpty(), "If repository returns null, service should return an empty list.");
    }


    // -------------------------------------------------
    // FUNCTIONALITY 3: getRequestById(...)
    // -------------------------------------------------
    @Test
    void testGetRequestById_Found() {
        // We'll assume the real code does em.find(...),
        // which is trickier to mock. If your code calls
        // a repo method, you can do when(mockWorkRequestRepo.findById(99L))...
        // For demonstration, let's pretend we have a method or partial integration test.
        // We'll do a simpler approach:

        ResidentialWorkRequest mockReq = new ResidentialWorkRequest();
        mockReq.id = 99L;

        // Suppose we do a direct approach: your code calls 'em.find(...)',
        // so mocking the repository might not help. But let's assume
        // your code calls mockWorkRequestRepo.findById(...) for the test:
        when(mockWorkRequestRepo.findById(99L)).thenReturn(mockReq);

        ResidentialWorkRequest result = service.getRequestById(99L);
        // If your actual code is using em.find, you'd do an integration test or PanacheMock.
        // This is a simplified demonstration.

        assertNotNull(result);
        assertEquals(99L, result.id);
    }

    @Test
    void testGetRequestById_NotFound() {
        when(mockWorkRequestRepo.findById(777L)).thenReturn(null);

        ResidentialWorkRequest result = service.getRequestById(777L);
        assertNull(result);
    }

    @Test
    void testGetRequestById_Exception() {
        when(mockWorkRequestRepo.findById(555L)).thenThrow(new RuntimeException("DB Error"));

        ResidentialWorkRequest result = service.getRequestById(555L);
        // The code in your service catches the exception and returns null
        assertNull(result);
    }

    // -------------------------------------------------
    // FUNCTIONALITY 4: getRequestByTitle(...)
    // -------------------------------------------------
    @Test
    void testGetRequestByTitle_Found() {
        ResidentialWorkRequest mockReq = new ResidentialWorkRequest();
        mockReq.id = 400L;
        mockReq.setWorkTitle("Special Title");

        // We want to return a mocked PanacheQuery
        PanacheQuery<ResidentialWorkRequest> mockQuery = mock(PanacheQuery.class);
        when(mockQuery.firstResult()).thenReturn(mockReq);

        when(mockWorkRequestRepo.find("workTitle", "Special Title"))
                .thenReturn(mockQuery);

        ResidentialWorkRequest result = service.getRequestByTitle("Special Title");
        assertNotNull(result);
        assertEquals(400L, result.id);
        assertEquals("Special Title", result.getWorkTitle());
    }

    @Test
    void testGetRequestByTitle_NotFound() {
        PanacheQuery<ResidentialWorkRequest> mockQuery = mock(PanacheQuery.class);
        when(mockQuery.firstResult()).thenReturn(null);

        when(mockWorkRequestRepo.find("workTitle", "Unknown Title"))
                .thenReturn(mockQuery);

        ResidentialWorkRequest result = service.getRequestByTitle("Unknown Title");
        assertNull(result);
    }

    @Test
    void testGetRequestByTitle_Exception() {
        when(mockWorkRequestRepo.find("workTitle", "Fail"))
                .thenThrow(new RuntimeException("DB Fail"));

        ResidentialWorkRequest result = service.getRequestByTitle("Fail");
        // The method logs and returns null
        assertNull(result);
    }

    // -------------------------------------------------
    // FUNCTIONALITY 5: getFilteredRequests(...)
    // -------------------------------------------------
    @Test
    void testGetFilteredRequests_Found() {
        ResidentialWorkRequest mockReq = new ResidentialWorkRequest();
        mockReq.id = 200L;
        mockReq.setWorkType("Roadwork");
        mockReq.setNeighbourhood("Downtown");

        List<ResidentialWorkRequest> mockList = new ArrayList<>();
        mockList.add(mockReq);

        // Mock PanacheQuery
        PanacheQuery<ResidentialWorkRequest> mockQuery = mock(PanacheQuery.class);
        when(mockQuery.list()).thenReturn(mockList);

        when(mockWorkRequestRepo.find("workType = ?1 and neighbourhood = ?2", "Roadwork", "Downtown"))
                .thenReturn(mockQuery);

        List<ResidentialWorkRequest> result = service.getFilteredRequests("Roadwork", "Downtown");
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(200L, result.get(0).id);
    }

    @Test
    void testGetFilteredRequests_Empty() {
        PanacheQuery<ResidentialWorkRequest> mockQuery = mock(PanacheQuery.class);
        when(mockQuery.list()).thenReturn(Collections.emptyList());

        when(mockWorkRequestRepo.find("workType = ?1 and neighbourhood = ?2", "Plumbing", "Suburb"))
                .thenReturn(mockQuery);

        List<ResidentialWorkRequest> result = service.getFilteredRequests("Plumbing", "Suburb");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetFilteredRequests_Exception() {
        when(mockWorkRequestRepo.find("workType = ?1 and neighbourhood = ?2", "Fake", "Fake"))
                .thenThrow(new RuntimeException("DB fail"));

        // The service returns Collections.emptyList() if exception is thrown
        List<ResidentialWorkRequest> result = service.getFilteredRequests("Fake", "Fake");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // -------------------------------------------------
    // FUNCTIONALITY 6: getWorkRequestsByResident(...)
    // -------------------------------------------------
    @Test
    void testGetWorkRequestsByResident_Found() {
        ResidentialWorkRequest mockReq1 = new ResidentialWorkRequest();
        mockReq1.id = 300L;
        ResidentialWorkRequest mockReq2 = new ResidentialWorkRequest();
        mockReq2.id = 301L;

        List<ResidentialWorkRequest> mockList = new ArrayList<>();
        mockList.add(mockReq1);
        mockList.add(mockReq2);

        when(mockWorkRequestRepo.findByResidentId(10L)).thenReturn(mockList);

        List<ResidentialWorkRequest> result = service.getWorkRequestsByResident(10L);
        assertEquals(2, result.size());
        assertEquals(300L, result.get(0).id);
        assertEquals(301L, result.get(1).id);
    }

    @Test
    void testGetWorkRequestsByResident_Empty() {
        when(mockWorkRequestRepo.findByResidentId(99L)).thenReturn(Collections.emptyList());

        List<ResidentialWorkRequest> result = service.getWorkRequestsByResident(99L);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetWorkRequestsByResident_Exception() {
        when(mockWorkRequestRepo.findByResidentId(777L)).thenThrow(new RuntimeException("DB error"));

        List<ResidentialWorkRequest> result = service.getWorkRequestsByResident(777L);
        // The service code returns an empty list if an exception is thrown
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
