package cz.cvut.fit.tjv.art_commissions.app.api;

import cz.cvut.fit.tjv.art_commissions.app.ApiMain;
import cz.cvut.fit.tjv.art_commissions.app.api.model.converter.CommissionConverter;
import cz.cvut.fit.tjv.art_commissions.app.api.model.dto.CommissionDto;
import cz.cvut.fit.tjv.art_commissions.app.business.CommissionService;
import cz.cvut.fit.tjv.art_commissions.app.business.CustomerService;
import cz.cvut.fit.tjv.art_commissions.app.domain.ArtType;
import cz.cvut.fit.tjv.art_commissions.app.domain.Artist;
import cz.cvut.fit.tjv.art_commissions.app.domain.Commission;
import cz.cvut.fit.tjv.art_commissions.app.domain.Customer;
import cz.cvut.fit.tjv.art_commissions.app.exceptions.EntityDoesNotExistException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = ApiMain.class)
@Import(CommissionController.class)
public class CommissionApiTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    CommissionService commissionService;
    @MockBean
    CommissionConverter commissionConverter;
    @MockBean
    CustomerService customerService;

    @Test
    public void getMyCommissionsTest() throws Exception {
        // Set up testing data
        Customer customer1 = new Customer("Alice", new HashSet<>());
        customer1.setId(1L);
        Customer customer2 = new Customer("Bob", new HashSet<>());
        customer2.setId(2L);
        Artist artist = new Artist("John", 500, ArtType.MUSIC_COMPOSING, null, new HashSet<>(), new HashSet<>());
        artist.setId(1L);

        Commission comm1 = new Commission(ArtType.MUSIC_COMPOSING, "", 5, LocalDate.now(), customer1, Set.of(artist));
        comm1.setId(1L);
        Commission comm2 = new Commission(ArtType.MUSIC_COMPOSING, "", 5, LocalDate.now().minusDays(5), customer1, Set.of(artist));
        comm2.setId(2L);
        Commission comm3 = new Commission(ArtType.MUSIC_COMPOSING, "", 5, LocalDate.now(), customer1, Set.of(artist));
        comm3.setId(3L);
        Commission comm4 = new Commission(ArtType.MUSIC_COMPOSING, "", 5, LocalDate.now(), customer1, Set.of(artist));
        comm4.setId(4L);
        Commission comm5 = new Commission(ArtType.MUSIC_COMPOSING, "", 5, LocalDate.now().plusDays(2), customer2, Set.of(artist));
        comm5.setId(5L);
        Commission comm6 = new Commission(ArtType.MUSIC_COMPOSING, "", 5, LocalDate.now().plusDays(2), customer2, Set.of(artist));
        comm6.setId(6L);

        // Set up DTOs
        CommissionDto commDto1 = new CommissionDto(1L, ArtType.MUSIC_COMPOSING, "", 5,
                500, LocalDate.now().minusDays(5), LocalDate.now().minusDays(4), 1L, Set.of(1L));
        CommissionDto commDto2 = new CommissionDto(2L, ArtType.MUSIC_COMPOSING, "", 5,
                500, LocalDate.now(), LocalDate.now().plusDays(1), 1L, Set.of(1L));
        CommissionDto commDto3 = new CommissionDto(3L, ArtType.MUSIC_COMPOSING, "", 5,
                500, LocalDate.now(), LocalDate.now().plusDays(1), 1L, Set.of(1L));
        CommissionDto commDto4 = new CommissionDto(4L, ArtType.MUSIC_COMPOSING, "", 5,
                500, LocalDate.now(), LocalDate.now().plusDays(1), 1L, Set.of(1L));
        CommissionDto commDto5 = new CommissionDto(5L, ArtType.MUSIC_COMPOSING, "", 5,
                500, LocalDate.now().plusDays(2), LocalDate.now().plusDays(3), 2L, Set.of(1L));
        CommissionDto commDto6 = new CommissionDto(6L, ArtType.MUSIC_COMPOSING, "", 5,
                500, LocalDate.now().plusDays(2), LocalDate.now().plusDays(3), 2L, Set.of(1L));

        // Mock needed function calls
        Mockito.when(customerService.readById(1L)).thenReturn(Optional.of(customer1));
        Mockito.when(customerService.readById(2L)).thenReturn(Optional.of(customer2));
        Mockito.when(customerService.readById(3L)).thenThrow(EntityDoesNotExistException.class);
        Mockito.when(commissionService.findMyActiveCommissions(1L)).thenReturn(List.of(comm1, comm3, comm4));
        Mockito.when(commissionService.findMyActiveCommissions(2L)).thenReturn(List.of());
        Mockito.when(commissionService.findByCustomerId(1L)).thenReturn(List.of(comm1, comm2, comm3, comm4));
        Mockito.when(commissionService.findByCustomerId(2L)).thenReturn(List.of(comm5, comm6));
        Mockito.when(commissionConverter.fromEntityToDto(comm1)).thenReturn(commDto1);
        Mockito.when(commissionConverter.fromEntityToDto(comm2)).thenReturn(commDto2);
        Mockito.when(commissionConverter.fromEntityToDto(comm3)).thenReturn(commDto3);
        Mockito.when(commissionConverter.fromEntityToDto(comm4)).thenReturn(commDto4);
        Mockito.when(commissionConverter.fromEntityToDto(comm5)).thenReturn(commDto5);
        Mockito.when(commissionConverter.fromEntityToDto(comm6)).thenReturn(commDto6);

        // Test the method
        mockMvc.perform(get("/v1/commissions/customers/1")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(Matchers.is(1)))
                .andExpect(jsonPath("$[1].id").value(Matchers.is(2)))
                .andExpect(jsonPath("$[2].id").value(Matchers.is(3)))
                .andExpect(jsonPath("$[3].id").value(Matchers.is(4)));

        mockMvc.perform(get("/v1/commissions/customers/1?filter_by=active")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(Matchers.is(1)))
                .andExpect(jsonPath("$[1].id").value(Matchers.is(3)))
                .andExpect(jsonPath("$[2].id").value(Matchers.is(4)));

        mockMvc.perform(get("/v1/commissions/customers/2")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(Matchers.is(5)))
                .andExpect(jsonPath("$[1].id").value(Matchers.is(6)));

        mockMvc.perform(get("/v1/commissions/customers/2?filter_by=active")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());

        // Try getting a nonexistent customer
        mockMvc.perform(get("/v1/commissions/customers/3")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
