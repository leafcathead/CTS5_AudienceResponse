package com.ars.alpha.service;

import com.ars.alpha.dao.PanicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PanicService implements PanicServiceInterface{

    @Autowired
    PanicRepository panicRepository;

}
