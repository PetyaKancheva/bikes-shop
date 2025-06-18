package bg.softuni.bikes_shop.model.entity;

import bg.softuni.bikes_shop.model.UserRoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name="user_role")
public class UserRoleEntity extends BaseEntity {
        @Enumerated(EnumType.STRING)
        @Column(name="name",nullable = false)
        private UserRoleEnum name;

        public UserRoleEnum getName() {
                return name;
        }

        public UserRoleEntity setName(UserRoleEnum name) {
                this.name = name;
                return this;
        }
}
