package club.hosppy.hospital.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Hospital {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    private String name;

    private String type;

    private String provinceCode;

    private String cityCode;

    private String districtCode;

    private String address;

    private String intro;

    private HospitalStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Hospital hospital = (Hospital) o;
        return id != null && Objects.equals(id, hospital.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
