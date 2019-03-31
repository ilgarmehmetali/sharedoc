package net.milgar.sharedoc.domain.service;

import net.milgar.sharedoc.domain.model.TermClass;
import net.milgar.sharedoc.domain.model.User;
import net.milgar.sharedoc.domain.repository.TermClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TermClassServiceImpl implements TermClassService {

    @Autowired
    TermClassRepository termClassRepository;

    @Override
    public void save(TermClass termClass) {
        termClassRepository.save(termClass);
    }

    @Override
    public TermClass findByName(String name) {
        return termClassRepository.findByName(name);
    }

    @Override
    public TermClass findByCode(String code) {
        return termClassRepository.findByCode(code);
    }

    @Override
    public String generateRandomCode() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 8;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }

    @Override
    public TermClass findById(long id) {
        return termClassRepository.findById(id).orElse(null);
    }

    @Override
    public boolean canUserSeeTermClass(Long id, User user) {
        TermClass tmp1 = termClassRepository.findByIdAndStudentsContaining(id, user);
        boolean isCreator = termClassRepository.findById(id).orElse(new TermClass()).getCreator().getId().equals(user.getId());
        return tmp1 != null || isCreator;
    }

    @Override
    public boolean canUserEditTermClass(Long id, User user) {
        TermClass termClass = termClassRepository.findById(id).orElse(new TermClass());
        User creator = termClass.getCreator();
        boolean isCreator = creator.getId().equals(user.getId());
        return isCreator;
    }
}
