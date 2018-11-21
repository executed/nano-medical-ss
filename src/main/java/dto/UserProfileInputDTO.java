package dto;

import javax.servlet.http.HttpServletRequest;

public interface UserProfileInputDTO extends UserProfileDTO{
    void decompose(HttpServletRequest request);
}
