package com.example.svt2023sr50.services;

import com.example.svt2023sr50.model.Group;
import com.example.svt2023sr50.model.Post;
import com.example.svt2023sr50.repository.GroupRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class GroupService {

    public final GroupRepository repository;


    public List<Group> getAll() {
        return repository.findAll();
    }
    public Group getGroup(Long id) {
        return repository.findById(id).get();
    }
    @Transactional
    public Group save(Group group) {
        return repository.save(group);
    }
    public Group getGroupByPost(Post post) {
        List<Group> groups = getAll();
        for (Group group : groups) {
            List<Post> posts = group.getPosts();
            if (posts.contains(post)) {
                return group;
            }
        }
        return null;
    }


    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }


}
