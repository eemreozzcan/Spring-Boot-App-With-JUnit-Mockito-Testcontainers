package com.eemrezcn.education.services;

import com.eemrezcn.education.dto.BeerDTO;

import java.util.UUID;

/**
 * Created by jt on 2019-04-20.
 */
public interface BeerService {

    BeerDTO getBeerById(UUID beerId);
    BeerDTO saveNewBeer(BeerDTO beerDTO);

    void updateBeer(UUID beerId, BeerDTO beerDTO);

    void deleteById(UUID beerId);
}