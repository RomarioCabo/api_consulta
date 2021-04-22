package romario.cabo.com.br.consulta_api.repository.criteria.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import romario.cabo.com.br.consulta_api.domain.City;
import romario.cabo.com.br.consulta_api.model.City_;
import romario.cabo.com.br.consulta_api.model.State_;
import romario.cabo.com.br.consulta_api.repository.criteria.CityRepositoryCustom;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.CityFilter;
import romario.cabo.com.br.consulta_api.service.dto.CityDto;
import romario.cabo.com.br.consulta_api.service.mapper.CityMapper;

@RequiredArgsConstructor
public class CityRepositoryCustomImpl implements CityRepositoryCustom {

    private final EntityManager entityManager;
    private final CityMapper cityMapper;

    @Override
    public Page<CityDto> filterCity(CityFilter filter, Pageable pageable) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();

        Root<City> root = criteriaQuery.from(City.class);

        criteriaQuery.multiselect(
                root.get(City_.ID),
                root.get(City_.NAME),
                root.get(City_.STATE)
        );

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getIdCity() != null) {
            predicates.add(criteriaBuilder.equal(root.get(City_.ID), filter.getIdCity()));
        }

        if (filter.getNameCity() != null) {
            predicates.add(criteriaBuilder.equal(root.get(City_.NAME), filter.getNameCity()));
        }

        if (filter.getIdState() != null) {
            predicates.add(criteriaBuilder.equal(root.get(City_.STATE).get(State_.ID), filter.getIdState()));
        }

        if (filter.getNameState() != null) {
            predicates.add(criteriaBuilder.equal(root.get(City_.STATE).get(State_.NAME), filter.getNameState()));
        }

        if (filter.getAcronymState() != null) {
            predicates.add(criteriaBuilder.equal(root.get(City_.STATE).get(State_.ACRONYM), filter.getAcronymState()));
        }

        if (!predicates.isEmpty()) criteriaQuery.where(predicates.toArray(new Predicate[0]));

        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(getTableName(pageable.getSort().toString()))));

        TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);

        int totalRows = typedQuery.getResultList().size();
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<CityDto> cities = cityMapper.tupleToDto(typedQuery.getResultList());

        if (cities.isEmpty()) return null;

        return new PageImpl<>(cities, pageable, totalRows);
    }

    private String getTableName(String sort) {
        String[] sortBy = sort.split(":");

        if (City_.NAME.equals(sortBy[0])) {
            return City_.NAME;
        }
        return City_.ID;
    }
}
