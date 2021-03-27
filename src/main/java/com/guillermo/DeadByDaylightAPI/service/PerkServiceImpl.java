package com.guillermo.DeadByDaylightAPI.service;

import com.guillermo.DeadByDaylightAPI.domain.Perk;
import com.guillermo.DeadByDaylightAPI.domain.Survivor;
import com.guillermo.DeadByDaylightAPI.exceptions.NotFoundException;
import com.guillermo.DeadByDaylightAPI.repository.PerkRepository;
import com.guillermo.DeadByDaylightAPI.repository.SurvivorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
@Service
public class PerkServiceImpl implements PerkService{
    private final Logger logger = LoggerFactory.getLogger(PerkServiceImpl.class);
    @Autowired
    private PerkRepository perkRepository;
    @Autowired
    private SurvivorRepository survivorRepository;
    @Override
    public Set<Perk> findAll() {
        return perkRepository.findAll();
    }

    @Override
    public Set<Perk> findByExhaustion(Boolean exhaustion) {
        return perkRepository.findByExhaustion(exhaustion);
    }

    @Override
    public Set<Perk> findByVersionNumber(String version) {
        return perkRepository.findByVersionNumber(version);
    }

    @Override
    public Set<Perk> findBySurvivor(String nameSurvivor) {
        Survivor survivor = survivorRepository.findByName(nameSurvivor);
        return perkRepository.findBySurvivor(survivor);
    }

    @Override
    public Optional<Perk> findByName(String name) {
        return perkRepository.findByName(name);
    }

    @Override
    public Perk findById(long id) throws NotFoundException{
        return perkRepository.findById(id).orElseThrow(() -> new NotFoundException(id));

    }

    @Override
    public Perk addPerk(Perk perk) {
        return perkRepository.save(perk);
    }

    @Override
    public void deletedById(long id) throws NotFoundException{
        perkRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        perkRepository.deleteById(id);
    }

    @Override
    public Perk modifyPerk(long id, Perk newPerk) throws NotFoundException {
        Perk perk = perkRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        newPerk.setId(perk.getId());
        return perkRepository.save(newPerk);
    }
}
