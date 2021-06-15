package romario.cabo.com.br.consulta_api.service;

import org.springframework.stereotype.Service;
import romario.cabo.com.br.consulta_api.exception.BadRequestException;
import romario.cabo.com.br.consulta_api.exception.InternalServerErrorException;
import romario.cabo.com.br.consulta_api.domain.Profile;
import romario.cabo.com.br.consulta_api.domain.User;
import romario.cabo.com.br.consulta_api.repository.ProfileRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public void save(Long idProfile, User user, int codProfile) {
        if (idProfile != null) {
            profileRepository.findById(idProfile)
                    .orElseThrow(() -> new BadRequestException("Perfil não localizado em nossa base de dados!"));
        }

        try {
            profileRepository.save(getProfile(idProfile, user, codProfile));
        } catch (Exception e) {
            throw new InternalServerErrorException(idProfile != null ? "Não foi possível alterar o perfil!" : "Não foi possível Salvar o perfil!");
        }
    }

    public void delete(Long idProfile) {
        Optional<Profile> profileOptional = profileRepository.findById(idProfile);

        if(!profileOptional.isPresent()) {
            throw new BadRequestException("Perfil não localizado em nossa base de dados!");
        }

        try {
            profileRepository.deleteById(idProfile);
        } catch (Exception e) {
            throw new InternalServerErrorException("Não foi possível excluir! " + e.getMessage());
        }
    }

    public Profile getProfileByIdUser(Long idUSer) {
        return profileRepository.findProfileByIdUser(idUSer);
    }

    private Profile getProfile(Long idProfile, User user, int codProfile) {
        Profile profile = new Profile();
        profile.setId(idProfile);
        profile.setUser(user);
        profile.setProfileCode(codProfile);
        return profile;
    }
}
