package lv.tsi.pages;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.Model;

public class WebImage extends WebComponent 
{
    public WebImage(String id, String url) 
    {
        super(id, new Model<String>(url));
    }

    protected void onComponentTag(ComponentTag tag) 
    {
        super.onComponentTag(tag);
        checkComponentTag(tag, "img");
        tag.put("src", getDefaultModelObjectAsString());
    }
}