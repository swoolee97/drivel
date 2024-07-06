package com.ebiz.drivel.domain.theme.entity;

import com.ebiz.drivel.domain.course.entity.CourseTheme;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "theme")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Theme {
    @Id
    private Long id;

    @Column(name = "display_name")
    private String displayName;

    @OneToMany(mappedBy = "theme", fetch = FetchType.LAZY)
    private List<CourseTheme> courseThemes;
}

