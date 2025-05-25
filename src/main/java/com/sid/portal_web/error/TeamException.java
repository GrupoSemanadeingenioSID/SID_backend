package com.sid.portal_web.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TeamException extends RuntimeException {

  private final String messageError;
  private final HttpStatus httpStatus;

    private TeamException(String message, HttpStatus httpStatus) {
        super(message);
        this.messageError = message;
        this.httpStatus = httpStatus;
    }

    public static TeamException teamNotFound(Number id) {
      return new TeamException("Team not found with id : "+id, HttpStatus.NOT_FOUND);
    }
    public static TeamException errorCreatingTeam() {
      return new TeamException("Error creating team",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
