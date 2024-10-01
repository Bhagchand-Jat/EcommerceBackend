/**
 * 
 */
package com.ecommerce.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 */
@Controller
@RequestMapping("/ecommerce/api/password")
public class MyController {

	@GetMapping("/reset-password/{tokenId}")
	public String resetPassword(Model model, HttpServletRequest request, @PathVariable("tokenId") Long tokenId) {

		model.addAttribute("resetToken", request.getHeader("Authorization"));
		model.addAttribute("tokenId", tokenId);
		return "resetPassword";
	}

}
