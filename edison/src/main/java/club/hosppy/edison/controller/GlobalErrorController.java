package club.hosppy.edison.controller;

import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class GlobalErrorController implements ErrorController {

    private static final ILogger logger = SLoggerFactory.getLogger(GlobalErrorController.class);

    @RequestMapping
    public String handleError(HttpServletRequest request, Model model) {
        return "error";
    }
}
