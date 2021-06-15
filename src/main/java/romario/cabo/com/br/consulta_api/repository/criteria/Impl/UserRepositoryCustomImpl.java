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

import romario.cabo.com.br.consulta_api.domain.User;
import romario.cabo.com.br.consulta_api.domain.User_;
import romario.cabo.com.br.consulta_api.repository.criteria.UserRepositoryCustom;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.UserFilter;
import romario.cabo.com.br.consulta_api.service.dto.UserDto;
import romario.cabo.com.br.consulta_api.service.mapper.UserMapper;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final EntityManager entityManager;
    private final UserMapper userMapper;

    @Override
    public Page<UserDto> filterUser(UserFilter filter, Pageable pageable) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();

        Root<User> root = criteriaQuery.from(User.class);

        criteriaQuery.multiselect(
                root.get(User_.ID),
                root.get(User_.NAME),
                root.get(User_.EMAIL)
        );

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get(User_.ID), filter.getId()));
        }

        if (filter.getEmail() != null) {
            predicates.add(criteriaBuilder.equal(root.get(User_.EMAIL), filter.getEmail()));
        }

        if (!predicates.isEmpty()) criteriaQuery.where(predicates.toArray(new Predicate[0]));

        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(getTableName(pageable.getSort().toString()))));

        TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);

        int totalRows = typedQuery.getResultList().size();
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<UserDto> users = userMapper.tupleToDto(typedQuery.getResultList());

        if(users.isEmpty()) return null;

        return new PageImpl<>(users, pageable, totalRows);
    }

    private String getTableName(String sort) {
        String[] sortBy = sort.split(":");

        switch (sortBy[0]) {
            case User_.NAME:
                return User_.NAME;
            case User_.EMAIL:
                return User_.EMAIL;
            default:
                return User_.ID;
        }
    }
}
