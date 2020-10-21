
package nosmoke;

public class Died extends AbstractEvent {

    private Long id;
    private Long point;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getHealth() {
        return point;
    }

    public void setHealth(Long point) {
        this.point = point;
    }
}
