package kr.co.mytestpj.ui.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class TextareaTag extends AbstractFieldTag {
    private String value;
    private String placeholder;
    private int rows = 3;

    public void setValue(String value) {
        this.value = value;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public int doStartTag() throws JspException {
        StringBuilder sb = new StringBuilder();
        sb.append("<span class=\"cbui-textarea\"");
        appendCommonAttributes(sb);
        sb.append(">");
        sb.append("<textarea class=\"cbui-textarea-control\"");
        if (field != null) {
            sb.append(" name=\"").append(field).append("\"");
        }
        sb.append(" rows=\"").append(rows).append("\"");
        if (placeholder != null) {
            sb.append(" placeholder=\"").append(placeholder).append("\"");
        }
        sb.append(">");
        if (value != null) {
            sb.append(value);
        }
        sb.append("</textarea>");
        sb.append("</span>");

        try {
            pageContext.getOut().write(sb.toString());
        } catch (Exception e) {
            throw new JspException(e);
        }
        return TagSupport.SKIP_BODY;
    }
}
