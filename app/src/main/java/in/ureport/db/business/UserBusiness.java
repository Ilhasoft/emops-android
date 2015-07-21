package in.ureport.db.business;

import com.activeandroid.query.Select;

import java.util.List;

import br.com.ilhasoft.support.db.business.AbstractBusiness;
import in.ureport.db.repository.UserRepository;
import in.ureport.models.User;
import in.ureport.models.holders.Login;

/**
 * Created by johncordeiro on 7/9/15.
 */
public class UserBusiness extends AbstractBusiness<User> implements UserRepository {

    public UserBusiness() {
        super(User.class);
    }

    @Override
    public User login(Login login) {
        return new Select().from(getTypeClass())
                    .where("username = ? AND password = ?", login.getUsername(), login.getPassword())
                    .executeSingle();
    }

    public List<User> getAllOrdered() {
        return new Select().from(getTypeClass())
                .orderBy("points DESC")
                .execute();
    }

    @Override
    public List<User> getAllExcluding(Long id) {
        return new Select().from(getTypeClass())
                .where("id != ?", id)
                .execute();
    }
}