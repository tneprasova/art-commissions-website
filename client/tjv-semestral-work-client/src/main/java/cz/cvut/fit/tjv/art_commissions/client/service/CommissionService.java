package cz.cvut.fit.tjv.art_commissions.client.service;

import cz.cvut.fit.tjv.art_commissions.client.api_client.CommissionClient;
import cz.cvut.fit.tjv.art_commissions.client.dto.CommissionDto;
import cz.cvut.fit.tjv.art_commissions.client.dto.CommissionPostDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class CommissionService {
    CommissionClient commissionClient;
    private boolean isCurrentCommissionSet;

    public CommissionService(CommissionClient commissionClient) {
        this.commissionClient = commissionClient;
        this.isCurrentCommissionSet = false;
    }

    public CommissionDto create(CommissionPostDto commission) {
        return commissionClient.create(commission);
    }

    public Collection<CommissionDto> readAll() {
        return commissionClient.readAll();
    }

    public void setCurrentCommission(long id) {
        isCurrentCommissionSet = true;
        commissionClient.setCurrentEntity(id);
    }

    public boolean isCurrentCommissionSet() {
        return isCurrentCommissionSet;
    }

    public Optional<CommissionDto> readOne() {
        return commissionClient.readOne();
    }

    public void update(CommissionDto commission) {
        commissionClient.updateOne(commission);
    }

    public void delete() {
        commissionClient.deleteOne();
        isCurrentCommissionSet = false;
    }

    public Collection<CommissionDto> readMyCommissions(Long id, Optional<String> filter_by) {
        return commissionClient.getMyCommissions(id, filter_by);
    }
}
