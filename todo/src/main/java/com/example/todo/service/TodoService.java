package com.example.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.todo.dto.PageRequestDTO;
import com.example.todo.dto.PageResultDTO;
import com.example.todo.dto.TodoDTO;
import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Transactional
@Log4j2
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    public Long create(TodoDTO dto) {
        Todo todo = modelMapper.map(dto, Todo.class);

        // dto => entity
        return todoRepository.save(todo).getId();
    }

    public Long update(TodoDTO dto) {
        Todo todo = todoRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
        todo.changeTitle(dto.getTitle());
        todo.changeCompleted(dto.isCompleted());
        todo.changeImportant(dto.isImportant());

        return todo.getId();
    }

    public void delete(Long id) {
        todoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PageResultDTO<TodoDTO> findCompletedTodos(Boolean completed, PageRequestDTO dto) {
        Page<Todo> result = null;

        // react 요청 => react 페이지 나누기 라이브러리 사용(-1을 화면단에서 처리해줌)
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize(), Sort.by("id").descending());

        if (completed == null) {
            result = todoRepository.findAll(pageable);
        } else {
            result = todoRepository.findByCompleted(completed, pageable);
        }

        // entity => dto
        List<TodoDTO> dtoList = result.stream().map(todo -> modelMapper.map(todo, TodoDTO.class))
                .collect(Collectors.toList());

        Long totalCount = result.getTotalElements();

        return PageResultDTO.<TodoDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(dto)
                .build();
    }

    @Transactional(readOnly = true)
    public List<TodoDTO> findImportantTodos(boolean important) {
        List<Todo> result = todoRepository.findByImportant(important);
        // entity => dto
        return result.stream().map(todo -> modelMapper.map(todo, TodoDTO.class)).collect(Collectors.toList());
    }

}
