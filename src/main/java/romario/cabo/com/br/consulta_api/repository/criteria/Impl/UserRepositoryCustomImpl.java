package romario.cabo.com.br.consulta_api.repository.criteria.Impl;

import lombok.RequiredArgsConstructor;
import romario.cabo.com.br.consulta_api.model.User_;
import romario.cabo.com.br.consulta_api.repository.criteria.UserRepositoryCustom;
import romario.cabo.com.br.consulta_api.repository.criteria.filter.UserFilter;
import romario.cabo.com.br.consulta_api.model.User;
import romario.cabo.com.br.consulta_api.service.dto.UserDto;
import romario.cabo.com.br.consulta_api.service.mapper.UserMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final EntityManager entityManager;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> filterUser(UserFilter filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> root = criteriaQuery.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get(User_.ID), filter.getId()));
        }

        if (filter.getEmail() != null) {
            predicates.add(criteriaBuilder.equal(root.get(User_.EMAIL), filter.getEmail()));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(User_.ID)));

        TypedQuery<User> TypedQuery = entityManager.createQuery(criteriaQuery);

        List<UserDto> usersDto = this.userMapper.toDto(TypedQuery.getResultList());

        return usersDto;
    }
}
