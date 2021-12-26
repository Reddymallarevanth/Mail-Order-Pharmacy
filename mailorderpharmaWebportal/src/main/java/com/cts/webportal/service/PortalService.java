package com.cts.webportal.service;

import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.cts.webportal.entity.AdHocModel;
import com.cts.webportal.entity.DateModel;
import com.cts.webportal.entity.PrescriptionDetails;
import com.cts.webportal.entity.UserData;
import com.cts.webportal.exceptions.DrugQuantityNotAvailable;
import com.cts.webportal.exceptions.InvalidTokenException;

import feign.FeignException;

public interface PortalService {

	Boolean isSessionValid(HttpSession session);

	String postLogin(UserData user, HttpSession session, ModelMap warning);

	String getWelcome(String attribute);

	String subscribe(PrescriptionDetails prescriptionDetails, HttpSession session);

	String unsubscribe(HttpSession session, Long sId,Model model);

	String getSupportedDrugs(HttpSession session, ModelMap modelMap);

	ModelAndView requestAdhocRefill(HttpSession session, AdHocModel adHocModel, ModelAndView view) throws NumberFormatException,
			FeignException, ParseException, InvalidTokenException, DrugQuantityNotAvailable;

	String postSubscriptions(HttpSession session, Model model);

	String getRefillDueAsofDate( HttpSession session, DateModel dateModel, Model model)
			throws NumberFormatException, InvalidTokenException;
}
