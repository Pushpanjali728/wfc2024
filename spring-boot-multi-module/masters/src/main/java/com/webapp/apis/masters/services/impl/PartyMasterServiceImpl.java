package com.webapp.apis.masters.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.apis.masters.constants.MastersConstants;
import com.webapp.apis.masters.domain.PartyMaster;
import com.webapp.apis.masters.mapper.PartyMasterMapper;
import com.webapp.apis.masters.model.PartyMasterFormBean;
import com.webapp.apis.masters.model.SearchCriteriaFormBean;
import com.webapp.apis.masters.model.ServerSidePagination;
import com.webapp.apis.masters.repository.PartyMasterRepository;
import com.webapp.apis.masters.repository.specs.PartyMasterSpecification;
import com.webapp.apis.masters.repository.specs.SearchCriteria;
import com.webapp.apis.masters.repository.specs.SearchOperation;
import com.webapp.apis.masters.service.PartyMasterService;
import com.webapp.apis.utility.SessionUtility;

@Service
public class PartyMasterServiceImpl implements PartyMasterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PartyMasterServiceImpl.class);

	@Inject
	private PartyMasterRepository partyMasterRepository;
	@Inject
	private SessionUtility sessionUtility;

	@Override
	public List<PartyMasterFormBean> getAllPartyMasters(ServerSidePagination serverSidePagination) {

		//Page<PartyMaster> partyMasters = null;
		// Pageable requestedPage = PageRequest.of(serverSidePagination.getPage(),
		// serverSidePagination.getSize(), Sort.by("id").descending());
		//Pageable requestedPage = PageRequest.of(serverSidePagination.getPage(), serverSidePagination.getSize(), Sort.Direction.DESC, "Id");
		//Pageable requestedPage = PageRequest.of(serverSidePagination.getPage(), serverSidePagination.getSize());
		//Pageable requestedPage = PageRequest.of(serverSidePagination.getPage(), serverSidePagination.getSize(), Sort.by("Id").ascending()); 
		//Pageable requestedPage = PageRequest.of(serverSidePagination.getPage(), serverSidePagination.getSize());
		//partyMasters = partyMasterRepository.findAllByOrderByIdDesc(requestedPage);
		//partyMasters = partyMasterRepository.findAll(requestedPage);
		// List<PartyMasterFormBean> listPartyMaster = new
		// ArrayList<PartyMasterFormBean>();
		/*
		 * for (PartyMaster partyMaster : partyMasters.getContent()) {
		 * PartyMasterFormBean partyMasterFormBean = new PartyMasterFormBean();
		 * //BeanUtils.copyProperties(partyMaster, partyMasterFormBean);
		 * partyMasterFormBean= PartyMasterMapper.mapDomainToForm(partyMaster);
		 * listPartyMaster.add(partyMasterFormBean); }
		 */
		
		Page<PartyMaster> partyMasters = null;
		// Pageable requestedPage = PageRequest.of(serverSidePagination.getPage(),
		// serverSidePagination.getSize());
		Pageable requestedPage = PageRequest.of(serverSidePagination.getPage(), serverSidePagination.getSize(),
				Sort.by("id").descending());
		partyMasters = partyMasterRepository.findAllByOrderByIdDesc(requestedPage);
		return PartyMasterMapper.mapDomainListToFormList(partyMasters.getContent());
		//return PartyMasterMapper.mapDomainListToFormList(partyMasterRepository.findAll());
	}

	@Transactional
	@Override
	public String savePartyMaster(PartyMasterFormBean partyMasterFormBean) {
		System.out.println(partyMasterFormBean);
		List<PartyMaster> partyMasters = new ArrayList<>();
			partyMasterFormBean.setPartyName(partyMasterFormBean.getPartyName().toUpperCase());
			if (partyMasterFormBean.getId() == null) {
				partyMasterFormBean.setCreatedBy(sessionUtility.getUserSession().getUsername());
				partyMasterFormBean.setCreatedDate(new Date());
			} else {
				partyMasterFormBean.setUpdatedBy(sessionUtility.getUserSession().getUsername());
				partyMasterFormBean.setUpdatedDate(new Date());
				
			}
			if (partyMasterFormBean.getBranches().size() > 0) {
				partyMasterFormBean.getBranches().forEach(x -> {
					PartyMasterFormBean partymasterformbean = new PartyMasterFormBean();
					partymasterformbean = partyMasterFormBean;
					partymasterformbean.setBranchId(x.getId());
					partymasterformbean.setPartyName(partymasterformbean.getPartylabel() + " - " + x.getLocation_name());
					partyMasters.add(PartyMasterMapper.mapFormToDomain(partymasterformbean));
				});
				
			}
					
		List<PartyMaster> partyMaster = partyMasterRepository.saveAll(partyMasters);

		if (partyMaster != null) {
			return MastersConstants.SAVE;
		}
		return MastersConstants.FAIL;

	}

	@Override
	public List<PartyMasterFormBean> findByWhereCondition(SearchCriteriaFormBean searchCriteriaFormBean) {
		
		PartyMasterSpecification msTitleRating = new PartyMasterSpecification();
        msTitleRating.add(new SearchCriteria(searchCriteriaFormBean.getKey(),searchCriteriaFormBean.getValue(), SearchOperation.valueOf(searchCriteriaFormBean.getOperation())));
       // msTitleRating.add(new SearchCriteria("rating", 7, SearchOperation.GREATER_THAN));
        List<PartyMaster> titleMasterList = partyMasterRepository.findAll(msTitleRating);
		//List<titleMaster> titleMasterList = titleMasterRepository.findByWhereCondition(where);
        
		return PartyMasterMapper.mapDomainListToFormList(titleMasterList);
	}

}
