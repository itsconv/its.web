package com.itsconv.web.question.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itsconv.web.question.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{
    
    @Query(
        value = "select q from Question q where q.delYn = 'N' order by q.isCheck asc, q.sortOrder desc",
        countQuery = "select count(q) from Question q where q.delYn = 'N'"
    )
    Page<Question> findQuestionList(Pageable pageable);

    Optional<Question> findByIdAndDelYn(Long id, String delYn);

    @Query("""
            select
                q
            from Question q
            where q.delYn = 'N'
            and (q.sortOrder < :sortOrder or (q.sortOrder = :sortOrder and q.id < :id))
            order by sortOrder desc
            limit 1
            """)
    Optional<Question> findPrevByIdAndOrder(Long id, Integer sortOrder);

    @Query("""
            select
                q
            from Question q
            where q.delYn = 'N'
            and (q.sortOrder > :sortOrder or (q.sortOrder = :sortOrder and q.id > :id))
            order by sortOrder asc
            limit 1
            """)
    Optional<Question> findNextByIdAndOrder(Long id, Integer sortOrder);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Question q set q.delYn = 'Y' where q.id = :id and q.delYn = 'N'")
    int softDeleteWithoutAudit(@Param("id") Long id);

    @Query("select coalesce(max(q.sortOrder), 0) from Question q")
    Integer findMaxOrder();
}
