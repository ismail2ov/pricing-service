package com.github.ismail2ov.pricingservice.infrastructure.persistence;

import com.github.ismail2ov.pricingservice.domain.Price;
import com.github.ismail2ov.pricingservice.domain.PriceNotFoundException;
import com.github.ismail2ov.pricingservice.domain.PriceRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class PriceRepositoryImpl implements PriceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Price getPrice(Integer brandId, Integer productId, LocalDateTime dateTime) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Price> query = cb.createQuery(Price.class);
        Root<Price> rootEntity = query.from(Price.class);

        Predicate predicateForBrandId = cb.equal(rootEntity.get("brand").get("id"), brandId);
        Predicate predicateForProductId = cb.equal(rootEntity.get("productId"), productId);
        Predicate predicateForStartDate = cb.lessThanOrEqualTo(rootEntity.get("startDate"), dateTime);
        Predicate predicateForEndDate = cb.greaterThanOrEqualTo(rootEntity.get("endDate"), dateTime);

        Predicate predicates = cb.and(predicateForProductId, predicateForBrandId, predicateForStartDate, predicateForEndDate);

        query.select(rootEntity)
                .where(predicates)
                .orderBy(cb.desc(rootEntity.get("priority")));

        Optional<Price> price = entityManager.createQuery(query)
                .getResultStream()
                .findFirst();

        return price.orElseThrow(PriceNotFoundException::new);
    }
}
