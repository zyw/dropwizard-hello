package cn.c5cn.dropwizard.views;

import cn.c5cn.dropwizard.core.Contact;
import io.dropwizard.views.View;

/**
 * Created by ZYW on 2014/6/20.
 */
public class ContactView extends View {

    private final Contact contact;

    public ContactView(Contact contact) {
        super("/views/contact.mustache");
        this.contact = contact;
    }

    public Contact getContact(){
        return contact;
    }
}
