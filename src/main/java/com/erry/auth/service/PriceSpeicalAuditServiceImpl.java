package com.erry.auth.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.component.CurrentUserInfo;
import com.erry.auth.model.PriceStrategy;
import com.erry.auth.repository.PriceStrategyRepository;
import com.erry.util.GuidIdUtil;

@Service
@Transactional
public class PriceSpeicalAuditServiceImpl {
	@PersistenceContext
	private EntityManager entityManager;


	
}
