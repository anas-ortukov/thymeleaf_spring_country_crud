package uz.oasis.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.oasis.model.request.RegionRequestDto;
import uz.oasis.service.RegionService;

import java.util.UUID;

@CrossOrigin
@Controller
@RequiredArgsConstructor
@RequestMapping("region")
public class RegionController {

    private final RegionService regionService;
    private final HttpServletResponse httpServletResponse;

    @GetMapping
    public ModelAndView get() {
        return regionService.findAll();
    }

    @PostMapping("/save")
    public String save(@ModelAttribute RegionRequestDto regionRequestDto) {
        return regionService.save(regionRequestDto);
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") UUID id, @ModelAttribute RegionRequestDto regionRequestDto) {
        return regionService.updateById(id, regionRequestDto);
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") UUID id) {
        return regionService.deleteById(id);
    }

    @GetMapping("/editPage/{id}")
    public ModelAndView edit(@PathVariable(name = "id") UUID id) {
        return regionService.editPage(id);
    }

    @GetMapping("/addPage")
    public ModelAndView addPage() {
        return regionService.addPage();
    }

}
