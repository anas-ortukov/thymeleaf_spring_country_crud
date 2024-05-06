package uz.oasis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import uz.oasis.entity.District;
import uz.oasis.model.request.DistrictRequestDto;
import uz.oasis.repo.CountryRepo;
import uz.oasis.repo.DistrictRepo;
import uz.oasis.repo.RegionRepo;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final RegionRepo regionRepo;
    private final CountryRepo countryRepo;
    private final DistrictRepo districtRepo;


    public ModelAndView findAll() {
        ModelAndView view = new ModelAndView();
        view.setViewName("district");
        view.addObject("districts", districtRepo.findAllResponseDto());
        return view;
    }

    public String deleteById(UUID id) {
        districtRepo.deleteById(id);
        return "redirect:/district";
    }

    public ModelAndView editPage(UUID id) {
        District district = districtRepo.findById(id);
        if (district == null) {
            return new ModelAndView("redirect:/district");
        }
        ModelAndView view = new ModelAndView();
        view.setViewName("actionDistrict");
        view.addObject("district", district);
        view.addObject("countries", countryRepo.findAll());
        view.addObject("regions", regionRepo.findAll());
        return view;
    }

    public ModelAndView addPage() {
        ModelAndView view = new ModelAndView();
        view.setViewName("actionDistrict");
        view.addObject("countries", countryRepo.findAll());
        view.addObject("regions", regionRepo.findAll());
        return view;
    }

    public String updateById(UUID id, DistrictRequestDto districtRequestDto) {
        districtRepo.updateById(id, districtRequestDto);
        return "redirect:/district";
    }

    public String save(DistrictRequestDto districtRequestDto) {
        districtRepo.save(districtRequestDto);
        return "redirect:/district";
    }
}
