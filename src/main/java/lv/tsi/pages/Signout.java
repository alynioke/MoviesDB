package lv.tsi.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;

public class Signout extends Homepage
{
    public Signout(final PageParameters parameters)
    {
        getSession().invalidate();
        addOrReplace(new Label("welcome", ""));
        addOrReplace(new Label("signout", ""));
    }
}