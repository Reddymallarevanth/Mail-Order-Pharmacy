package com.cts.webportal.controller;

import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cts.webportal.entity.AdHocModel;
import com.cts.webportal.entity.DateModel;
import com.cts.webportal.entity.PrescriptionDetails;
import com.cts.webportal.entity.UserData;
import com.cts.webportal.exceptions.DrugQuantityNotAvailable;
import com.cts.webportal.exceptions.InvalidTokenException;
import com.cts.webportal.service.PortalService;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WebPortalController {

	@Autowired
	private PortalService portalService;
	
	

	/* Login end-points------------------------------------ */

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView getLogin() {
		return new ModelAndView("login");
	}

	
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String postLogin(@ModelAttribute UserData user, HttpSession session, ModelMap warning)
			throws FeignException {
		log.info("inn login post" + user.toString());

		return portalService.postLogin(user, session, warning);
	}

	@GetMapping("/home")
	public String getWelcome(HttpSession session) throws FeignException {
		return portalService.getWelcome((String) session.getAttribute("token"));
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) throws FeignException {
		session.removeAttribute("token");
		session.removeAttribute("memberId");
		session.invalidate();
		return "redirect:/";
	}
	/* DrugService end-points--------------------- */

	@GetMapping("/supportedDrugs")
	public String getSupportedDrugs(HttpSession session, ModelMap warning) {
		if (portalService.isSessionValid(session))
			return postSupportedDrugs(session, warning);
		return "redirect:/";
	}

	@PostMapping(path = "/supportedDrugs")
	public String postSupportedDrugs(HttpSession session, ModelMap warning) throws FeignException {
		log.info("Controller supportedDrugs");
		return portalService.getSupportedDrugs(session, warning);
	}

	/* Subscription end-points--------------------- */

	@GetMapping("/prescriptionform")
	public String getPrescriptionForm(HttpSession session) {
		if (portalService.isSessionValid(session))
			return "prescription";
		return "redirect:/";
	}

	@PostMapping("/subscribe")
	public ModelAndView subscribe(@ModelAttribute PrescriptionDetails prescriptionDetails, HttpSession session)
			throws FeignException {
		log.info("inn subscribe post controller " + prescriptionDetails.toString());
		ModelAndView view = new ModelAndView("prescription");
		view.addObject("msg", portalService.subscribe(prescriptionDetails, session));
		return view;
	}

	@GetMapping("/subscriptions")
	public String getAllSubscriptions(HttpSession session, Model model) throws FeignException {
		log.info("controller getAllSubscriptions");
		return portalService.postSubscriptions(session, model);
	}

	@PostMapping("/unsubscribe/{sId}")
	public String getSubsAfterUnsubscribe(@PathVariable Long sId, HttpSession session,Model model) {
		return portalService.unsubscribe(session, sId,model);
	}
	
	@GetMapping("/errorsubscription")
	public String errorsubscription(HttpSession session, Model model) throws FeignException {
		log.info("controller errorsubscription");
		model.addAttribute("errormsg","You have to clear your payment dues first." );
		return portalService.postSubscriptions(session, model);
	}
	/* Refill end-points-------------------- */

	@PostMapping("/refillDueAsOfDate")
	public String getRefillDueAsofDate(@ModelAttribute DateModel dateModel, HttpSession session, Model model)
			throws NumberFormatException, InvalidTokenException {

		return portalService.getRefillDueAsofDate(session, dateModel, model);
	}

	@GetMapping("/adhocRefill/{sId}")
	public String adhocRefill(@PathVariable("sId") Long sId, HttpSession session) {
		log.info("in controller get adhocrefill");
		session.setAttribute("sub_Id", sId);
		return "adhocRefill";
	}

	@PostMapping("/postAdhocRefill")
	public ModelAndView postAdHocDetails(@ModelAttribute AdHocModel adHocModel, HttpSession session)
			throws NumberFormatException, FeignException, ParseException, InvalidTokenException,
			DrugQuantityNotAvailable {
		log.info("in postAdHocDetails controller " + adHocModel.getLocation() + adHocModel.isPaymentStatus()
				+ adHocModel.getQuantity());
		ModelAndView view = new ModelAndView("refillstatus");
		return portalService.requestAdhocRefill(session, adHocModel, view);
	}
}
