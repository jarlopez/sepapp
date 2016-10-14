package com.sep.convert;


import com.sep.domain.Client;
import com.sep.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

final class ClientConverter implements Converter<String, Client> {
    @Autowired
    private ClientRepository repo;

    public Client convert(String source) {
        return repo.findOne(Long.valueOf(source));
    }

}