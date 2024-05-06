package uz.oasis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.oasis.model.request.DistrictRequestDto;
import uz.oasis.service.DistrictService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("district")
public class DistrictController {

    private final DistrictService districtService;

    @GetMapping
    public ModelAndView get() {
        return districtService.findAll();
    }

    @PostMapping("/save")
    public String save(@ModelAttribute DistrictRequestDto districtRequestDto) {
        return districtService.save(districtRequestDto);
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") UUID id, @ModelAttribute DistrictRequestDto districtRequestDto) {
        return districtService.updateById(id, districtRequestDto);
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") UUID id) {
        return districtService.deleteById(id);
    }

    @GetMapping("/editPage/{id}")
    public ModelAndView edit(@PathVariable(name = "id") UUID id) {
        return districtService.editPage(id);
    }

    @GetMapping("/addPage")
    public ModelAndView addPage() {
        return districtService.addPage();
    }
}
