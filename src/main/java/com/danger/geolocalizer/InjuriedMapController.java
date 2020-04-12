package com.danger.geolocalizer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class InjuriedMapController
{
    private DataRepo dataRepo;

    public InjuriedMapController(DataRepo dataRepo)
    {
        this.dataRepo = dataRepo;
    }

    /*@RequestMapping(method = RequestMethod.GET)
    public String getMap(Model model, @RequestParam String x, @RequestParam String y)
    {
        model.addAttribute("x",x);
        model.addAttribute("y",y);

        return "map";
    }*/

    @RequestMapping(method = RequestMethod.GET)
    public String getMap(Model model)
    {
        model.addAttribute("pointList",dataRepo.getPointsList());
        return "index";
    }

}
