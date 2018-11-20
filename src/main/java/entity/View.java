package entity;

import service.ViewService;

import javax.validation.constraints.NotNull;

import static service.ViewService.referencesFile;

public class View {

    /** Is forward() used */
    private boolean forwarded = false;
    /** Is sendRedirect() used */
    private boolean redirected = true;
    /** Is file in WEB-INF */
    private boolean pathClosed = false;

    @NotNull private String path;

    public View(){ }

    public View(boolean forwarded){ setForwarded(forwarded); }

    public boolean isForwarded() { return forwarded; }

    public boolean isRedirected() { return redirected; }

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

    public boolean isPathClosed() { return pathClosed; }

    public void setPathClosed(boolean pathClosed) {this.pathClosed = pathClosed; }
}
