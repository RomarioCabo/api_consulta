package romario.cabo.com.br.consulta_api.repository.criteria.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import romario.cabo.com.br.consulta_api.model.City;
import romario.cabo.com.br.consulta_api.model.City_;
import romario.cabo.com.br.consulta_api.model.State_;
import romario.cabo.com.br.consulta_api.repository.criteria.CityRepositoryCustom;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.CityFilter;
import romario.cabo.com.br.consulta_api.service.dto.CityDto;
import romario.cabo.com.br.consulta_api.service.mapper.CityMapper;


public class CityRepositoryCustomImpl implements CityRepositoryCustom {

    private final EntityManager entityManager;
    private final CityMapper cityMapper;
    
    
    CityRepositoryCustomImpl(EntityManager entityManager, CityMapper cityMapper) {
    	this.entityManager = entityManager;
    	this.cityMapper = cityMapper;
    }
    
    @Override
    public List<CityDto> filterCity(CityFilter filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<City> criteriaQuery = criteriaBuilder.createQuery(City.class);

        Root<City> root = criteriaQuery.from(City.class);

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

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(City_.STATE).get(State_.ACRONYM)), criteriaBuilder.asc(root.get(City_.NAME)));

        TypedQuery<City> TypedQuery = entityManager.createQuery(criteriaQuery);

        return this.cityMapper.toDto(TypedQuery.getResultList());
    }
}
