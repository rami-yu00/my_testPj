package kr.co.mytestpj.ui.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class ComboTag extends TagSupport {
    private String id;
    private String field;
    private String option;
    private String io = "IO";
    private String value;
    private String label;
    private String codebase;
    private String cssClass;

    @Override
    public int doStartTag() throws JspException {
        StringBuilder sb = new StringBuilder();
        String widgetId = id != null ? id : (field != null ? field + "_combo" : null);

        sb.append("<span");
        if (widgetId != null) {
            sb.append(" id=\"").append(escape(widgetId)).append("\"");
        }
        sb.append(" class=\"cb-widget cb-combo");
        if (cssClass != null && !cssClass.isEmpty()) {
            sb.append(' ').append(escape(cssClass));
        }
        sb.append("\"");

        if (field != null) {
            sb.append(" data-field=\"").append(escape(field)).append("\"");
        }
        if (option != null) {
            sb.append(" data-option=\"").append(escape(option)).append("\"");
        }
        if (io != null) {
            sb.append(" data-io=\"").append(escape(io)).append("\"");
        }
        if (codebase != null) {
            sb.append(" data-codebase=\"").append(escape(codebase)).append("\"");
        }
        sb.append(">");

        if (field != null) {
            sb.append("<input type=\"hidden\" name=\"")
                    .append(escape(field))
                    .append("\" value=\"")
                    .append(value != null ? escape(value) : "")
                    .append("\" />");
        }

        sb.append("<span class=\"cb-combo-label\">")
                .append(label != null ? escape(label) : (value != null ? escape(value) : ""))
                .append("</span>");
        sb.append("<span class=\"cb-combo-arrow\">&#9662;</span>");

        sb.append("</span>");

        try {
            pageContext.getOut().write(sb.toString());
        } catch (IOException e) {
            throw new JspException("Failed to render combo widget", e);
        }

        return SKIP_BODY;
    }

    private String escape(String text) {
        return text.replace("&", "&amp;")
                .replace("\"", "&quot;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public void setIo(String io) {
        this.io = io;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setCodebase(String codebase) {
        this.codebase = codebase;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }
}
