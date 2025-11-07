package guat.tsdrs.mapper;

import guat.tsdrs.pojo.AnalysisHistory;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface AnalysisHistoryMapper {
    
    @Select("<script>" +
            "SELECT file_name, file_type, analysis_result, status, created_at " +
            "FROM analysis_history " +
            "<where> " +
            "<if test='startTime != null'> " +
            "AND created_at &gt;= #{startTime} " +
            "</if> " +
            "<if test='endTime != null'> " +
            "AND created_at &lt;= #{endTime} " +
            "</if> " +
            "And username = #{username}" +
            "</where> " +
            "ORDER BY created_at DESC" +
            "</script>")
    @Results({
            @Result(column = "file_name", property = "fileName"),
            @Result(column = "file_type", property = "fileType"),
            @Result(column = "analysis_result", property = "analysisResult"),
            @Result(column = "created_at", property = "createdAt")
    })
    List<AnalysisHistory> findByTimeRange(@Param("startTime") String startTime, @Param("endTime") String endTime,
                                          @Param("username") String username);

    @Delete("DELETE FROM analysis_history WHERE file_name = #{fileName}")
    int deleteByFileName(String fileName);

    @Delete("<script>" +
            "DELETE FROM analysis_history WHERE file_name IN " +
            "<foreach collection='fileNames' item='fileName' open='(' separator=',' close=')'>" +
            "#{fileName}" +
            "</foreach>" +
            "</script>")
    int batchDelete(@Param("fileNames") List<String> fileNames);

    @Insert("INSERT INTO analysis_history (file_name, file_type, analysis_result, status, created_at, username) " +
            "VALUES (#{fileName}, #{fileType}, #{analysisResult}, 1, NOW(), #{username})")
    int insert(@Param("fileName") String fileName, 
              @Param("fileType") String fileType, 
              @Param("analysisResult") String analysisResult,
               @Param("username") String username);
} 