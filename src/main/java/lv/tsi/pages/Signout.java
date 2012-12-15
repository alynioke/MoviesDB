package lv.tsi.pages;

import org.apache.wicket.PageParameters;

public class Signout extends Homepage
{
    public Signout(final PageParameters parameters)
    {
        getSession().invalidate();
    }
}