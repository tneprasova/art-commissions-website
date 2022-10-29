package cz.cvut.fit.tjv.art_commissions.app.business;

import cz.cvut.fit.tjv.art_commissions.app.dao.ArtistRepository;
import cz.cvut.fit.tjv.art_commissions.app.domain.Artist;
import org.springframework.stereotype.Service;

@Service
public class ArtistService extends AbstractCrudService<Artist, Long> {
    protected ArtistService(ArtistRepository repository) {
        super(repository);
    }
}
