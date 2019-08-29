package org.artemis.config.server.test.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.StringJoiner;

@RefreshScope
@Component
public class TestReadRemoteConfig implements Serializable {

    @Value("${author.name}")
    private String authorName;

    @Value("${author.gender}")
    private String authorGender;

    @Value("${author.age}")
    private Integer authorAge;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorGender() {
        return authorGender;
    }

    public void setAuthorGender(String authorGender) {
        this.authorGender = authorGender;
    }

    public Integer getAuthorAge() {
        return authorAge;
    }

    public void setAuthorAge(Integer authorAge) {
        this.authorAge = authorAge;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TestReadRemoteConfig.class.getSimpleName() + "[", "]")
                .add("authorName='" + authorName + "'")
                .add("authorGender='" + authorGender + "'")
                .add("authorAge=" + authorAge)
                .toString();
    }
}
