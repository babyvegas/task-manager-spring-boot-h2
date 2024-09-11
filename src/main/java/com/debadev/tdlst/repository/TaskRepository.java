package com.debadev.tdlst.repository;

import com.debadev.tdlst.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Repositorio para gestionar las operaciones de la BD
public interface TaskRepository extends JpaRepository<Task,Long> {

    //Se crea nuevo metodo de busqueda para filtrar las tareas completadas
    List<Task> findByCompleted(boolean completed);
}
