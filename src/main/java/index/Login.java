package index;


import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;



public class Login extends Homepage{

	public Login() {

        add(new FeedbackPanel("feedback"));

        add(new SignInForm("signInForm"));
		
	}
    public final class SignInForm extends Form<Void>
    {
        private static final String USERNAME = "username";
        private static final String PASSWORD = "password";

        private final ValueMap properties = new ValueMap();

       
        public SignInForm(final String id)
        {
            super(id);

            // Attach textfield components that edit properties map model
            add(new TextField<String>(USERNAME, new PropertyModel<String>(properties, USERNAME)));
            add(new PasswordTextField(PASSWORD, new PropertyModel<String>(properties, PASSWORD)));
        }

        /**
         * @see org.apache.wicket.markup.html.form.Form#onSubmit()
         */
        @Override
        public final void onSubmit()
        {
            // Get session info
            SignInSession session = getMySession();

            // Sign the user in
            if (session.signIn(getUsername(), getPassword()))
            {
                continueToOriginalDestination();
                setResponsePage(getApplication().getHomePage());
                
            }
            else
            {
                // Get the error message from the properties file associated with the Component
                String errmsg = getString("loginError", null, "Unable to sign you in");

                // Register the error message with the feedback panel
                error(errmsg);
            }
        }

        private String getPassword()
        {
            return properties.getString(PASSWORD);
        }

        private String getUsername()
        {
            return properties.getString(USERNAME);
        }

        private SignInSession getMySession()
        {
            return (SignInSession)getSession();
        }
    }
}
