package com.easyexam.apps.dao;

import com.easyexam.apps.entity.ExaminationRoom;
import io.swagger.models.auth.In;
import net.bytebuddy.description.field.FieldDescription;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExaminationRoomDao {
    public List<ExaminationRoom>findAllExaminationRoom(@Param(value = "subjectId") Integer subjectId,
      @Param("invigilateId")Integer invigilateId ,@Param("paperId")Integer paperId ,@Param(value = "info") String info);
    public ExaminationRoom findOneExaminationRoom( Integer id);
    public void  updateExaminationRoom(ExaminationRoom room);
    public void  deleteExaminationRoom(Integer id);
}
