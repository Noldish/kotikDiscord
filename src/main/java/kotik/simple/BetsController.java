package kotik.simple;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Roman_Kuznetcov on 01.12.2016.
 */
@Controller
public class BetsController {

    @RequestMapping(value = "/newwager", method = RequestMethod.GET)
    public String showNewWagerForm(ModelAndView model) {
        return "newwager";
    }

    @RequestMapping(value = "/newwager", method = RequestMethod.POST)
    public String addNewWager(HttpServletRequest request) {
        request.getParameter();
        model.addAttribute("name", student.getName());
        model.addAttribute("age", student.getAge());
        model.addAttribute("id", student.getId());
        return "addedwager";
    }

}
