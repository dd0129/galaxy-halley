package com.dianping.data.warehouse.core.dao;

import com.dianping.data.warehouse.halley.domain.TaskRelaDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TaskRelaDAO {

    public List<TaskRelaDO> getTaskRelaByID(int id);

    public void insertTaskRela(List<TaskRelaDO> list);

    public void deleteTaskRela(int id);

    public List<TaskRelaDO> getAllTaskRelations();

}
