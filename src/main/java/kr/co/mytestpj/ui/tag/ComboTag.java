package kr.co.mytestpj.ui.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class ComboTag extends AbstractFieldTag {
    private String codebase;
    private String value;
    private String label;

    public void setCodebase(String codebase) {
        this.codebase = codebase;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public int doStartTag() throws JspException {
        StringBuilder sb = new StringBuilder();
        sb.append("<span class=\"cbui-combo\"");
        appendCommonAttributes(sb);
        if (codebase != null) {
            sb.append(" data-codebase=\"").append(codebase).append("\"");
        }
        sb.append(">");
        sb.append("<input type=\"hidden\" class=\"cbui-combo-value\"");
        if (field != null) {
            sb.append(" name=\"").append(field).append("\"");
        }
        if (value != null) {
            sb.append(" value=\"").append(value).append("\"");
        }
        sb.append(" />");
        sb.append("<span class=\"cbui-combo-label\">");
        if (label != null) {
            sb.append(label);
        } else {
            sb.append("선택");
        }
        sb.append("</span>");
        sb.append("<button type=\"button\" class=\"cbui-combo-toggle\">▼</button>");
        sb.append("<ul class=\"cbui-combo-menu\"></ul>");
        sb.append("</span>");

        try {
            pageContext.getOut().write(sb.toString());
        } catch (Exception e) {
            throw new JspException(e);
        }
        return TagSupport.SKIP_BODY;
    }
}
