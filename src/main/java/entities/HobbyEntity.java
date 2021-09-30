package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "HOBBY")
@Entity
public class HobbyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    private String category;
    private String wikiLink;
    private String type;
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<PersonEntity> pList;

    public HobbyEntity() {
    }

    public List<PersonEntity> getpList(){
        return pList;
    }
    public void addPerson(PersonEntity et){
        pList.add(et);
    }


    public HobbyEntity(String name, String category, String wikiLink, String type) {
        this.name = name;
        this.category = category;
        this.wikiLink = wikiLink;
        this.type = type;
        pList = new ArrayList<>();
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }




    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getcategory() {
        return category;
    }

    public void setcategory(String category) {
        category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}