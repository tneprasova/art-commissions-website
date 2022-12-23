package cz.cvut.fit.tjv.art_commissions.app.business;

import cz.cvut.fit.tjv.art_commissions.app.dao.ArtistRepository;
import cz.cvut.fit.tjv.art_commissions.app.dao.CommissionRepository;
import cz.cvut.fit.tjv.art_commissions.app.domain.ArtType;
import cz.cvut.fit.tjv.art_commissions.app.domain.Artist;
import cz.cvut.fit.tjv.art_commissions.app.domain.Commission;
import cz.cvut.fit.tjv.art_commissions.app.domain.Customer;
import cz.cvut.fit.tjv.art_commissions.app.exceptions.CommissionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
class CommissionServiceTest {
    @Autowired
    CommissionService commissionService;
    @MockBean
    CommissionRepository commissionRepository;
    @MockBean
    ArtistRepository artistRepository;

    @Test
    void addCommissionerTest() {
        // Testing data
        Customer customer = new Customer("Bob", null);
        Artist artist1 = new Artist("Alice", 500, ArtType.SKETCHING, null, new HashSet<>(), new HashSet<>());
        artist1.setId(1L);
        Artist artist2 = new Artist("John", 500, ArtType.SKETCHING, null, new HashSet<>(), new HashSet<>());
        artist2.setId(2L);
        Artist artist3 = new Artist("Terry", 500, ArtType.WATERCOLOR_PAINTING, null, new HashSet<>(), new HashSet<>());
        artist3.setId(3L);
        Commission commission = new Commission(ArtType.SKETCHING, "", 5, LocalDate.now(), customer, Set.of(artist2));
        commission.setId(4L);

        // Mocking
        Mockito.when(artistRepository.findById(1L)).thenReturn(Optional.of(artist1));
        Mockito.when(artistRepository.findById(3L)).thenReturn(Optional.of(artist3));
        Mockito.when(commissionRepository.findById(4L)).thenReturn(Optional.of(commission));

        // Test adding a new commissioner
        commissionService.addCommissioner(4L, 1L);

        Mockito.verify(artistRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(commissionRepository, Mockito.times(1)).findById(4L);
        Assertions.assertTrue(commission.getCommissioners().contains(artist1));

        // Test adding the same commissioner
        CommissionException ex1 = Assertions.assertThrows(CommissionException.class, () -> commissionService.addCommissioner(4L, 1L));

        Mockito.verify(artistRepository, Mockito.times(2)).findById(1L);
        Mockito.verify(commissionRepository, Mockito.times(2)).findById(4L);
        Assertions.assertEquals("The artist with ID 1 is already assigned to commission with ID 4", ex1.getMessage());

        // Test adding a commissioner with different art type specialization
        CommissionException ex2 = Assertions.assertThrows(CommissionException.class, () -> commissionService.addCommissioner(4L, 3L));

        Mockito.verify(artistRepository, Mockito.times(1)).findById(3L);
        Mockito.verify(commissionRepository, Mockito.times(3)).findById(4L);
        Assertions.assertEquals("The artist with ID 3 does not specialize in the art type of the commission with ID 4", ex2.getMessage());
    }
}