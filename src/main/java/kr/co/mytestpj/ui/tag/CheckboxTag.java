package kr.co.mytestpj.ui.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class CheckboxTag extends AbstractFieldTag {
    private String value = "N";
    private String label;

    public void setValue(String value) {
        this.value = value;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public int doStartTag() throws JspException {
        StringBuilder sb = new StringBuilder();
        sb.append("<span class=\"cbui-checkbox\"");
        appendCommonAttributes(sb);
        sb.append(">");
        sb.append("<input type=\"hidden\" class=\"cbui-checkbox-value\"");
        if (field != null) {
            sb.append(" name=\"").append(field).append("\"");
        }
        if (value != null) {
            sb.append(" value=\"").append(value).append("\"");
        }
        sb.append(" />");
        sb.append("<label class=\"cbui-checkbox-label\">");
        sb.append("<input type=\"checkbox\" class=\"cbui-checkbox-control\" /> ");
        if (label != null) {
            sb.append(label);
        } else {
            sb.append("체크");
        }
        sb.append("</label>");
        sb.append("</span>");

        try {
            pageContext.getOut().write(sb.toString());
        } catch (Exception e) {
            throw new JspException(e);
        }
        return TagSupport.SKIP_BODY;
    }
}
