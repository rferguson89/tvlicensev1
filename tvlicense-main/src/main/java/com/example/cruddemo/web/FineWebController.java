package com.example.cruddemo.web;

import com.example.cruddemo.entity.Fine;
import com.example.cruddemo.service.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FineWebController {

    @Autowired
    private FineService fineService;

    @PostMapping("/amendAmount")
    public String displayFineForm(@ModelAttribute FineAmountDTO fineAmountDTO, Model model) {
        Fine theFine = fineService.findByReference(fineAmountDTO.getReference());

        if (theFine != null) {
            FineAmountDTO populatedDTO = new FineAmountDTO();
            populatedDTO.setReference(theFine.getReference());
            populatedDTO.setAmount(theFine.getAmount());
            model.addAttribute("fineAmountDTO", populatedDTO);

            return "fine-details";
        } else {
            // Handle case where fine is not found
            return "fine-start-page";
        }
    }

    @GetMapping("/confirmation")
    public String displaySuccessPage(Model theModel) {
        theModel.addAttribute("theDate", new java.util.Date());
        return "fine-receipt";

    }


    @GetMapping("/search")
    public String searchByReference(Model model) {
        model.addAttribute("fine", new FineAmountDTO());
        return "fine-search";
    }

    @GetMapping("/finePay")
    public String displaypayPage(Model theModel) {
        Model fine = theModel.addAttribute("fine", new FineAmountDTO());
        return "fine-pay";
    }

    @PostMapping("/updateAmount")
    public String updateAmount(@ModelAttribute FineAmountDTO fineAmountDTO) {

        String theReference = fineAmountDTO.getReference();
        double theAmount = fineAmountDTO.getAmount();
        String thePostcode = fineAmountDTO.getPostcode();


        // Check if amount and reference is set to something acceptable
        // i.e. Amount is not zero, and Reference is not an empty string or null
        if(theAmount <= 0 || theReference.trim().isEmpty()) {
            return "redirect:/amendAmount";
        }
        else {
            // Update the specific column of the entity
            Fine theFine = fineService.findByReference(theReference);
            double balance = theFine.getAmount();
            balance = balance - fineAmountDTO.getAmount();
            theFine.setAmount(balance);

            // Save the updated entity
            fineService.save(theFine);

            // Redirect to a success page or handle accordingly
            return "redirect:/Confirmation";

        }
    }

    @PostMapping("/fineDetails")
    public String displayFineDetails(@ModelAttribute FineAmountDTO fineAmountDTO, Model model) {
        Fine theFine = fineService.findByReference(fineAmountDTO.getReference());

        if (theFine != null) {
            model.addAttribute("fine", theFine);
            return "fine-details";
        } else {
            // Handle case where fine is not found
            return "search";
        }
    }
}