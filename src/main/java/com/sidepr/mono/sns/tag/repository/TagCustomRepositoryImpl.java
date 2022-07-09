package com.sidepr.mono.sns.tag.repository;

import com.sidepr.mono.sns.tag.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

@Repository
@RequiredArgsConstructor
public class TagCustomRepositoryImpl implements TagCustomRepository{

    private final EntityManager em;

    @Override
    public Tag findByContentOrCreate(String content) {
        try {
            Tag tag = em.createQuery(
                            "SELECT t" +
                                    " FROM Tag t" +
                                    " WHERE t.content = :content"
                            , Tag.class)
                    .setParameter("content", content)
                    .getSingleResult();
            return tag;
        } catch (PersistenceException e){
            Tag tag = new Tag(content);
            em.persist(tag);
            return tag;
        }
    }
}
