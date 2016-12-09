package kotik.simple;

        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Roman_Kuznetcov on 01.12.2016.
 */
@Controller
public class AudioPlayerController {

    @RequestMapping("/audioplayer")
    public ModelAndView greeting(ModelAndView model) {
        model.setViewName("audioplayer.jsp");
        return model;
    }

}
