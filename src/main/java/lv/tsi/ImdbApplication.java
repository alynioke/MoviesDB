package lv.tsi;

import index.AuthenticatedWebPage;
import index.Homepage;
import index.Login;
import index.SignInSession;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.ServerAndClientTimeFilter;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.component.IRequestableComponent;
public class ImdbApplication extends WebApplication {

	public ImdbApplication(){}
	@Override
	public Class<? extends Page> getHomePage() {
        System.out.println('b');
		return Homepage.class; //return default page
	}
	

   @Override
    public Session newSession(Request request, Response response)
    {
        return new SignInSession(request);
    }

    /**
     * @see org.apache.wicket.examples.WicketExampleApplication#init()
     */
    @Override
    protected void init()
    {
        super.init();

        System.out.println('a');
        // Register the authorization strategy
        getSecuritySettings().setAuthorizationStrategy(new IAuthorizationStrategy()
        {
            public boolean isActionAuthorized(Component component, Action action)
            {
                // authorize everything
                return true;
            }
            
            public <T extends IRequestableComponent> boolean isInstantiationAuthorized(
                Class<T> componentClass)
            {
            	/*
                System.out.println('b');
                // Check if the new Page requires authentication (implements the marker interface)
                if (AuthenticatedWebPage.class.isAssignableFrom(componentClass))
                {
                    // Is user signed in?
                    if (((SignInSession)Session.get()).isSignedIn())
                    {
                        // okay to proceed
                        return true;
                    }

                    // Intercept the request, but remember the target for later.
                    // Invoke Component.continueToOriginalDestination() after successful logon to
                    // continue with the target remembered.

                    throw new RestartResponseAtInterceptPageException(Login.class);
                }
				*/
                // okay to proceed
                return true;
            }
        });
    }
	

}
