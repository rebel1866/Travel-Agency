package by.epamtc.stanislavmelnikov.view.customtags;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageParamCut extends TagSupport {
    private String queryStr;
    private static final String regex = "&page=\\d+?";
    private static final String regexEx = "&page=\\w*";

    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }

    public int doStartTag() {
        JspWriter out = pageContext.getOut();
        Pattern patEx = Pattern.compile(regexEx);
        Matcher matcherEx = patEx.matcher(queryStr);
        if(matcherEx.find()){
            StringBuilder parsedQueryString = new StringBuilder(queryStr);
            parsedQueryString.delete(matcherEx.start(), matcherEx.end());
            try {
                out.print(parsedQueryString);
            } catch (IOException e) {
                Logger logger = LogManager.getLogger(PageParamCut.class);
                logger.error(e.getMessage(), e);
            }
            return SKIP_BODY;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(queryStr);
        if (matcher.find()) {
            StringBuilder parsedQueryString = new StringBuilder(queryStr);
            parsedQueryString.delete(matcher.start(), matcher.end());
            try {
                out.print(parsedQueryString);
            } catch (IOException e) {
                Logger logger = LogManager.getLogger(PageParamCut.class);
                logger.error(e.getMessage(), e);
            }
        }
        return SKIP_BODY;
    }
}
