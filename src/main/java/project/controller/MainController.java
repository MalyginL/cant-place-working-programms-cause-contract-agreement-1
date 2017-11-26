package project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.comparator.DaoImpl;
import project.comparator.DataFile;
import project.comparator.HistoryLoader;
import project.entity.ThreadControl;
import project.entity.WebRepository;

import javax.validation.Valid;
import java.text.SimpleDateFormat;


@Controller
public class MainController {
    @Autowired
    WebRepository repository;

    @Autowired
    ThreadControl threadControl;

    @Autowired
    HistoryLoader historyLoader;

    @Autowired
    DataFile dataFile;

    @Autowired
    DaoImpl daoImpl;

    @GetMapping("/")
    public String home1() {
        return "home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminForm(WebRepository webRepository, Model model) {
        model.addAttribute("adr", repository.getDataBaseAddress());
        model.addAttribute("log", repository.getDataBaseUserName());
        model.addAttribute("pswd", repository.getDataBasePassword());
        model.addAttribute("pd", repository.getParsingDate());
        model.addAttribute("pdc",(new SimpleDateFormat("dd/MM/yyyy")).format((long)repository.getParsingDate()*1000));
        model.addAttribute("path", repository.getDataPath());
        model.addAttribute("num", repository.getChannels());
        model.addAttribute("nam",repository.getDbName());
        return "admin";
    }

    @RequestMapping(value = "/admin/change", method = RequestMethod.GET)
    public String view(WebRepository webRepository) {
        return "change";
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public String start() {
        daoImpl.setConnection();
        threadControl.setHistory(true);
        threadControl.setReal(true);

        historyLoader.start();
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/change", method = RequestMethod.POST)
    public String change(@Valid WebRepository webRepository, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            return "redirect:/admin";
        }

            repository.setDataBaseAddress(webRepository.getDataBaseAddress());
            repository.setDataBaseUserName(webRepository.getDataBaseUserName());
            repository.setDataBasePassword(webRepository.getDataBasePassword());
            repository.setParsingDate(webRepository.getParsingDate());
            repository.setDataPath(webRepository.getDataPath());
            repository.setChannels(webRepository.getChannels());
            repository.setDbName(webRepository.getDbName());

        return "redirect:/admin";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }


}
