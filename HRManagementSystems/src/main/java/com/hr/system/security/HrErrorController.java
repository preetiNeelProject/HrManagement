package com.hr.system.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HrErrorController implements ErrorController {

	@Override
	public String getErrorPath() {
		return "/error";
	}

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public ModelAndView handleError(HttpServletRequest request) {
		
		Integer status = (Integer)request.getAttribute("javax.servlet.error.status_code");
		
		ModelAndView errorPage = new ModelAndView("error");
		
		String errorMsg = "";

		if (status != null) {
			
			Integer statusCode = Integer.valueOf(status.toString());
			
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				
				errorMsg = "Http Error Code: 400. Bad Request";
				errorPage.addObject("errorMsg", errorMsg);
				return errorPage;
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				errorMsg = "Http Error Code: 500. Internal Server Error";
				errorPage.addObject("errorMsg", errorMsg);
				return errorPage;
			} else {
				errorMsg = "Sorry! Data is not available in my database";
				errorPage.addObject("errorMsg", errorMsg);
				return errorPage;
			}

		}
		return errorPage;
	}

}
