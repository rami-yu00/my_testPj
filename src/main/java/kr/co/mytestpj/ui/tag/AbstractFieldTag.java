package kr.co.mytestpj.ui.tag;

import javax.servlet.jsp.tagext.TagSupport;

public abstract class AbstractFieldTag extends TagSupport {
    protected String id;
    protected String field;
    protected String option;
    protected String io = "IO";

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

    protected void appendCommonAttributes(StringBuilder sb) {
        if (id != null) {
            sb.append(" id=\"").append(id).append("\"");
        }
        if (field != null) {
            sb.append(" data-field=\"").append(field).append("\"");
        }
        if (option != null) {
            sb.append(" data-option=\"").append(option).append("\"");
        }
        if (io != null) {
            sb.append(" data-io=\"").append(io).append("\"");
        }
    }
}
