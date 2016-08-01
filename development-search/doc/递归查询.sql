SELECT id AS ID,pid AS 父ID ,levels AS 父到子之间级数, paths AS 父到子路径 FROM (
     SELECT id,pid,
     @le:= IF (pid = 0 ,0,  
         IF( LOCATE( CONCAT('|',pid,':'),@pathlevel)   > 0  ,      
                  SUBSTRING_INDEX( SUBSTRING_INDEX(@pathlevel,CONCAT('|',pid,':'),-1),'|',1) +1
        ,@le+1) ) levels
     , @pathlevel:= CONCAT(@pathlevel,'|',id,':', @le ,'|') pathlevel
      , @pathnodes:= IF( pid =0,',0', 
           CONCAT_WS(',',
           IF( LOCATE( CONCAT('|',pid,':'),@pathall) > 0  , 
               SUBSTRING_INDEX( SUBSTRING_INDEX(@pathall,CONCAT('|',pid,':'),-1),'|',1)
              ,@pathnodes ) ,pid  ) )paths
    ,@pathall:=CONCAT(@pathall,'|',id,':', @pathnodes ,'|') pathall 
        FROM  tb_basic_menu, 
    (SELECT @le:=0,@pathlevel:='', @pathall:='',@pathnodes:='') vv
    ORDER BY  pid,id
    ) src
