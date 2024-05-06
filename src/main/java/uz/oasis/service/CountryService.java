package uz.oasis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import uz.oasis.entity.Country;
import uz.oasis.model.CountryDto;
import uz.oasis.repo.CountryRepo;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepo countryRepo;


    public ModelAndView findAll() {
        ModelAndView view = new ModelAndView();
        view.setViewName("country");
        view.addObject("countries", countryRepo.findAll());
        return view;
    }

    public String deleteById(UUID id) {
        countryRepo.deleteById(id);
        return "redirect:/country";
    }

    public ModelAndView editPage(UUID id) {
        Country country = countryRepo.findById(id);
        if (country == null) {
            return new ModelAndView("redirect:/country");
        }
        ModelAndView view = new ModelAndView();
        view.setViewName("actionCountry");
        view.addObject("country", country);
        return view;
    }

    public ModelAndView addPage() {
        ModelAndView view = new ModelAndView();
        view.setViewName("actionCountry");
        return view;
    }

    public String updateById(UUID id, CountryDto countryDto) {
        countryRepo.updateById(id, countryDto);
        return "redirect:/country";
    }

    public String save(CountryDto countryDto) {
        countryRepo.save(countryDto);
        return "redirect:/country";
    }
}
