package kr.co.mytestpj.ui.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class RadioTag extends AbstractFieldTag {
    private String codebase;

    public void setCodebase(String codebase) {
        this.codebase = codebase;
    }

    @Override
    public int doStartTag() throws JspException {
        StringBuilder sb = new StringBuilder();
        sb.append("<span class=\"cbui-radio\"");
        appendCommonAttributes(sb);
        if (codebase != null) {
            sb.append(" data-codebase=\"").append(codebase).append("\"");
        }
        sb.append(">");
        sb.append("<input type=\"hidden\" class=\"cbui-radio-value\"");
        if (field != null) {
            sb.append(" name=\"").append(field).append("\"");
        }
        sb.append(" />");
        sb.append("<div class=\"cbui-radio-options\"></div>");
        sb.append("</span>");

        try {
            pageContext.getOut().write(sb.toString());
        } catch (Exception e) {
            throw new JspException(e);
        }
        return TagSupport.SKIP_BODY;
    }
}
