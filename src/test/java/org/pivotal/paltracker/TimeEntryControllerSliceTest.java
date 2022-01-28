package org.pivotal.paltracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.pivotal.paltracker.repository.TimeEntry;
import org.pivotal.paltracker.repository.TimeEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebMvcTest(TimeEntryController.class)
public class TimeEntryControllerSliceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TimeEntryRepository repository;

    @Test
    public void shouldSuccessfullyCreate() throws Exception {
        TimeEntry timeEntryIn = new TimeEntry(1L, 1L, LocalDate.now(), 5);
        TimeEntry timeEntryOut = new TimeEntry(100L, 1L, 1L, LocalDate.now(), 5);
        when(repository.create(eq(timeEntryIn))).thenReturn(timeEntryOut);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        byte[] content = mapper.writeValueAsBytes(timeEntryIn);

        mockMvc.perform(MockMvcRequestBuilders.post("/time-entries/").content(content).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", URI.create(Long.toString(100L)).toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(100L));

    }

    @Test
    public void shouldSuccessfullyGet() throws Exception {
        TimeEntry timeEntryIn = new TimeEntry(1L, 1L, LocalDate.now(), 5);
        TimeEntry timeEntryOut = new TimeEntry(100L, 1L, 1L, LocalDate.now(), 5);
        when(repository.find(1L)).thenReturn(timeEntryOut);

        mockMvc.perform(MockMvcRequestBuilders.get("/time-entries/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(100L));
    }

    @Test
    public void shouldSuccessfullyPut() throws Exception {
        TimeEntry timeEntry = new TimeEntry(1L, 1L, LocalDate.now(), 5);
        when(repository.update(1L, timeEntry)).thenReturn(timeEntry);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        byte[] content = mapper.writeValueAsBytes(timeEntry);

        mockMvc.perform(MockMvcRequestBuilders.put("/time-entries/1").contentType("application/json").content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.hours").value(5));
    }

    @Test
    public void shouldSuccessfullyDelete() throws Exception {
        when(repository.delete(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/time-entries/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldDeleteNotFound() throws Exception {
        when(repository.delete(1L)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/time-entries/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
