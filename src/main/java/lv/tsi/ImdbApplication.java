package lv.tsi;

import lv.tsi.database.DatabaseHandler;
import lv.tsi.pages.AuthenticatedWebPage;
import lv.tsi.pages.Homepage;
import lv.tsi.pages.SignInSession;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.settings.IResourceSettings;
public class ImdbApplication extends WebApplication 
{
    public ImdbApplication() {}

    @Override
    public Class<? extends Page> getHomePage() 
    {
        return Homepage.class; //return default page
    }
    
    @Override
    public Session newSession(Request request, Response response)
    {
        return new SignInSession(request);
    }

    @Override
    protected void init()
    {
        super.init();
    	DatabaseHandler dbHandler = DatabaseHandler.instance;
    	dbHandler.databaseInit();
        IResourceSettings resourceSettings = getResourceSettings();
        resourceSettings.addResourceFolder("html");
        
        //from wicket examples:
        getSecuritySettings().setAuthorizationStrategy(new IAuthorizationStrategy()
        {
            @Override
            public boolean isActionAuthorized(Component arg0, Action arg1) 
            {
                return true;
            }

            @Override
            public <T extends Component> boolean isInstantiationAuthorized(Class<T> componentClass) 
            {
                if (AuthenticatedWebPage.class.isAssignableFrom(componentClass)) {
                    if (((SignInSession)Session.get()).isSignedIn()) {
                        return true;
                    }
                    //throw new RestartResponseAtInterceptPageException(Login.class);
                }
                return true;
            }
        });
    }
}
