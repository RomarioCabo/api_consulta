package romario.cabo.com.br.consulta_api.repository.criteria.Impl;

import lombok.RequiredArgsConstructor;
import romario.cabo.com.br.consulta_api.model.State_;
import romario.cabo.com.br.consulta_api.repository.criteria.StateRepositoryCustom;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.StateFilter;
import romario.cabo.com.br.consulta_api.model.State;
import romario.cabo.com.br.consulta_api.service.dto.StateDto;
import romario.cabo.com.br.consulta_api.service.mapper.StateMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class StateRepositoryCustomImpl implements StateRepositoryCustom {

    private final EntityManager entityManager;
    private final StateMapper stateMapper;

    @Override
    public List<StateDto> filterState(StateFilter state) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<State> criteriaQuery = criteriaBuilder.createQuery(State.class);

        Root<State> root = criteriaQuery.from(State.class);

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

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(State_.ACRONYM)));

        TypedQuery<State> TypedQuery = entityManager.createQuery(criteriaQuery);

        return this.stateMapper.toDto(TypedQuery.getResultList());
    }
}
