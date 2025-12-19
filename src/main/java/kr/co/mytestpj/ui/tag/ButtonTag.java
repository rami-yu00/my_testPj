package kr.co.mytestpj.ui.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class ButtonTag extends AbstractFieldTag {
    private String text;
    private String type = "button";

    public void setText(String text) {
        this.text = text;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int doStartTag() throws JspException {
        StringBuilder sb = new StringBuilder();
        sb.append("<button");
        sb.append(" class=\"cbui-button\"");
        appendCommonAttributes(sb);
        sb.append(" type=\"").append(type == null ? "button" : type).append("\"");
        sb.append(">");
        if (text != null) {
            sb.append(text);
        } else {
            sb.append("Button");
        }
        sb.append("</button>");

        try {
            pageContext.getOut().write(sb.toString());
        } catch (Exception e) {
            throw new JspException(e);
        }
        return TagSupport.SKIP_BODY;
    }
}
