package entity;

import javax.validation.constraints.NotNull;
import java.util.HashMap;

public class View {

    /** Is forward() used */
    private boolean forwarded = false;
    /** Is sendRedirect() used */
    private boolean redirected = true;
    /** Is file in WEB-INF */
    private boolean pathClosed = false;
    /** Status for response */
    private Integer status;
    /** Attributes, that contain error messages */
    private HashMap<String, String> errorAttributes = new HashMap<>();
    /** Attributes, that contain session important data */
    private HashMap<String, Object> sessionAttributes = new HashMap<>();
    /** Attributes, that are temporary stored to use them in jsp files */
    private HashMap<String, Object> requestAttributes = new HashMap<>();

    @NotNull private String path;

    public View(){ }

    public View(boolean forwarded){ setForwarded(forwarded); }

    public boolean isForwarded() { return forwarded; }

    public boolean isRedirected() { return redirected; }

    public boolean isPathClosed() { return pathClosed; }

    public Integer getStatus(){ return this.status; }

    public HashMap<String, String> getErrorAttributes() { return this.errorAttributes; }

    public String getErrorAttributeValue(String key){ return this.errorAttributes.get(key); }

    public HashMap<String, Object> getSessionAttributes() { return this.sessionAttributes; }

    public Object getSessionAttribute(String key){ return this.sessionAttributes.get(key); }

    public HashMap<String, Object> getRequestAttributes(){ return this.requestAttributes; }

    public Object getRequestAttribute(String key){ return this.requestAttributes.get(key); }

    public void setRedirected(boolean redirected) {
        this.redirected = redirected;
        this.forwarded = !redirected;
    }

    public String getPath() { return path; }

    public void setPath(String path) { this.path = path; }

    public void setForwarded(boolean forwarded) {
        this.forwarded = forwarded;
        this.redirected = !forwarded;
    }

    public void setPathClosed(boolean pathClosed) {this.pathClosed = pathClosed; }

    public void setStatus(int status){ this.status = status; }

    public void putErrorAttribute(String attr, String value){
        this.errorAttributes.put(attr, value);
    }

    public void putSessionAttribute(String attr, Object value){
        this.sessionAttributes.put(attr, value);
    }

    public void putRequestAttribute(String attr, Object value){
        this.requestAttributes.put(attr, value);
    }
}
