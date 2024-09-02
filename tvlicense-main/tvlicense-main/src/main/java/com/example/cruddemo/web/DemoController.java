package com.example.cruddemo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

	// create a mapping for "/hello"
	
	@GetMapping("/start")
	public String sayHello(Model theModel) {

		//link to next page

		theModel.addAttribute("theDate", new java.util.Date());
		theModel.addAttribute("banana", "I'm big and yellow");

		return "start";
	}
}








