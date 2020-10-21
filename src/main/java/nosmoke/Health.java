package nosmoke;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Health_table")
public class Health {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long point;
    private String status;

    @PostPersist
    public void onPostPersist(){
        Runed runed = new Runed();
        BeanUtils.copyProperties(this, runed);
        runed.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        nosmoke.external.Earn earn = new nosmoke.external.Earn();
        // mappings goes here
        earn.setPoint(this.getPoint());
        earn.setHealthId(this.getId());
        HealthApplication.applicationContext.getBean(nosmoke.external.EarnService.class)
            .healthy(earn);


    }

    @PostUpdate
    public void onPostUpdate(){
        Died died = new Died();
        BeanUtils.copyProperties(this, died);
        died.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
