package com.eemrezcn.education.model.v2;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BeerServiceV2Impl implements BeerServiceV2 {
    @Override
    public BeerDTOV2 getBeerById(UUID beerId) {
        return null;
    }

    @Override
    public BeerDTOV2 saveNewBeer(BeerDTOV2 beerDto) {
        return null;
    }

    @Override
    public void updateBeer(UUID beerId, BeerDTOV2 beerDto) {

    }

    @Override
    public void deleteById(UUID beerId) {

    }
}