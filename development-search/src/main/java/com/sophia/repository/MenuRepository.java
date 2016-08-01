package com.sophia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sophia.domain.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String>{
	
	@Query(value="SELECT paths FROM ( "+
		     " SELECT id,pid,link,  "+
		     " @le\\:= IF (pid = 0 ,0,    "+
		     "    IF( LOCATE( CONCAT('|',pid,':'),@pathlevel)   > 0  ,      "+ 
		     "             SUBSTRING_INDEX( SUBSTRING_INDEX(@pathlevel,CONCAT('|',pid,':'),-1),'|',1) +1 "+
		     "   ,@le+1) ) levels "+
		     ", @pathlevel\\:= CONCAT(@pathlevel,'|',id,':', @le ,'|') pathlevel "+
		     " , @pathnodes\\:= IF( pid = '0',name,  "+
		     "      CONCAT_WS(',', "+
		     "      IF( LOCATE( CONCAT('|',pid,':'),@pathall) > 0  ,  "+
		     "          SUBSTRING_INDEX( SUBSTRING_INDEX(@pathall,CONCAT('|',pid,':'),-1),'|',1)  "+
		     "         ,@pathnodes ) ,name  ) )paths "+
		    ",@pathall\\:=CONCAT(@pathall,'|',id,':', @pathnodes ,'|') pathall  "+
		    "FROM  tb_basic_menu,  "+
		    "(SELECT @le\\:=0,@pathlevel\\:='', @pathall\\:='',@pathnodes\\:='') vv "+
		    "where pid <> '-1' "+
		    "ORDER BY  pid,id ) src where link = ?1 ",nativeQuery=true)
	public String getPath(String link);
	
}
