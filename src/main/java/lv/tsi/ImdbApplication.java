package lv.tsi;

import index.AuthenticatedWebPage;
import index.Homepage;
import index.SignInSession;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.protocol.http.WebApplication;
public class ImdbApplication extends WebApplication {

	public ImdbApplication(){}
	@Override
	public Class<? extends Page> getHomePage() {
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
        
        // Register the authorization strategy
        getSecuritySettings().setAuthorizationStrategy(new IAuthorizationStrategy()
        {

			@Override
			public boolean isActionAuthorized(Component arg0, Action arg1) {
				return true;
			}

			@Override
			public <T extends Component> boolean isInstantiationAuthorized(
					Class<T> componentClass) {
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
                    return true;
                    //throw new RestartResponseAtInterceptPageException(Login.class);
                }
				
                // okay to proceed
                return true;
			}
            
        });
        
    }
	

}
