package uz.oasis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import uz.oasis.entity.Region;
import uz.oasis.model.request.RegionRequestDto;
import uz.oasis.repo.CountryRepo;
import uz.oasis.repo.RegionRepo;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepo regionRepo;
    private final CountryRepo countryRepo;


    public ModelAndView findAll() {
        ModelAndView view = new ModelAndView();
        view.setViewName("region");
        view.addObject("regions", regionRepo.findAllResponseDto());
        return view;
    }

    public String deleteById(UUID id) {
        regionRepo.deleteById(id);
        return "redirect:/region";
    }

    public ModelAndView editPage(UUID id) {
        Region region = regionRepo.findById(id);
        if (region == null) {
            return new ModelAndView("redirect:/region");
        }
        ModelAndView view = new ModelAndView();
        view.setViewName("actionRegion");
        view.addObject("region", region);
        view.addObject("countries", countryRepo.findAll());
        return view;
    }

    public ModelAndView addPage() {
        ModelAndView view = new ModelAndView();
        view.setViewName("actionRegion");
        view.addObject("countries", countryRepo.findAll());
        return view;
    }

    public String updateById(UUID id, RegionRequestDto regionRequestDto) {
        regionRepo.updateById(id, regionRequestDto);
        return "redirect:/region";
    }

    public String save(RegionRequestDto regionRequestDto) {
        regionRepo.save(regionRequestDto);
        return "redirect:/region";
    }

    public List<Region> getByCountryId(UUID id) {
        return regionRepo.getByCountryId(id);
    }
}
