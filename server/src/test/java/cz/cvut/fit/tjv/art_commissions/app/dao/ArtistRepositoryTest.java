package cz.cvut.fit.tjv.art_commissions.app.dao;

import cz.cvut.fit.tjv.art_commissions.app.domain.ArtType;
import cz.cvut.fit.tjv.art_commissions.app.domain.Artist;
import cz.cvut.fit.tjv.art_commissions.app.domain.Commission;
import cz.cvut.fit.tjv.art_commissions.app.domain.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@DataJpaTest
class ArtistRepositoryTest {
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    CommissionRepository commissionRepository;
    @Autowired
    CustomerRepository customerRepository;

    @AfterEach
    void tearDown() {
        artistRepository.deleteAll();
        commissionRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    void readAllByActiveCommissionsToDateAscTest() {
        Customer customer = new Customer("Bob", new HashSet<>());
        customerRepository.save(customer);

        Artist artist1 = new Artist("Alice", 500, ArtType.DIGITAL_ART, null, new HashSet<>(), new HashSet<>());
        artistRepository.save(artist1);
        Artist artist2 = new Artist("John", 500, ArtType.DIGITAL_ART, null, new HashSet<>(), new HashSet<>());
        artistRepository.save(artist2);
        Artist artist3 = new Artist("Emily", 500, ArtType.DIGITAL_ART, null, new HashSet<>(), new HashSet<>());
        artistRepository.save(artist3);
        Artist artist4 = new Artist("Harry", 500, ArtType.DIGITAL_ART, null, new HashSet<>(), new HashSet<>());
        artistRepository.save(artist4);

        // After each added commission to the database, test the function
        Commission comm1 = new Commission(ArtType.DIGITAL_ART, "", 2, LocalDate.now(), customer, Set.of(artist1, artist2));
        commissionRepository.save(comm1);
        List<Long> returnValues =  artistRepository.readAllByActiveCommissionsToDateAsc(LocalDate.now()).stream().toList();
        List<Long> expectedValues = List.of(3L, 4L, 1L, 2L);
        Assertions.assertEquals(expectedValues, returnValues);

        Commission comm2 = new Commission(ArtType.DIGITAL_ART, "", 2, LocalDate.now().minusDays(3), customer, Set.of(artist3, artist2));
        commissionRepository.save(comm2);
        returnValues =  artistRepository.readAllByActiveCommissionsToDateAsc(LocalDate.now()).stream().toList();
        expectedValues = List.of(3L, 4L, 1L, 2L);
        Assertions.assertEquals(expectedValues, returnValues);
        returnValues =  artistRepository.readAllByActiveCommissionsToDateAsc(LocalDate.now().minusDays(3)).stream().toList();
        expectedValues = List.of(1L, 4L, 2L, 3L);
        Assertions.assertEquals(expectedValues, returnValues);

        Commission comm3 = new Commission(ArtType.DIGITAL_ART, "", 2, LocalDate.now().plusDays(2), customer, Set.of(artist4));
        commissionRepository.save(comm3);
        returnValues =  artistRepository.readAllByActiveCommissionsToDateAsc(LocalDate.now()).stream().toList();
        expectedValues = List.of(3L, 4L, 1L, 2L);
        Assertions.assertEquals(expectedValues, returnValues);

        Commission comm4 = new Commission(ArtType.DIGITAL_ART, "", 2, LocalDate.now(), customer, Set.of(artist1));
        commissionRepository.save(comm4);
        returnValues =  artistRepository.readAllByActiveCommissionsToDateAsc(LocalDate.now()).stream().toList();
        expectedValues = List.of(3L, 4L, 2L, 1L);
        Assertions.assertEquals(expectedValues, returnValues);

        Commission comm5 = new Commission(ArtType.DIGITAL_ART, "", 2, LocalDate.now(), customer, Set.of(artist1, artist4));
        commissionRepository.save(comm5);
        returnValues =  artistRepository.readAllByActiveCommissionsToDateAsc(LocalDate.now()).stream().toList();
        expectedValues = List.of(3L, 2L, 4L, 1L);
        Assertions.assertEquals(expectedValues, returnValues);
        returnValues =  artistRepository.readAllByActiveCommissionsToDateAsc(LocalDate.now().plusDays(10)).stream().toList();
        expectedValues = List.of(1L, 2L, 3L, 4L);
        Assertions.assertEquals(expectedValues, returnValues);
    }
}