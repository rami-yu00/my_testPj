package kr.co.mytestpj.ui.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class RealGridTag extends AbstractFieldTag {
    @Override
    public int doStartTag() throws JspException {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"cbui-realgrid\"");
        appendCommonAttributes(sb);
        sb.append(">");
        sb.append("<table class=\"cbui-realgrid-table\">");
        sb.append("<thead><tr><th>선택</th><th>필드</th><th>값</th></tr></thead>");
        sb.append("<tbody></tbody>");
        sb.append("</table>");
        sb.append("</div>");
        try {
            pageContext.getOut().write(sb.toString());
        } catch (Exception e) {
            throw new JspException(e);
        }
        return TagSupport.SKIP_BODY;
    }
}
