package uz.oasis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.oasis.model.CountryDto;
import uz.oasis.service.CountryService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("country")
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public ModelAndView get() {
        return countryService.findAll();
    }

    @PostMapping("/save")
    public String save(@ModelAttribute CountryDto countryDto) {
        return countryService.save(countryDto);
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") UUID id, @ModelAttribute CountryDto countryDto) {
        return countryService.updateById(id, countryDto);
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") UUID id) {
        return countryService.deleteById(id);
    }

    @GetMapping("/editPage/{id}")
    public ModelAndView edit(@PathVariable(name = "id") UUID id) {
        return countryService.editPage(id);
    }

    @GetMapping("/addPage")
    public ModelAndView addPage() {
        return countryService.addPage();
    }
}
