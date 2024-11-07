package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.domain.VisitTO;
import com.tecsup.petclinic.entities.Visits;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VisitMapper {

    VisitMapper INSTANCE = Mappers.getMapper(VisitMapper.class);

    VisitTO toVisitTO(Visits visit);

    Visits toVisit(VisitTO visitTO);

    List<VisitTO> toVisitTOList(List<Visits> visits);

    List<Visits> toVisitList(List<VisitTO> visitTOs);
}
