package net.milgar.sharedoc.domain.web;

import net.milgar.sharedoc.domain.model.Document;
import net.milgar.sharedoc.domain.model.TermClass;
import net.milgar.sharedoc.domain.model.User;
import net.milgar.sharedoc.domain.service.DocumentService;
import net.milgar.sharedoc.domain.service.SecurityService;
import net.milgar.sharedoc.domain.service.TermClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private TermClassService termClassService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/{documentId:[0-9]*}", method = RequestMethod.GET)
    public String view(Model model, @PathVariable long documentId){

        Document document = documentService.findById(documentId);
        model.addAttribute("document", document);
        model.addAttribute("currentTermClass", document.getTermClass());


        return "document";
    }

    @PostMapping("/create")
    public String create(@RequestParam HashMap<String, Object> map, Model model){
        Document doc = new Document();
        doc.setTitle((String) map.get("title"));
        doc.setContent((String) map.get("content"));
        doc.setTermClass(termClassService.findById(Long.parseLong((String) map.get("termClassId"))));
        User user = securityService.findLoggedInUser();
        doc.setPosterUser(user);
        documentService.save(doc);

        return "redirect:/document/" + doc.getId();
    }
}
