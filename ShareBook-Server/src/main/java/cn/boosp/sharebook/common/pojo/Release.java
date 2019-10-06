package cn.boosp.sharebook.common.pojo;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "[release]")
public class Release {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany
    @JoinTable(
            name = "release_ubook",
            joinColumns = {
                    @JoinColumn(
                            name = "release_id",
                            referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "ubook_id",
                            referencedColumnName = "id")})
    private List<Ubook> ubooks;

    @Temporal(TemporalType.TIMESTAMP)
    Date releaseTime;
    public Release() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Ubook> getUbooks() {
        return ubooks;
    }

    public void setUbooks(List<Ubook> ubooks) {
        this.ubooks = ubooks;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }
}
