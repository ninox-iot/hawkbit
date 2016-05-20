package org.eclipse.hawkbit.ui.common;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.hawkbit.ui.components.SPUIComponentProvider;
import org.eclipse.hawkbit.ui.decorators.SPUIButtonStyleBorderWithIcon;
import org.eclipse.hawkbit.ui.utils.SPUIComponetIdProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Service
public class CommonDialogWindow extends Window {

    private static final long serialVersionUID = -1321949234316858703L;

    private static final Logger LOG = LoggerFactory.getLogger(CommonDialogWindow.class);

    private final VerticalLayout mainLayout = new VerticalLayout();

    private String caption;

    private Component content;

    private String helpLink;

    private Button saveButton;

    private Button cancelButton;

    public CommonDialogWindow() {

        init(null, null);
    }

    public CommonDialogWindow(final String caption, final Component content, final String helpLink,
            final ClickListener saveButtonClickListener, final ClickListener cancelButtonClickListener) {

        this.caption = caption;
        this.content = content;
        this.helpLink = helpLink;

        if (null == content) {
            // TODO
            // throw Exception
        }
        init(saveButtonClickListener, cancelButtonClickListener);
    }

    public void init(final ClickListener saveButtonClickListener, final ClickListener cancelButtonClickListener) {

        if (content instanceof AbstractOrderedLayout) {
            ((AbstractOrderedLayout) content).setSpacing(true);
            ((AbstractOrderedLayout) content).setMargin(true);
        }

        if (StringUtils.isNotEmpty(helpLink)) {
            mainLayout.addComponent(createLinkToHelp(helpLink));
        }
        if (null != content) {
            mainLayout.addComponent(content);
        }
        final HorizontalLayout buttonLayout = createActionButtonsLayout(saveButtonClickListener,
                cancelButtonClickListener);
        mainLayout.addComponent(buttonLayout);
        mainLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);

        setHeight("60%");
        setWidth("50%");
        setCaption(caption);
        setContent(mainLayout);
        setResizable(true);
        center();
        setModal(true);
    }

    private HorizontalLayout createActionButtonsLayout(final ClickListener saveButtonClickListener,
            final ClickListener cancelButtonClickListener) {

        final HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.setSpacing(true);

        saveButton = SPUIComponentProvider.getButton(SPUIComponetIdProvider.SYSTEM_CONFIGURATION_SAVE, "save", "", "",
                true, FontAwesome.SAVE, SPUIButtonStyleBorderWithIcon.class);
        if (null != saveButtonClickListener) {
            saveButton.addClickListener(saveButtonClickListener);
        } else {
            LOG.warn("No ClickListener for saveButton specified");
        }
        hlayout.addComponent(saveButton);
        hlayout.setComponentAlignment(saveButton, Alignment.MIDDLE_LEFT);

        cancelButton = SPUIComponentProvider.getButton(SPUIComponetIdProvider.SYSTEM_CONFIGURATION_CANCEL, "cancel", "",
                "", true, FontAwesome.TIMES, SPUIButtonStyleBorderWithIcon.class);
        if (null != cancelButtonClickListener) {
            cancelButton.addClickListener(cancelButtonClickListener);
        } else {
            LOG.warn("No ClickListener for cancelButton specified");
        }
        hlayout.addComponent(cancelButton);
        hlayout.setComponentAlignment(cancelButton, Alignment.MIDDLE_RIGHT);
        hlayout.addStyleName("actionButtonsMargin");

        return hlayout;
    }

    public void setSaveButtonEnabled(final boolean enabled) {
        saveButton.setEnabled(enabled);
    }

    public void setCancelButtonEnabled(final boolean enabled) {
        cancelButton.setEnabled(enabled);
    }

    private Link createLinkToHelp(final String link) {
        return SPUIComponentProvider.getHelpLink(link);
    }

}
