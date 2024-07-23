package org.example.settlement.mapper;

public interface RequestBodyMapperInterface<F,T> {
          T map(F request);
}
