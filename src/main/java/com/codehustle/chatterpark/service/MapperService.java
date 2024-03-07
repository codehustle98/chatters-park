package com.codehustle.chatterpark.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MapperService {

    private final ModelMapper modelMapper;

    public <T> T map(Object source,Class<T> target){
        return modelMapper.map(source,target);
    }

    public <S,T> List<T> map(List<S> source,Class<T> target){
        return source
                .stream()
                .map(element -> this.map(element,target))
                .collect(Collectors.toList());
    }
}
