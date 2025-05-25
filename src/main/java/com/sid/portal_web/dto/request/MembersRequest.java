package com.sid.portal_web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MembersRequest {

   private Date join_date;
   private Date end_date; // era mejor leavedate | Este es opcional
   private Integer rol_id; // Aqui no tiene sentido que sea un string, necesito que me pasen el id .
   private Integer development_member_id;
   private Integer title_id;

}
