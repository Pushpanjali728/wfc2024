package com.webapp.apis.masters.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.apis.exceptions.WebAppError;
import com.webapp.apis.exceptions.WebAppException;
import com.webapp.apis.masters.constants.MastersConstants;
import com.webapp.apis.masters.model.RolesMasterFormBean;
import com.webapp.apis.masters.service.RolesMasterService;
import com.webapp.apis.utility.ResponseWrapper;

@RestController
@RequestMapping(value = "/masters")

public class RolesMasterController {

	@Inject
	RolesMasterService rolesMasterService;

	@RequestMapping(value = "/saveRolesMaster", method = RequestMethod.POST)
	public ResponseWrapper<String> saveRolesMaster(@RequestBody RolesMasterFormBean rolesMasterFormBean) {
		String flag = rolesMasterService.saveRolesMaster(rolesMasterFormBean);
		List<WebAppError> error = new ArrayList<>();
		WebAppException t = null;
		if (flag != MastersConstants.SAVE) {
			error.add(new WebAppError(MastersConstants.SAVE, MastersConstants.DB_ERROR, MastersConstants.SAVE_FAILURE));
			throw new WebAppException(HttpStatus.BAD_REQUEST.toString(), MastersConstants.SAVE_FAILURE, error);
		}
		return new ResponseWrapper<String>(t, HttpStatus.OK, "", flag);
	}

	@RequestMapping(value = "/getAllRolesMaster", method = RequestMethod.GET)
	public ResponseWrapper<List<RolesMasterFormBean>> getAllRolesMaster() {
		List<RolesMasterFormBean> forms = rolesMasterService.getAllRolesMaster();
		return new ResponseWrapper<List<RolesMasterFormBean>>(null, HttpStatus.OK, "", forms);

	}
}
