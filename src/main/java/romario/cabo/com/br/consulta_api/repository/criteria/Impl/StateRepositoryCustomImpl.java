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
import romario.cabo.com.br.consulta_api.model.State;
import romario.cabo.com.br.consulta_api.model.State_;
import romario.cabo.com.br.consulta_api.repository.criteria.StateRepositoryCustom;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.StateFilter;
import romario.cabo.com.br.consulta_api.service.dto.StateDto;
import romario.cabo.com.br.consulta_api.service.mapper.StateMapper;

@RequiredArgsConstructor
public class StateRepositoryCustomImpl implements StateRepositoryCustom {

    private final EntityManager entityManager;
    private final StateMapper stateMapper;

    @Override
    public Page<StateDto> filterState(StateFilter state, Pageable pageable) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();

        Root<State> root = criteriaQuery.from(State.class);

        criteriaQuery.multiselect(
                root.get(State_.ID),
                root.get(State_.NAME),
                root.get(State_.ACRONYM),
                root.get(State_.IMAGE),
                root.get(State_.CAPITAL),
                root.get(State_.GENTLE),
                root.get(State_.TERRITORIAL_AREA),
                root.get(State_.TOTAL_COUNTIES),
                root.get(State_.TOTAL_POPULATION),
                root.get(State_.DEMOGRAPHIC_DENSITY),
                root.get(State_.IDH),
                root.get(State_.BORDERING_TERRITORY),
                root.get(State_.PIB),
                root.get(State_.NATURAL_ASPECTS),
                root.get(State_.ECONOMIC_ACTIVITIES),
                root.get(State_.CURIOSITY),
                root.get(State_.REGION)
        );

        List<Predicate> predicates = new ArrayList<>();

        if (state.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get(State_.ID), state.getId()));
        }

        if (state.getName() != null) {
            predicates.add(criteriaBuilder.equal(root.get(State_.NAME), state.getName()));
        }

        if (state.getAcronym() != null) {
            predicates.add(criteriaBuilder.equal(root.get(State_.ACRONYM), state.getAcronym()));
        }

        if (!predicates.isEmpty()) criteriaQuery.where(predicates.toArray(new Predicate[0]));

        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(getTableName(pageable.getSort().toString()))));

        TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);

        int totalRows = typedQuery.getResultList().size();
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<StateDto> states = stateMapper.tupleToDto(typedQuery.getResultList());

        if(states.isEmpty()) return null;

        return new PageImpl<>(states, pageable, totalRows);
    }

    private String getTableName(String sort) {
        String[] sortBy = sort.split(":");

        switch (sortBy[0]) {
            case State_.NAME:
                return State_.NAME;
            case State_.ACRONYM:
                return State_.ACRONYM;
            case State_.IMAGE:
                return State_.IMAGE;
            case State_.CAPITAL:
                return State_.CAPITAL;
            case State_.GENTLE:
                return State_.GENTLE;
            case State_.TERRITORIAL_AREA:
                return State_.TERRITORIAL_AREA;
            case State_.TOTAL_COUNTIES:
                return State_.TOTAL_COUNTIES;
            case State_.TOTAL_POPULATION:
                return State_.TOTAL_POPULATION;
            case State_.DEMOGRAPHIC_DENSITY:
                return State_.DEMOGRAPHIC_DENSITY;
            case State_.IDH:
                return State_.IDH;
            case State_.BORDERING_TERRITORY:
                return State_.BORDERING_TERRITORY;
            case State_.PIB:
                return State_.PIB;
            case State_.NATURAL_ASPECTS:
                return State_.NATURAL_ASPECTS;
            case State_.ECONOMIC_ACTIVITIES:
                return State_.ECONOMIC_ACTIVITIES;
            case State_.CURIOSITY:
                return State_.CURIOSITY;
            case State_.REGION:
                return State_.REGION;
            default:
                return State_.ID;
        }
    }
}
