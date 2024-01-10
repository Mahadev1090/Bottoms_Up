package com.ins.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ins.web.dao.MasterUserDao;
//import com.ins.web.dao.MasterUserDao;
import com.ins.web.service.exception.NoMatchingDataException;
import com.ins.web.vo.MasterUserVo;
import com.ins.web.vo.SearchRequest;



import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Service
public class MasterUserServiceImpl implements MasterUserService{
	
	@Autowired
	private MasterUserDao masterUserDao;

	 @PersistenceContext
	    private EntityManager entityManager;
	    
	 @Override
	 public List<MasterUserVo> searchData(SearchRequest searchRequest) {
	        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	        CriteriaQuery<MasterUserVo> criteriaQuery = criteriaBuilder.createQuery(MasterUserVo.class);
	        Root<MasterUserVo> root = criteriaQuery.from(MasterUserVo.class);

	        List<Predicate> predicates = new ArrayList<Predicate>();

	        if (searchRequest.getId() != null) {
	            predicates.add(criteriaBuilder.equal(root.get("id"), searchRequest.getId()));
	        }

	        if (!StringUtils.isEmpty(searchRequest.getName())) {
	            predicates.add(criteriaBuilder.equal(root.get("name"), searchRequest.getName()));
	        }

	        if (!StringUtils.isEmpty(searchRequest.getTitle())) {
	            predicates.add(criteriaBuilder.equal(root.get("title"), searchRequest.getTitle()));
	        }

	        if (!StringUtils.isEmpty(searchRequest.getCompany())) {
	            predicates.add(criteriaBuilder.equal(root.get("company"), searchRequest.getCompany()));
	        }
	        
	        
	        criteriaQuery.where(predicates.toArray(new Predicate[0]));
	        
	        
	        if (!StringUtils.isEmpty(searchRequest.getSortField())) {
	            List<Order> orders = new ArrayList<>();
	            if ("asc".equalsIgnoreCase(searchRequest.getSortOrder())) {
	                orders.add(criteriaBuilder.asc(root.get(searchRequest.getSortField())));
	            } else {
	                orders.add(criteriaBuilder.desc(root.get(searchRequest.getSortField())));
	            }
	            criteriaQuery.orderBy(orders);
	        }
	        
	        TypedQuery<MasterUserVo> query = entityManager.createQuery(criteriaQuery);
	        
	     // Apply limit
	        query.setMaxResults(searchRequest.getLimit());
	        
	        List<MasterUserVo> result = query.getResultList();

	        if (result.isEmpty()) {
	            // Return a custom message when no matching data is found
	            throw new NoMatchingDataException("Matching data not found");
	        }

	        return result;
	    }

	@Override
	public MasterUserVo createUser(MasterUserVo user) {
		
		MasterUserVo savedUser = masterUserDao.save(user);
		return savedUser;
	}



	
}