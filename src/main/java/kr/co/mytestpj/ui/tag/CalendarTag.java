package kr.co.mytestpj.ui.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class CalendarTag extends AbstractFieldTag {
    private String format;
    private String value;
    private String placeholder;

    public void setFormat(String format) {
        this.format = format;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public int doStartTag() throws JspException {
        StringBuilder sb = new StringBuilder();
        sb.append("<span class=\"cbui-calendar\"");
        appendCommonAttributes(sb);
        if (format != null) {
            sb.append(" data-format=\"").append(format).append("\"");
        }
        sb.append(">");
        sb.append("<input type=\"hidden\" class=\"cbui-calendar-value\"");
        if (field != null) {
            sb.append(" name=\"").append(field).append("\"");
        }
        if (value != null) {
            sb.append(" value=\"").append(value).append("\"");
        }
        sb.append(" />");
        sb.append("<input type=\"text\" class=\"cbui-calendar-display\"");
        if (value != null) {
            sb.append(" value=\"").append(value).append("\"");
        }
        if (placeholder != null) {
            sb.append(" placeholder=\"").append(placeholder).append("\"");
        }
        sb.append(" />");
        sb.append("</span>");

        try {
            pageContext.getOut().write(sb.toString());
        } catch (Exception e) {
            throw new JspException(e);
        }
        return TagSupport.SKIP_BODY;
    }
}
