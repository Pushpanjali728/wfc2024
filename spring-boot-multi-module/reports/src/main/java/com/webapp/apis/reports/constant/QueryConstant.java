package com.webapp.apis.reports.constant;

/**
 * @author Mairaj
 * @copyright 
 * @email 
 * @date 14-May-2022
 * @purpose This is the interface for Queries in  Project.
 */
public interface QueryConstant {
  String STOCK_VERIFICATION =
      "SELECT SCREEN_B.SCREEN_ID,SCREEN_B.SCREEN_NAME,SCREEN_PLANT_PROCESS_MAP.SCREEN_PLAN_PROCESS_MAP_ID FROM PLANT_PROCESS_UNIT_MAP INNER JOIN SCREEN_PLANT_PROCESS_MAP on PLANT_PROCESS_UNIT_MAP.PLANT_PROCESS_UNIT_ID = SCREEN_PLANT_PROCESS_MAP.PLANT_PROCESS_UNIT_ID INNER JOIN SCREEN_B on SCREEN_B.SCREEN_ID = SCREEN_PLANT_PROCESS_MAP.SCREEN_ID WHERE PLANT_ID = ?1  And PROCESS_ID = ?2  ";
 
  String GET_BY_HEATNO = "select *from SCHEDULE_DETAIL where HEAT_NO=:heatNo";
}
