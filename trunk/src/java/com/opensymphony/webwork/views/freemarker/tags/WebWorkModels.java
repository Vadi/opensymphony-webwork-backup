package com.opensymphony.webwork.views.freemarker.tags;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: plightbo
 * Date: Aug 1, 2005
 * Time: 8:41:12 PM
 */
public class WebWorkModels {
    protected OgnlValueStack stack;
    protected HttpServletRequest req;
    protected HttpServletResponse res;

    protected CheckboxListModel checkboxlist;
    protected CheckboxModel checkbox;
    protected ComboBoxModel comboBox;
    protected ComponentModel component;
    protected DoubleSelectModel doubleselect;
    protected FileModel file;
    protected FormModel form;
    protected HiddenModel hidden;
    protected LabelModel label;
    protected PasswordModel password;
    protected RadioModel radio;
    protected SelectModel select;
    protected SubmitModel submit;
    protected TextAreaModel textarea;
    protected TextFieldModel textfield;
    protected DatePickerModel datepicker;
    protected TokenModel token;
    protected WebTableModel table;
    protected URLModel url;
    protected IncludeModel include;
    protected ParamModel param;
    protected ActionModel action;

    public WebWorkModels(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        this.stack = stack;
        this.req = req;
        this.res = res;
    }

    public CheckboxListModel getCheckboxlist() {
        if (checkboxlist == null) {
            checkboxlist = new CheckboxListModel(stack, req, res);
        }

        return checkboxlist;
    }

    public CheckboxModel getCheckbox() {
        if (checkbox == null) {
            checkbox = new CheckboxModel(stack, req, res);
        }

        return checkbox;
    }

    public ComboBoxModel getComboBox() {
        if (comboBox == null) {
            comboBox = new ComboBoxModel(stack, req, res);
        }

        return comboBox;
    }

    public ComponentModel getComponent() {
        if (component == null) {
            component = new ComponentModel(stack, req, res);
        }

        return component;
    }

    public DoubleSelectModel getDoubleselect() {
        if (doubleselect == null) {
            doubleselect = new DoubleSelectModel(stack, req, res);
        }

        return doubleselect;
    }

    public FileModel getFile() {
        if (file == null) {
            file = new FileModel(stack, req, res);
        }

        return file;
    }

    public FormModel getForm() {
        if (form == null) {
            form = new FormModel(stack, req, res);
        }

        return form;
    }

    public HiddenModel getHidden() {
        if (hidden == null) {
            hidden = new HiddenModel(stack, req, res);
        }

        return hidden;
    }

    public LabelModel getLabel() {
        if (label == null) {
            label = new LabelModel(stack, req, res);
        }

        return label;
    }

    public PasswordModel getPassword() {
        if (password == null) {
            password = new PasswordModel(stack, req, res);
        }

        return password;
    }

    public RadioModel getRadio() {
        if (radio == null) {
            radio = new RadioModel(stack, req, res);
        }

        return radio;
    }

    public SelectModel getSelect() {
        if (select == null) {
            select = new SelectModel(stack, req, res);
        }

        return select;
    }

    public SubmitModel getSubmit() {
        if (submit == null) {
            submit = new SubmitModel(stack, req, res);
        }

        return submit;
    }

    public TextAreaModel getTextarea() {
        if (textarea == null) {
            textarea = new TextAreaModel(stack, req, res);
        }

        return textarea;
    }

    public TextFieldModel getTextfield() {
        if (textfield == null) {
            textfield = new TextFieldModel(stack, req, res);
        }

        return textfield;
    }

    public DatePickerModel getDatepicker() {
        if (datepicker == null) {
            datepicker = new DatePickerModel(stack, req, res);
        }

        return datepicker;
    }

    public TokenModel getToken() {
        if (token == null) {
            token = new TokenModel(stack, req, res);
        }

        return token;
    }

    public WebTableModel getTable() {
        if (table == null) {
            table = new WebTableModel(stack, req, res);
        }

        return table;
    }

    public URLModel getUrl() {
        if (url == null) {
            url = new URLModel(stack, req, res);
        }

        return url;
    }

    public IncludeModel getInclude() {
        if (include == null) {
            include = new IncludeModel(stack, req, res);
        }

        return include;
    }

    public ParamModel getParam() {
        if (param == null) {
            param = new ParamModel();
        }

        return param;
    }

    public ActionModel getAction() {
        if (action == null) {
            action = new ActionModel(stack, req, res);
        }

        return action;
    }
}
