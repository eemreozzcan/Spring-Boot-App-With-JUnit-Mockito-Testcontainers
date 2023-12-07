package com.eemrezcn.education.model.v2;

import java.util.UUID;

public interface BeerServiceV2 {
    BeerDTOV2 getBeerById(UUID beerId);

    BeerDTOV2 saveNewBeer(BeerDTOV2 beerDto);

    void updateBeer(UUID beerId, BeerDTOV2 beerDto);

    void deleteById(UUID beerId);
}