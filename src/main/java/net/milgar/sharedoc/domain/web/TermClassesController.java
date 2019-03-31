package net.milgar.sharedoc.domain.web;

import net.milgar.sharedoc.domain.CustomUserDetails;
import net.milgar.sharedoc.domain.model.Document;
import net.milgar.sharedoc.domain.model.TermClass;
import net.milgar.sharedoc.domain.model.User;
import net.milgar.sharedoc.domain.service.SecurityService;
import net.milgar.sharedoc.domain.service.TermClassService;
import net.milgar.sharedoc.domain.service.UserService;
import org.hibernate.engine.jdbc.ReaderInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/classes")
public class TermClassesController {

    @Autowired
    TermClassService termClassService;

    @Autowired
    SecurityService securityService;

    @Autowired
    UserService userService;

    @GetMapping("")
    @Transactional
    public String index(HashMap<String, Object> modal){
        CustomUserDetails customUserDetails = (CustomUserDetails) securityService.findLoggedInUserDetails();
        User user = securityService.findLoggedInUser();
        List classes = user.getCreatedClasses().stream()
                .sorted(Comparator.comparing(TermClass::getName))
                .collect(Collectors.toList());
        modal.put("createdClasses", classes);
        return "classes_index";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model){
        return "class_create";
    }

    @PostMapping("/create")
    public String save(@RequestParam HashMap<String, Object> map){
        TermClass termClass = new TermClass();
        termClass.setName((String) map.get("name"));
        String code = termClassService.generateRandomCode();
        termClass.setCode(code);

        User user = securityService.findLoggedInUser();
        termClass.setCreator(user);
//        customUserDetails.getUser().addCreatedClass(termClass);
        termClassService.save(termClass);
        return "redirect:/classes";
    }

    @GetMapping("/join")
    public String joinForm(@RequestParam HashMap<String, Object> map){
        return "class_join_form";
    }

    @PostMapping("/join")
    public String join(@RequestParam HashMap<String, Object> map, Model model){
        Object code = map.get("code");
        if(!(code instanceof String)){
            model.addAttribute("error", "Invitation code required.");
            return "class_join_form";
        }
        TermClass termClass = termClassService.findByCode((String) code);
        if(termClass == null){
            model.addAttribute("error", "Invitation code is wrong.");
            return "class_join_form";
        }

        User user = securityService.findLoggedInUser();
        user.joinClass(termClass);
        userService.update(user);
        return "redirect:/classes/" + termClass.getId();
    }

    @RequestMapping(value = "/{termClassId:[0-9]+}", method = RequestMethod.GET)
//    public String view(@RequestParam HashMap<String, Object> map, @PathVariable long termClassId){
    public String view(Model model, @PathVariable long termClassId){
        boolean canUserSeeTermClass = termClassService.canUserSeeTermClass(termClassId, securityService.findLoggedInUser());
        if(!canUserSeeTermClass){
            return "redirect:/";
        }
        TermClass termClass = termClassService.findById(termClassId);
        List<Document> documents;
        documents = termClass.getDocuments().stream().map((Document doc) -> {
            if(doc.getContent().length() > 2000){
                doc.setContent(doc.getContent().substring(2000));
            }
            return doc;
        }).collect(Collectors.toList());

        model.addAttribute("currentTermClass", termClass);
        model.addAttribute("documents", documents);
        model.addAttribute("termClassId", termClassId);

        return "class_details";
    }

    @RequestMapping(value = "/{termClassId:[0-9]+}/createDocument", method = RequestMethod.GET)
    public String createDocument(Model model, @PathVariable long termClassId){
        TermClass termClass = termClassService.findById(termClassId);

        model.addAttribute("currentTermClass", termClass);
        model.addAttribute("newDocument", new Document());
        model.addAttribute("termClassId", termClassId);

        return "document_create";
    }

    @RequestMapping(value = "/{termClassId:[0-9]+}/students", method = RequestMethod.GET)
    public String viewStudents(Model model, @PathVariable long termClassId){
        TermClass termClass = termClassService.findById(termClassId);

        model.addAttribute("currentTermClass", termClass);
        model.addAttribute("students", termClass.getStudents());
        model.addAttribute("termClassId", termClassId);

        return "class_students";
    }
}



