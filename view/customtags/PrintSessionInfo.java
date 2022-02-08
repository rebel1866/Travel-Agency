package by.epamtc.stanislavmelnikov.view.customtags;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PrintSessionInfo extends TagSupport {
    private String arg;

    public void setArg(String arg) {
        this.arg = arg;
    }

    public int doStartTag() {
        JspWriter out = pageContext.getOut();
        HttpSession session = pageContext.getSession();
        Object attr = session.getAttribute(arg);
        if (attr == null) attr = " ";
        String value = String.valueOf(attr);
        try {
            out.print(value);
        } catch (IOException e) {
            Logger logger = LogManager.getLogger(PrintSessionInfo.class);
            logger.error(e.getMessage(), e);
        }
        session.removeAttribute(arg);
        return SKIP_BODY;
    }
}