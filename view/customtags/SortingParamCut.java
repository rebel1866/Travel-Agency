package by.epamtc.stanislavmelnikov.view.customtags;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SortingParamCut extends TagSupport {
    private String queryStr;
    private static final String regex = "&sorting=\\w+&sortingOrder=(asc|desc)";

    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }

    public int doStartTag() {
        JspWriter out = pageContext.getOut();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(queryStr);
        StringBuilder parsedQueryString = new StringBuilder(queryStr);
        if (matcher.find()) {
            parsedQueryString.delete(matcher.start(), matcher.end());
        }
        try {
            out.print(parsedQueryString);
        } catch (IOException e) {
            Logger logger = LogManager.getLogger(PageParamCut.class);
            logger.error(e.getMessage(), e);
        }
        return SKIP_BODY;
    }
}
