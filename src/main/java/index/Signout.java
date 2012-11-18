package index;

import org.apache.wicket.request.mapper.parameter.PageParameters;


public class Signout extends Homepage
{
    
    public Signout(final PageParameters parameters)
    {
        getSession().invalidate();
    }
}